package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermDto;

import java.util.List;

public interface TermDao {
  void insertTermAgreements(List<TermDto> termDtos);

  TermDto getTermByName(String termName);

  List<TermDto> getAllTerms();

  TermDto getTermById(int termId);

  List<Integer> getAllTermIds();

  void deleteTermById(int termId);

  void deleteAllTerms();

  void updateTerm(TermDto termDto);
}