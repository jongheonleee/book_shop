package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.dto.member.TermAgreeDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*
 * MemberDao 인터페이스의 구현 클래스입니다.
 * 데이터베이스와의 상호작용을 담당하며, 회원 정보를 삽입, 조회, 수정, 삭제하는 기능을 제공합니다.
 */
@Repository
public class MemberDaoImpl implements MemberDao {

  private final SqlSession sqlSession;
  private static final String NAMESPACE = "com.fastcampus.ch4.dao.member.MemberDao";

  @Autowired
  public MemberDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public void insertMember(MemberDto memberDto) {
    // ID 중복 체크
    if (isIdAvailable(memberDto.getId()) > 0) {
      throw new RuntimeException("회원 ID가 이미 존재합니다.");
    }
    try {
      sqlSession.insert(NAMESPACE + ".insertMember", memberDto);
    } catch (Exception e) {
      // 예외 처리 및 한글 메시지 로깅
      throw new RuntimeException("회원 정보 삽입 중 오류가 발생했습니다.", e);
    }
  }

  @Override
  public MemberDto selectMemberById(String id) {
    return sqlSession.selectOne(NAMESPACE + ".selectMemberById", id);
  }

  @Override
  public List<MemberDto> getAllMembers() {
    return sqlSession.selectList(NAMESPACE + ".getAllMembers");
  }

  @Override
  public boolean updateMember(MemberDto member) {
    int result = sqlSession.update(NAMESPACE + ".updateMember", member);
    return result > 0;  // 0보다 크면 true, 그렇지 않으면 false 반환
  }

  @Override
  public void deleteMember(String id) {
    try {
      sqlSession.delete(NAMESPACE + ".deleteMember", id);
    } catch (Exception e) {
      // 예외 처리 및 한글 메시지 로깅
      throw new RuntimeException("회원 정보 삭제 중 오류가 발생했습니다.", e);
    }
  }

  @Override
  public void deleteAll() {
    try {
      sqlSession.delete(NAMESPACE + ".deleteAll");
    } catch (Exception e) {
      // 예외 처리 및 한글 메시지 로깅
      throw new RuntimeException("모든 회원 삭제 중 오류가 발생했습니다.", e);
    }
  }

  @Override
  public boolean validatePassword(String id, String password) {
    MemberDto member = selectMemberById(id);
    if (member == null) {
      return false;
    }
    // 암호화된 비밀번호와 비교하는 로직으로 개선 필요
    return password.equals(member.getPswd());
  }

  @Override
  public MemberDto findUsernameByEmail(String email) {
    return sqlSession.selectOne(NAMESPACE + ".findUsernameByEmail", email);
  }

  @Override
  public MemberDto selectMemberByEmailAndId(String email, String id) {
    Map<String, Object> params = Map.of("email", email, "id", id);
    return sqlSession.selectOne(NAMESPACE + ".selectMemberByEmailAndId", params);
  }

  @Override
  public int isIdAvailable(String id) {
    return sqlSession.selectOne(NAMESPACE + ".isIdAvailable", id);
  }

  @Override
  public void saveAgreementAgree(TermAgreeDto dto) {
    try {
      sqlSession.insert(NAMESPACE + ".saveAgreementAgree", dto);
    } catch (Exception e) {
      // 예외 처리 및 한글 메시지 로깅
      throw new RuntimeException("약관 동의 이력 저장 중 오류가 발생했습니다.", e);
    }
  }
  @Override
  public void insertTermAgree(TermAgreeDto termAgree){
     sqlSession.insert(NAMESPACE +".insertTermAgree", termAgree);
  }

  @Override
  public MemberDto selectMemberByToken(String token) {
    return sqlSession.selectOne("com.fastcampus.ch4.dao.member.MemberDao.selectMemberByToken", token);
  }

  //  @Override
//  public void insertMemberChangeHistory(MemberChangeHistoryDto dto) {
//    try {
//      sqlSession.insert(NAMESPACE + ".insertMemberChangeHistory", dto);
//    } catch (Exception e) {
//      throw new RuntimeException("회원 정보 수정 이력 저장 중 오류가 발생했습니다.", e);
//    }
//  }
}
