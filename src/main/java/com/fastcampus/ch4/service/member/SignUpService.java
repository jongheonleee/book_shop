package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.dto.member.TermAgreeDto;

import java.util.List;

public interface SignUpService {
  void processSignup(MemberDto member, List<Integer> requiredTerms, List<Integer> optionalTerms, String address);
}
