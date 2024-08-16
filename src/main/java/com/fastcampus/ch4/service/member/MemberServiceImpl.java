package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.*;
import com.fastcampus.ch4.dto.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private LoginHistoryDao loginHistoryDao;

  @Autowired
  private TermAgreeDao TermAgreeDao;

  @Autowired
  private TermDao TermDao;

  @Autowired
  private InfoChangeHistoryDao infoChangeHistoryDao;

  @Override
  public void addMember(MemberDto member) {

    if (isIdDuplicate(member.getId())) {
      throw new RuntimeException("회원 ID가 이미 존재합니다.");
    }

    // 회원 정보를 저장
    memberDao.insertMember(member);
  }

  @Override
  public MemberDto getMemberById(String id) {
    return memberDao.selectMemberById(id);
  }

  @Override
  public List<MemberDto> getAllMembers() {
    return memberDao.getAllMembers();
  }

  @Override
  public void updateMember(MemberDto member) {
    memberDao.updateMember(member);
  }

  @Override
  public void removeMember(String id) {
    memberDao.deleteMember(id);
  }

  @Override
  public void removeAllMembers() {
    memberDao.deleteAll();
  }


  @Override
  @Transactional
  public boolean login(String id, String password, HttpServletRequest request) {
    MemberDto member = memberDao.selectMemberById(id);  // id로 Member 객체를 가져옵니다.
    if (member == null) {
      throw new RuntimeException("존재하지 않는 계정입니다.");
    }

    // 계정 잠금 상태 확인
    if ("Y".equals(member.getAcntLock())) {
      throw new RuntimeException("계정이 잠겼습니다.");
    }

    boolean isAuthenticated = memberDao.validatePassword(id, password);

    if (isAuthenticated) {
      // 로그인 성공 처리
      String ipAddr = getClientIp(request); // 클라이언트 IP 주소 추출
      LocalDateTime now = LocalDateTime.now();

      // LoginHistoryDto 객체 생성
      LoginHistoryDto loginHistory = new LoginHistoryDto();
      loginHistory.setLognTime(now);  // lognTime
      loginHistory.setIpAddr(ipAddr); // ipAddr
      loginHistory.setRegDate(now);   // regDate
      loginHistory.setRegId(id);      // regId
      loginHistory.setUpDate(now);    // upDate
      loginHistory.setUpId(id);       // upId
      loginHistory.setId(id);         // id

      saveLoginHistory(loginHistory);

      // 로그인 성공 시 로그인 실패 횟수 리셋
      member.setFailLognAtmt(0);
      memberDao.updateMember(member);  // DB에 로그인 실패 횟수 리셋
    } else {
      // 로그인 실패 처리
      handleLoginFailure(member);
    }

    return isAuthenticated;
  }


  private String getClientIp(HttpServletRequest request) {
    String remoteAddr = request.getRemoteAddr();
    if (request.getHeader("X-Forwarded-For") != null) {
      remoteAddr = request.getHeader("X-Forwarded-For").split(",")[0];
    }
    return remoteAddr;
  }

  @Override
  public void saveLoginHistory(LoginHistoryDto loginHistory) {
    loginHistoryDao.insertLoginHistory(loginHistory);
  }

  @Override
  public List<LoginHistoryDto> getAllLoginHistories() {
    return loginHistoryDao.getAllLoginHistories();
  }

  @Override
  public void deleteAllLoginHistories() {
    loginHistoryDao.deleteAll();
  }

  public String findUsernameByEmail(String email) {
    return memberDao.findUsernameByEmail(email);
  }

  @Override
  public String findPasswordByEmailAndId(String email, String id) {
    // 이메일과 아이디로 사용자 조회
    MemberDto member = memberDao.selectMemberByEmailAndId(email, id);
    if (member == null) {
      return null; // 사용자 정보가 없음
    }
    // 사용자 비밀번호 반환
    return member.getPswd();
  }

  @Override
  public boolean isIdAvailable(String id) {
    // 아이디의 사용 가능 여부를 확인
    return memberDao.isIdAvailable(id) == 0;
  }
  @Override
  public void saveTermAgree(TermAgreeDto TermAgreeDto) {
    TermAgreeDao.insertTermAgree(TermAgreeDto);
  }

  @Override
  public List<TermDto> getAllTerms() {
    return TermDao.getAllTerms();
  }
  @Override
  public boolean isIdDuplicate(String id) {
    return memberDao.isIdAvailable(id) > 0;
  }

  @Override
  public MemberDto getCurrentUser(HttpServletRequest request) {
    // HttpServletRequest에서 HttpSession을 가져옴
    HttpSession session = request.getSession(false); // false를 사용하여 세션이 없으면 새로 생성하지 않도록

    if (session == null) {
      throw new RuntimeException("세션이 존재하지 않습니다.");
    }

    // 세션에서 userId 가져오기
    String userId = (String) session.getAttribute("id");

    if (userId != null) {
      return memberDao.selectMemberById(userId);
    }

    // 세션에 userId가 없을 경우 예외 처리
    throw new RuntimeException("세션에서 사용자를 찾을 수 없습니다.");
  }

  @Override
  public boolean updateUserProfile(HttpServletRequest request, MemberDto updatedUser) {
    MemberDto currentUser = getCurrentUser(request);

    // 프로필 업데이트
    boolean isUpdated = memberDao.updateMember(updatedUser);

    if (isUpdated) {
      // 변경 사항 이력 저장
      InfoChangeHistoryDto changeLog = createChangeHistory(updatedUser, currentUser);
      infoChangeHistoryDao.saveChangeHistory(changeLog);
    }

    return isUpdated;
  }

  private InfoChangeHistoryDto createChangeHistory(MemberDto updatedUser, MemberDto currentUser) {
    InfoChangeHistoryDto changeLog = new InfoChangeHistoryDto();
    changeLog.setId(currentUser.getId()); // 현재 사용자의 아이디
    changeLog.setChgTime(new Date());
    changeLog.setChgInfo("Profile updated");

    String beforeChanges = "Pswd: " + (currentUser.getPswd() != null ? currentUser.getPswd() : "Not changed") +
            ", PhnNumb: " + (currentUser.getPhnNumb() != null ? currentUser.getPhnNumb() : "Not changed") +
            ", Email: " + (currentUser.getEmail() != null ? currentUser.getEmail() : "Not changed");

    String afterChanges = "Pswd: " + (updatedUser.getPswd() != null ? updatedUser.getPswd() : "Not changed") +
            ", PhnNumb: " + (updatedUser.getPhnNumb() != null ? updatedUser.getPhnNumb() : "Not changed") +
            ", Email: " + (updatedUser.getEmail() != null ? updatedUser.getEmail() : "Not changed");

    changeLog.setBforChg(beforeChanges);
    changeLog.setAftrChg(afterChanges);
    changeLog.setRegDate(new Date());
    changeLog.setRegId(currentUser.getId()); // 등록자 아이디
    changeLog.setUpDate(new Date());
    changeLog.setUpId(currentUser.getId()); // 수정자 아이디

    return changeLog;
  }
  public void handleLoginFailure(MemberDto member) {
    int failCount = member.getFailLognAtmt() + 1;
    member.setFailLognAtmt(failCount);

    if (failCount >= 3) {
      member.setAcntLock("Y");
      // DB에 계정 잠금 상태와 실패 횟수 업데이트
      memberDao.updateMember(member);  // memberDao는 DB 업데이트를 수행하는 DAO 객체입니다.
      throw new RuntimeException("비밀번호 3회 틀렸습니다. 계정이 잠겼습니다.");
    } else {
      // DB에 로그인 실패 횟수 업데이트
      memberDao.updateMember(member);  // memberDao는 DB 업데이트를 수행하는 DAO 객체입니다.
      throw new RuntimeException("로그인 실패! 남은 기회: " + (3 - failCount));
    }
  }
}
