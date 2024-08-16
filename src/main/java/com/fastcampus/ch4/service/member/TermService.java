package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;

import java.util.List;
import java.util.Map;

public interface   TermService {
    List<TermDto> getAllTerms();
    TermDto getTermById(int termId);
    List<Integer> getAllTermIds();
//    void saveTermAgreements(String memberId, List<TermAgreeDto> termAgreeDtos);
    List<TermAgreeDto> getTermAgreements(String memberId);
}
