package com.fastcampus.ch4.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  private final String senderEmail = "buraldongja@naver.com"; // 발신자 이메일 주소 고정

  public void sendEmail(String to, String subject, String body) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      // 발신자 설정
      helper.setFrom(senderEmail);

      // 수신자 설정
      helper.setTo(to);

      // 제목 설정
      helper.setSubject(subject);

      // 본문 설정
      helper.setText(body, true); // HTML 사용 시 true 설정

      // 이메일 전송
      javaMailSender.send(message);
      System.out.println("이메일 전송 성공!");

    } catch (Exception e) {
      System.err.println("이메일 전송 중 오류 발생: " + e.getMessage());
    }
  }
}