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


    final double TEMP_BENEFIT_PERCENT = 0.1;

    /*
    여기에서 가격 처리를 하는 것이 좋다.
     */
    @Override
    public TempBookDto selectByIsbn(String isbn) {
        TempBookDto bookDto = sqlSession.selectOne("selectByIsbn", isbn);
        return bookDto;
    }
}
