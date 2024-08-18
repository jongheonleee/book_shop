package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;

import java.util.List;

public interface TermService {
    List<TermDto> getAllTerms();
    TermDto getTermById(int termId);
    void validateTermAgreements(List<TermAgreeDto> termAgreeDtos);
    void saveTermAgreements(List<TermAgreeDto> termAgreeDtos);
    List<Integer> getAllTermIds();
    List<TermAgreeDto> getTermAgreements(String memberId);
}
