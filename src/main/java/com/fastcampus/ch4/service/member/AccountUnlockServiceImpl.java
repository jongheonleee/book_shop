package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AccountUnlockServiceImpl implements AccountUnlockService {

  private static final Logger logger = LoggerFactory.getLogger(AccountUnlockServiceImpl.class);

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private EmailService emailService;

  @Override
  public void generatePasswordResetToken(String email) {
    String token = TokenGenerator.generateShortToken();
    logger.info("생성된 비밀번호 재설정 토큰: {}", token);

    MemberDto member = memberDao.findUsernameByEmail(email);
    if (member == null) {
      logger.error("이메일로 사용자를 찾을 수 없습니다: {}", email);
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }

    logger.info("토큰을 저장하는 중: {}", token);
    member.setMailTokn(token);
    memberDao.updateMember(member);

    String subject = "비밀번호 재설정 요청";
    String body = "비밀번호를 재설정하려면 다음 토큰을 입력하세요: " + token;

    try {
      logger.info("이메일 전송 중: {}", email);
      emailService.sendEmail(member.getEmail(), subject, body);
      logger.info("이메일이 성공적으로 전송되었습니다.");
    } catch (Exception e) {
      logger.error("이메일 전송 중 오류 발생: {}", e.getMessage());
      throw new RuntimeException("이메일 전송 중 오류가 발생했습니다: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean validatePasswordResetToken(String token) {
    logger.info("토큰 검증 중: {}", token);

    // DB에서 토큰을 조회하는 로직
    MemberDto member = memberDao.selectMemberByToken(token);

    if (member != null) {
      // DB에서 가져온 회원 ID와 메일 토큰 값 확인
      logger.info("조회된 회원 ID: {}", member.getId());
      logger.info("DB에 저장된 토큰: {}", member.getMailTokn());
      logger.info("입력된 토큰: {}", token);

      // 토큰 비교
      boolean isValid = token.equals(member.getMailTokn());
      if (isValid) {
        logger.info("토큰이 유효합니다: {}", token);
      } else {
        logger.warn("토큰이 일치하지 않음. DB 토큰: {}, 입력된 토큰: {}", member.getMailTokn(), token);
      }
      return isValid;
    } else {
      logger.warn("토큰에 해당하는 사용자를 찾을 수 없습니다. 입력된 토큰: {}", token);
      return false;
    }
  }


  @Override
  public void resetPassword(String token, String newPassword) {
    logger.info("비밀번호 재설정 중, 토큰: {}", token);
    MemberDto member = memberDao.selectMemberByToken(token);

    if (member == null) {
      logger.error("유효하지 않은 토큰: {}", token);
      throw new RuntimeException("유효하지 않은 토큰입니다.");
    }

    logger.info("비밀번호 재설정 중: {}, 사용자 ID: {}", token, member.getId());
    member.setPswd(newPassword);
    member.setMailTokn(null);
    member.setAcntLock("N");
    memberDao.updateMember(member);
    logger.info("비밀번호가 성공적으로 재설정되고, 계정 잠금이 해제되었습니다.");
  }

  public static class TokenGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TOKEN_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    public static String generateShortToken() {
      StringBuilder token = new StringBuilder(TOKEN_LENGTH);
      for (int i = 0; i < TOKEN_LENGTH; i++) {
        token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
      }
      return token.toString();
    }
  }
}
