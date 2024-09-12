package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.LoginHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class MemberAuthenticationServiceImpl implements MemberAuthenticationService {

  private static final Logger logger = LoggerFactory.getLogger(MemberAuthenticationServiceImpl.class);

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private LoginHistoryService loginHistoryService;

  @Autowired
  private AccountUnlockService accountUnlockService;

  @Override
  @Transactional
  public boolean login(String id, String password, HttpServletRequest request) {
    logger.info("Login attempt for user ID: {}", id);

    MemberDto member = memberDao.selectMemberById(id);
    if (member == null) {
      logger.warn("Login failed: User ID {} does not exist", id);
      throw new RuntimeException("존재하지 않는 계정입니다.");
    }

    if ("Y".equals(member.getAcntLock())) {
      logger.warn("Login failed: Account for user ID {} is locked", id);

      // 계정이 잠겼을 때 이메일 전송 로직 추가
      accountUnlockService.generatePasswordResetToken(member.getEmail());

      return false;
    }

    boolean isAuthenticated = memberDao.validatePassword(id, password);

    if (isAuthenticated) {
      logger.info("Login successful for user ID: {}", id);

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
      logger.info("Login failed for user ID: {} - Invalid password", id);
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
    logger.info("Failed login attempt for user ID: {}. Attempt count: {}", member.getId(), failCount);

    if (failCount >= 3) {
      member.setAcntLock("Y");
      memberDao.updateMember(member);
      logger.warn("Account locked for user ID: {} after {} failed login attempts", member.getId(), failCount);

      // 계정 잠금 시 이메일 전송
      accountUnlockService.generatePasswordResetToken(member.getEmail());

      throw new RuntimeException("비밀번호 3회 틀렸습니다. 계정이 잠겼습니다.");
    } else {
      memberDao.updateMember(member);
      logger.info("Updated failed login attempt count for user ID: {}", member.getId());
      throw new RuntimeException("로그인 실패! 남은 기회: " + (3 - failCount));
    }
  }
}
