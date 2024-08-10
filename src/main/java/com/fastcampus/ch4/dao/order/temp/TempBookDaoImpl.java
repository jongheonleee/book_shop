package com.fastcampus.ch4.dao.order.temp;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TempBookDaoImpl implements TempBookDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.order.temp.TempBookMapper.";

    @Override
    public TempBookDto selectByIsbn(String isbn) {
        return sqlSession.selectOne("selectByIsbn", isbn);
    }
}
