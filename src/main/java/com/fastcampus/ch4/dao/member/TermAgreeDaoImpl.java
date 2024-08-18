package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TermAgreeDaoImpl implements TermAgreeDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.fastcampus.ch4.dao.member.TermAgreeDao";

  @Override
  public List<TermAgreeDto> getTermAgreements(String memberId) {
    return sqlSession.selectList(NAMESPACE + ".getTermAgreements", memberId);
  }

  @Override
  public void insertTermAgreements(List<TermAgreeDto> termAgreeDto) {
    sqlSession.insert(NAMESPACE + ".insertTermAgreement", termAgreeDto);
  }

  @Override
  public void updateTermAgreement(TermAgreeDto termAgreeDto) {
    sqlSession.update(NAMESPACE + ".updateTermAgreement", termAgreeDto);
  }

  @Override
  public void deleteTermAgreement(String memberId, int termId) {
    sqlSession.delete(NAMESPACE + ".deleteTermAgreement", Map.of("id", memberId, "termId", termId));
  }

  @Override
  public void deleteAllTermAgreements() {
    sqlSession.delete(NAMESPACE + ".deleteAllTermAgreements");
  }

  @Override
  public TermAgreeDto getTermAgree(String memberId, int termId) {
    return sqlSession.selectOne(NAMESPACE + ".getTermAgree", Map.of("id", memberId, "termId", termId));
  }
}
