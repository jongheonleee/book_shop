package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.LoginHistoryDto;

import java.util.List;

public interface LoginHistoryService {
  // 로그인 이력 저장
  void saveLoginHistory(LoginHistoryDto loginHistory);

  // 모든 로그인 기록 조회
  List<LoginHistoryDto> getAllLoginHistories();

  // 모든 로그인 기록 삭제
  void deleteAllLoginHistories();
}