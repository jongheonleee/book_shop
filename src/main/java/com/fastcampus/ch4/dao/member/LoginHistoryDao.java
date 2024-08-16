package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.LoginHistoryDto;

import java.util.List;

public interface LoginHistoryDao {
  // 로그인 기록 저장 메서드
  int insertLoginHistory(LoginHistoryDto loginHistoryDto);

  // 로그인 기록 조회 메서드 (ID로 조회)
  List<LoginHistoryDto> selectLoginHistoryById(String id);

  // 모든 로그인 기록 조회 메서드
  List<LoginHistoryDto> getAllLoginHistories();

  // 모든 로그인 기록 삭제 메서드
  void deleteAll();
}
