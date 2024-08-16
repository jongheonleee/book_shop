package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.LoginHistoryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LoginHistoryDaoImpl implements LoginHistoryDao {

  private final SqlSession sqlSession;

  private static final String NAMESPACE = "com.fastcampus.ch4.dao.member.LoginHistoryDao.";

  @Autowired
  public LoginHistoryDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 로그인 기록 저장 구현
  @Override
  public int insertLoginHistory(LoginHistoryDto loginHistoryDto) {
    return sqlSession.insert(NAMESPACE + "insertLoginHistory", loginHistoryDto);
  }

  // 로그인 기록 조회 구현 (ID로 조회)
  @Override
  public List<LoginHistoryDto> selectLoginHistoryById(String id) {
    return sqlSession.selectList(NAMESPACE + "selectLoginHistoryById", id);
  }

  // 모든 로그인 기록 조회 구현
  @Override
  public List<LoginHistoryDto> getAllLoginHistories() {
    return sqlSession.selectList(NAMESPACE + "getAllLoginHistories");
  }

  // 모든 로그인 기록 삭제 구현
  @Override
  public void deleteAll() {
    sqlSession.delete(NAMESPACE + "deleteAll");
  }
}