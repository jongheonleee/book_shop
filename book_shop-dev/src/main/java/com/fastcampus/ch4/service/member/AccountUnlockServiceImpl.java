package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountUnlockServiceImpl implements AccountUnlockService {

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public void generatePasswordResetToken(String email) {
    // 비밀번호 재설정 토큰 생성
    String token = UUID.randomUUID().toString();

    // MemberDto 객체 가져오기
    MemberDto member = memberDao.findUsernameByEmail(email);
    if (member == null) {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }

    // 토큰 저장
    member.setMailTokn(token);
    memberDao.updateMember(member);

    // 이메일 전송 (옵션)
    String resetLink = generatePasswordResetLink(member.getEmail());
    String subject = "비밀번호 재설정 요청";
    String body = "계정이 잠겼습니다. 비밀번호를 재설정하려면 다음 링크를 클릭하세요: " + resetLink;

    // SimpleMailMessage 객체를 사용하여 이메일 전송
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(member.getEmail());  // 수신자 이메일 설정
    message.setSubject(subject);       // 이메일 제목 설정
    message.setText(body);             // 이메일 본문 설정

  try{
    // 이메일 전송
    javaMailSender.send(message);
  } catch (Exception e) {
    throw new RuntimeException("이메일 전송 중 오류가 발생했습니다: " + e.getMessage(), e);
  }

  }
  @Override
  public boolean validatePasswordResetToken(String token) {
    // 토큰 검증
    MemberDto member = memberDao.selectMemberByToken(token);
    return member != null && token.equals(member.getMailTokn());
  }

  @Override
  public void resetPassword(String token, String newPassword) {
    // 토큰 검증
    MemberDto member = memberDao.selectMemberByToken(token);
    if (member == null) {
      throw new RuntimeException("유효하지 않은 토큰입니다.");
    }

    // 비밀번호 업데이트
    member.setPswd(newPassword); // 암호화된 비밀번호를 사용해야 합니다.
    member.setMailTokn(null); // 토큰 삭제
    memberDao.updateMember(member);
  }

  private String generatePasswordResetLink(String token) {
    // 비밀번호 재설정 링크를 생성하는 로직
    // 예: http://localhost:8080/ch4/reset-password?token=<token>
    return "http://localhost:8080/ch4/reset-password?token=" + token;
  }
}
