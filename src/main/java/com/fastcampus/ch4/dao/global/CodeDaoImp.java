package com.fastcampus.ch4.dao.global;

import com.fastcampus.ch4.dto.global.CodeDto;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CodeDaoImp implements CodeDao {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.CodeMapper.";


    @Override
    public int insert(CodeDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    @Override
    public List<CodeDto> selectByCate(String cate_num) {
        return session.selectList(namespace + "selectByCate", cate_num);
    }

    @Override
    public CodeDto selectByCode(String code) {
        return session.selectOne(namespace + "selectByCode", code);
    }

    @Override
    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

    @Override
    public int delete(String code) {
        return session.delete(namespace + "delete", code);
    }



}
