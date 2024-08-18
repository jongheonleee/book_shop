package com.fastcampus.ch4.service.member;

public interface AccountUnlockService {
  void generatePasswordResetToken(String email);

  // 비밀번호 재설정 토큰 유효성 검증
  boolean validatePasswordResetToken(String token);

  // 비밀번호 재설정
  void resetPassword(String token, String newPassword);
}
