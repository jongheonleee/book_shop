package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TermAgreeDao {
  void insertTermAgreements(List<TermAgreeDto> termAgreeDtos);
  void updateTermAgreement(TermAgreeDto termAgreeDto);
  void deleteTermAgreement(@Param("memberId") String memberId, @Param("termId") int termId);
  void deleteAllTermAgreements();
  TermAgreeDto getTermAgree(@Param("memberId") String memberId, @Param("termId") int termId);
  List<TermAgreeDto> getTermAgreements(@Param("memberId") String memberId);
}
