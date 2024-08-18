package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.MemberDto;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;

public interface MemberAuthenticationService {
  boolean login(String id, String password, HttpServletRequest request);
  MemberDto findUsernameByEmail(String email);
  String findPasswordByEmailAndId(String email, String id);
  void handleLoginFailure(MemberDto member);

}
