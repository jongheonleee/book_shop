package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;

import java.util.List;

public interface TermAgreeDao {

  void insertTermAgree(TermAgreeDto dto);

  void updateTermAgree(TermAgreeDto dto);

  void deleteTermAgree(String id); // ID로 삭제

  void deleteAllTermAgrees();

  TermAgreeDto selectTermAgreeById(String id); // ID로 조회

  List<TermAgreeDto> selectAllTermAgrees();

  TermAgreeDto getTermAgree(String id, int termId);

  // 특정 회원의 약관 동의 이력 조회

  List<TermAgreeDto> getTermAgreements(String memberId);
}
