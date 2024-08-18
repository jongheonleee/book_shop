package com.fastcampus.ch4.dao.admin;

import com.fastcampus.ch4.dto.admin.AdminDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.admin.AdminMapper.";

    @Override
    public int count() {
        return sqlSession.selectOne(namespace + "count");
    }

    @Override
    public AdminDto select(String id) {
        return sqlSession.selectOne(namespace + "select", id);
    }

    @Override
    public List<AdminDto> selectAll() {
        return sqlSession.selectList(namespace + "selectAll");
    }

    @Override
    public int delete(String id) {
        return sqlSession.delete(namespace + "delete", id);
    }

    @Override
    public int deleteAll() {
        return sqlSession.delete(namespace + "deleteAll");
    }

    @Override
    public int insert(AdminDto adminDto) {
        return sqlSession.insert(namespace + "insert", adminDto);
    }
}
