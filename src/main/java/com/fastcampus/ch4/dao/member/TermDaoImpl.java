package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TermDaoImpl implements TermDao {

  private final SqlSession sqlSession;

  @Autowired
  public TermDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public List<TermDto> getAllTerms() {
    return sqlSession.selectList("com.fastcampus.ch4.dao.member.TermDao.getAllTerms");
  }

  @Override
  public TermDto getTermById(int termId) {
    return sqlSession.selectOne("com.fastcampus.ch4.dao.member.TermDao.getTermById", termId);
  }

  @Override
  public List<Integer> getAllTermIds() {
    return sqlSession.selectList("com.fastcampus.ch4.dao.member.TermDao.getAllTermIds");
  }

  @Override
  public void insertTermAgreements(List<TermDto> termDtos) {

      sqlSession.insert("com.fastcampus.ch4.dao.member.TermDao.insertTermAgreements", termDtos);

  }

  @Override
  public void deleteTermById(int termId) {
    sqlSession.delete("com.fastcampus.ch4.dao.member.TermDao.deleteTerm", termId);
  }

  @Override
  public void deleteAllTerms() {
    sqlSession.delete("com.fastcampus.ch4.dao.member.TermDao.deleteAllTerms");
  }

  @Override
  public TermDto getTermByName(String termName) {
    return sqlSession.selectOne("com.fastcampus.ch4.dao.member.TermDao.getTermByName", termName);
  }

  @Override
  public void updateTerm(TermDto termDto) {
    sqlSession.update("com.fastcampus.ch4.dao.member.TermDao.updateTerm", termDto);
  }
}
