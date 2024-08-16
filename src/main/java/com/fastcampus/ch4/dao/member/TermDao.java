package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;

import java.util.List;

public interface TermDao {

  void insertTerm(TermDto term);
  TermDto getTermById(int termId);
  void updateTerm(TermDto term);
  void deleteTerm(int termId);
  void deleteAllTerms();
  List<TermDto> getAllTerms();
  List<Integer> getAllTermIds();
  void insertTermAgreements(List<TermAgreeDto> termAgreeDtos);
}