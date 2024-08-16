package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TermDaoImpl implements TermDao {

  // SqlSession 객체를 자동 주입합니다. MyBatis와의 세션 관리에 사용됩니다.
  @Autowired
  private SqlSession sqlSession;

  // MyBatis의 네임스페이스를 정의합니다. XML 매퍼 파일과의 연동에 사용됩니다.
  private static final String NAMESPACE = "com.fastcampus.ch4.dao.member.TermDao";

  // TermDto 객체를 데이터베이스에 삽입합니다.
  @Override
  public void insertTerm(TermDto term) {
    sqlSession.insert(NAMESPACE + ".insertTerm", term);
  }

  // 주어진 ID에 해당하는 TermDto 객체를 데이터베이스에서 조회합니다.
  @Override
  public TermDto getTermById(int termId) {
    return sqlSession.selectOne(NAMESPACE + ".getTermById", termId);
  }

  // TermDto 객체의 정보를 업데이트합니다.
  @Override
  public void updateTerm(TermDto term) {
    sqlSession.update(NAMESPACE + ".updateTerm", term);
  }

  // 주어진 ID에 해당하는 TermDto 객체를 데이터베이스에서 삭제합니다.
  @Override
  public void deleteTerm(int termId) {
    sqlSession.delete(NAMESPACE + ".deleteTerm", termId);
  }

  // 데이터베이스의 모든 TermDto 객체를 삭제합니다.
  @Override
  public void deleteAllTerms() {
    sqlSession.delete(NAMESPACE + ".deleteAllTerms");
  }

  // 데이터베이스에서 모든 TermDto 객체를 조회하여 리스트로 반환합니다.
  @Override
  public List<TermDto> getAllTerms() {
    return sqlSession.selectList(NAMESPACE + ".getAllTerms");
  }

  @Override
  public List<Integer> getAllTermIds() {
    return sqlSession.selectList(NAMESPACE + ".getAllTermIds");
  }

  @Override
  public void insertTermAgreements(List<TermAgreeDto> termAgreeDtos) {
    sqlSession.insert(NAMESPACE+".insertTermAgreements", termAgreeDtos);
  }
}
