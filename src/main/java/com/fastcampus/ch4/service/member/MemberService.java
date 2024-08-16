package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;
import com.fastcampus.ch4.dto.member.LoginHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MemberService {
  public void addMember(MemberDto member);
  MemberDto getMemberById(String id);
  List<MemberDto> getAllMembers();
  void updateMember(MemberDto member);
  void removeMember(String id);
  void removeAllMembers();

  // 로그인 기능 추가
  boolean login(String id, String password, HttpServletRequest request);

  // 로그인 이력 저장 메서드 추가
  void saveLoginHistory(LoginHistoryDto loginHistory);

  // 모든 로그인 기록 조회 메서드 추가
  List<LoginHistoryDto> getAllLoginHistories();

  // 모든 로그인 기록 삭제 메서드 추가
  void deleteAllLoginHistories();

  // 이메일로 아이디 찾기 메서드 추가
  String findUsernameByEmail(String email);

  // 이메일과 아이디로 비밀번호 찾기 추가
  String findPasswordByEmailAndId(String email, String id);

  // 아이디 중복 검사
  boolean isIdAvailable(String id);

  // 약관 동의 이력 저장
  void saveTermAgree(TermAgreeDto TermAgreeDto);

  // 아이디 중복 체크
  boolean isIdDuplicate(String id);

  //
  List<TermDto> getAllTerms();
  MemberDto getCurrentUser(HttpServletRequest request);
  boolean updateUserProfile(HttpServletRequest request, MemberDto updatedUser);
  void handleLoginFailure(MemberDto member);
}
