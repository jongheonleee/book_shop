package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dao.member.TermAgreeDao;
import com.fastcampus.ch4.dao.member.TermDao;
import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
import com.fastcampus.ch4.dto.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Service
public class MemberManagementServiceImpl implements MemberManagementService {

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private TermAgreeDao termAgreeDao;

  @Autowired
  private TermDao termDao;

  @Autowired
  private InfoChangeHistoryDao infoChangeHistoryDao;

  @Override
  public void addMember(MemberDto member) {
    if (isIdDuplicate(member.getId())) {
      throw new RuntimeException("회원 ID가 이미 존재합니다.");
    }
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
  public boolean isIdAvailable(String id) {
    return memberDao.isIdAvailable(id) == 0;
  }

  @Override
  public boolean isIdDuplicate(String id) {
    return memberDao.isIdAvailable(id) > 0;
  }

  @Override
  public void saveTermAgree(List<TermAgreeDto> termAgreeDtos) {
    termAgreeDao.insertTermAgreements(termAgreeDtos);
  }

  @Override
  public List<TermDto> getAllTerms() {
    return termDao.getAllTerms();
  }

  @Override
  public MemberDto getCurrentUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session == null) {
      throw new RuntimeException("세션이 존재하지 않습니다.");
    }

    String userId = (String) session.getAttribute("id");

    if (userId != null) {
      return memberDao.selectMemberById(userId);
    }

    throw new RuntimeException("세션에서 사용자를 찾을 수 없습니다.");
  }

  @Override
  public boolean updateUserProfile(HttpServletRequest request, MemberDto updatedUser) {
    MemberDto currentUser = getCurrentUser(request);

    boolean isUpdated = memberDao.updateMember(updatedUser);

    if (isUpdated) {
      InfoChangeHistoryDto changeLog = createChangeHistory(updatedUser, currentUser);
      infoChangeHistoryDao.saveChangeHistory(changeLog);
    }

    return isUpdated;
  }

  private InfoChangeHistoryDto createChangeHistory(MemberDto updatedUser, MemberDto currentUser) {
    InfoChangeHistoryDto changeLog = new InfoChangeHistoryDto();
    changeLog.setId(currentUser.getId());
    changeLog.setChgTime(LocalDate.now());
    changeLog.setChgInfo("Profile updated");

    String beforeChanges = "Pswd: " + (currentUser.getPswd() != null ? currentUser.getPswd() : "Not changed") +
            ", PhnNumb: " + (currentUser.getPhnNumb() != null ? currentUser.getPhnNumb() : "Not changed") +
            ", Email: " + (currentUser.getEmail() != null ? currentUser.getEmail() : "Not changed");

    String afterChanges = "Pswd: " + (updatedUser.getPswd() != null ? updatedUser.getPswd() : "Not changed") +
            ", PhnNumb: " + (updatedUser.getPhnNumb() != null ? updatedUser.getPhnNumb() : "Not changed") +
            ", Email: " + (updatedUser.getEmail() != null ? updatedUser.getEmail() : "Not changed");

    changeLog.setBforChg(beforeChanges);
    changeLog.setAftrChg(afterChanges);
    changeLog.setRegDate(LocalDate.now());
    changeLog.setRegId(currentUser.getId());
    changeLog.setUpDate(LocalDate.now());
    changeLog.setUpId(currentUser.getId());

    return changeLog;
  }

  @Override
  public void updatePassword(String id, String newPassword) {
    MemberDto member = memberDao.selectMemberById(id);
    if (member == null) {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }

    member.setPswd(newPassword); // 암호화된 비밀번호를 사용해야 합니다.
    memberDao.updateMember(member);
  }

  @Override
  public boolean isTokenValid(String token) {
    MemberDto member = memberDao.selectMemberByToken(token);
    return member != null && member.getMailTokn() != null;
  }
}
