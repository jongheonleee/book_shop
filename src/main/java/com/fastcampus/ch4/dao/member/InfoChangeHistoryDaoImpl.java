package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InfoChangeHistoryDaoImpl implements InfoChangeHistoryDao {

  private static final String NAMESPACE = "com.fastcampus.ch4.mapper.InfoChangeHistoryMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public void insertChangeHistory(InfoChangeHistoryDto infoChangeHistory) {
    sqlSession.insert(NAMESPACE + ".insertChangeHistory", infoChangeHistory);
  }

  @Override
  public List<InfoChangeHistoryDto> getChangeHistoryById(String id) {
    return sqlSession.selectList(NAMESPACE + ".getChangeHistoryById", id);
  }

  @Override
  public void saveChangeHistory(InfoChangeHistoryDto changeLog) {
    sqlSession.insert(NAMESPACE+".saveChangeHistory",changeLog);
  }

  @Override
  public void deleteAllChangeHistories() {
    sqlSession.delete(NAMESPACE+".deleteAllChangeHistories");
  }

}

