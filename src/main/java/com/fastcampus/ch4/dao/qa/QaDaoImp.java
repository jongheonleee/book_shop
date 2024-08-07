package com.fastcampus.ch4.dao.qa;


import static com.fastcampus.ch4.code.error.qa.QaErrorCode.*;

import com.fastcampus.ch4.dto.qa.PageHandler;
import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.exeption.qa.QaDuplicatedKeyException;
import com.fastcampus.ch4.exeption.qa.QaInvalidValueException;
import com.fastcampus.ch4.exeption.qa.QaNoRequiredValueException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class QaDaoImp implements QaDao {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.QaMapper.";


    @Override
    public int count(String user_id) {
        return session.selectOne(namespace + "count", user_id);
    }

    // 사용자 문의 등록시 발생하는 예외 세분화해서 재정의 반환
    @Override
    public int insert(QaDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    @Override
    public List<QaDto> selectByUserId(String user_id) {
        return session.selectList(namespace + "selectByUserId", user_id);
    }

    @Override
    public QaDto selectForUpdate(QaDto dto) {
        return session.selectOne(namespace + "selectForUpdate", dto);
    }

    @Override
    public int update(QaDto dto) {
        return session.update(namespace + "update", dto);
    }

    @Override
    public int delete(QaDto dto) {
        return session.delete(namespace + "delete", dto);
    }

    @Override
    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }


}
