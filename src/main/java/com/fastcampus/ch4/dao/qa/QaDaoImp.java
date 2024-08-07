package com.fastcampus.ch4.dao.qa;


import com.fastcampus.ch4.dto.qa.QaDto;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QaDaoImp {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.QaMapper.";


    public int count(String user_id) {
        return session.selectOne(namespace + "count", user_id);
    }

    public int insert(QaDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    public List<QaDto> selectByUserId(String user_id) {
        return session.selectList(namespace + "selectByUserId", user_id);
    }

    public QaDto selectForUpdate(QaDto dto) {
        return session.selectOne(namespace + "selectForUpdate", dto);
    }

    public int update(QaDto dto) {
        return session.update(namespace + "update", dto);
    }

    public int delete(QaDto dto) {
        return session.delete(namespace + "delete", dto);
    }

    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

}
