package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.dto.member.TermAgreeDto;

import java.util.List;


public interface MemberDao {


  //새로운 회원 정보를 데이터베이스에 삽입

  void insertMember(MemberDto memberDto);

  // 주어진 ID로 회원 정보를 조회
  MemberDto selectMemberById(String id);

  // 기존 회원 정보를 업데이트
  List<MemberDto> getAllMembers();

  // 주어진 ID로 회원 정보를 수정
  boolean updateMember(MemberDto memberDto);

  // 주어진 ID로 회원 정보를 삭제
  void deleteMember(String id);

  // 모든 데이터 삭제
  void deleteAll();

  // 로그인 검증을 위한 메소드
  boolean validatePassword(String id, String password);

  // 이메일로 아이디 찾기
  MemberDto findUsernameByEmail(String email);

  // 이메일과 아이디로 비밀번호 찾기
  MemberDto selectMemberByEmailAndId(String email, String id);

  //아이디 중복 검사
  int isIdAvailable(String id);

  // 약관 동의 이력 저장
  void saveAgreementAgree(TermAgreeDto dto);  // 데이터베이스에 약관 동의 이력 저장

  void insertTermAgree(TermAgreeDto termAgree);
//  // 회원 정보 수정 이력 저장
//  void insertMemberChangeHistory(MemberChangeHistoryDto dto); // 회원 정보 수정 이력 저장
//  회원정보수정 dto 따로 만들지 고민
  MemberDto selectMemberByToken(String token);
}






