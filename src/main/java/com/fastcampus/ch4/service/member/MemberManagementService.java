package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MemberManagementService {
  void addMember(MemberDto member);
  MemberDto getMemberById(String id);
  List<MemberDto> getAllMembers();
  void updateMember(MemberDto member);
  void removeMember(String id);
  void removeAllMembers();

  // 아이디 중복 체크
  boolean isIdAvailable(String id);
  boolean isIdDuplicate(String id);

  // 약관 동의 이력 저장
  void saveTermAgree(List<TermAgreeDto> termAgreeDtos);

  // 모든 약관 가져오기
  List<TermDto> getAllTerms();

  // 현재 사용자 정보 가져오기
  MemberDto getCurrentUser(HttpServletRequest request);

  // 사용자 프로필 업데이트
  boolean updateUserProfile(HttpServletRequest request, MemberDto updatedUser);

  // 비밀번호 재설정 관련 메서드 추가
  void updatePassword(String id, String newPassword);
  boolean isTokenValid(String token);
}
