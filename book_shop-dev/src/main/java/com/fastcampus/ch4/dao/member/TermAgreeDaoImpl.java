package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TermAgreeDaoImpl implements TermAgreeDao {

  @Autowired
  private SqlSessionTemplate sqlSession;

  private static final String NAMESPACE = "com.fastcampus.ch4.dao.member.TermAgreeDao";

  @Override
  public void insertTermAgree(TermAgreeDto dto) {
    // 약관 동의 기록을 데이터베이스에 삽입
    sqlSession.insert(NAMESPACE + ".insertTermAgree", dto);
  }

  @Override
  public void updateTermAgree(TermAgreeDto dto) {
    // 약관 동의 기록을 데이터베이스에서 업데이트
    sqlSession.update(NAMESPACE + ".updateTermAgree", dto);
  }

  @Override
  public void deleteTermAgree(String id) {
    // 특정 ID에 해당하는 약관 동의 기록을 데이터베이스에서 삭제
    sqlSession.delete(NAMESPACE + ".deleteTermAgree", id);
  }

  @Override
  public void deleteAllTermAgrees() {
    // 모든 약관 동의 기록을 데이터베이스에서 삭제
    sqlSession.delete(NAMESPACE + ".deleteAllTermAgrees");
  }

  @Override
  public TermAgreeDto selectTermAgreeById(String id) {
    // 특정 ID에 해당하는 약관 동의 기록을 조회
    return sqlSession.selectOne(NAMESPACE + ".selectTermAgreeById", id);
  }

  @Override
  public List<TermAgreeDto> selectAllTermAgrees() {
    // 모든 약관 동의 기록을 조회
    return sqlSession.selectList(NAMESPACE + ".selectAllTermAgrees");
  }

  @Override
  public TermAgreeDto getTermAgree(String id, int termId) {
    // 특정 회원의 약관 동의 이력을 조회
    TermAgreeDto dto = new TermAgreeDto();
    dto.setId(id);
    dto.setTermId(termId);
    return sqlSession.selectOne(NAMESPACE + ".getTermAgree", dto);
  }

  @Override
  public List<TermAgreeDto> getTermAgreements(String memberId) {
    return sqlSession.selectList(NAMESPACE + ".getTermAgreements", memberId);
  }
}
