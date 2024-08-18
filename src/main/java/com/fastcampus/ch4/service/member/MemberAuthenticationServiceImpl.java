package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.LoginHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class MemberAuthenticationServiceImpl implements MemberAuthenticationService {

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private LoginHistoryService loginHistoryService;

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  AccountUnlockService accountUnlockService;

  @Override
  @Transactional
  public boolean login(String id, String password, HttpServletRequest request) {
    MemberDto member = memberDao.selectMemberById(id);
    if (member == null) {
      throw new RuntimeException("존재하지 않는 계정입니다.");
    }

    if ("Y".equals(member.getAcntLock())) {
      throw new RuntimeException("계정이 잠겼습니다.");
    }

    boolean isAuthenticated = memberDao.validatePassword(id, password);

    if (isAuthenticated) {
      String ipAddr = request.getRemoteAddr();
      LocalDateTime now = LocalDateTime.now();

      LoginHistoryDto loginHistory = new LoginHistoryDto();
      loginHistory.setLognTime(now);
      loginHistory.setIpAddr(ipAddr);
      loginHistory.setRegDate(now);
      loginHistory.setRegId(id);
      loginHistory.setUpDate(now);
      loginHistory.setUpId(id);
      loginHistory.setId(id);

      loginHistoryService.saveLoginHistory(loginHistory);

      member.setFailLognAtmt(0);
      memberDao.updateMember(member);
    } else {
      handleLoginFailure(member);
    }

    return isAuthenticated;
  }

  @Override
  public MemberDto findUsernameByEmail(String email) {
    return memberDao.findUsernameByEmail(email);
  }

  @Override
  public String findPasswordByEmailAndId(String email, String id) {
    MemberDto member = memberDao.selectMemberByEmailAndId(email, id);
    return member != null ? member.getPswd() : null;
  }

  @Override
  public void handleLoginFailure(MemberDto member) {
    int failCount = member.getFailLognAtmt() + 1;
    member.setFailLognAtmt(failCount);

    if (failCount >= 3) {
      member.setAcntLock("Y");
      memberDao.updateMember(member);
      // 이메일로 비밀번호 재설정 토큰 보내기
      try {
        accountUnlockService.generatePasswordResetToken(member.getEmail());
      } catch (Exception e) {
        throw new RuntimeException("비밀번호 재설정 이메일 발송 실패");
      }
      throw new RuntimeException("비밀번호 3회 틀렸습니다. 계정이 잠겼습니다.");
    } else {
      memberDao.updateMember(member);
      throw new RuntimeException("로그인 실패! 남은 기회: " + (3 - failCount));
    }
  }


}
