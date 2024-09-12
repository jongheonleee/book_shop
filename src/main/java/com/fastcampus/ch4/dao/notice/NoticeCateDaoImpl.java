package com.fastcampus.ch4.dao.notice;

import com.fastcampus.ch4.dto.notice.NoticeCateDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeCateDaoImpl implements NoticeCateDao {

    String namespace = "com.fastcampus.ch4.dao.notice.NoticeCateMapper.";

    private final SqlSession sqlSession;

    @Autowired
    public NoticeCateDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int count() {
        return sqlSession.selectOne(namespace + "count");
    }

    @Override
    public int insert(NoticeCateDto noticeCateDto) {
        return sqlSession.insert(namespace + "insert", noticeCateDto);
    }

    @Override
    public NoticeCateDto selectById(Integer ntc_cate_id) {
        return sqlSession.selectOne(namespace + "selectById", ntc_cate_id);
    }

    @Override
    public NoticeCateDto selectByCode(String ntc_cate_code) {
        return sqlSession.selectOne(namespace + "selectByCode", ntc_cate_code);
    }

    @Override
    public List<NoticeCateDto> selectAll() {
        return sqlSession.selectList(namespace + "selectAll");
    }

    @Override
    public Integer selectIdByCode(String ntc_cate_code) {
        return sqlSession.selectOne(namespace + "selectIdByCode", ntc_cate_code);
    }

    @Override
    public int update(NoticeCateDto noticeCateDto) {
        return sqlSession.update(namespace + "update", noticeCateDto);
    }

    @Override
    public int delete(Integer ntc_cate_id) {
        return sqlSession.delete(namespace + "delete", ntc_cate_id);
    }

    @Override
    public int deleteAll() {
        return sqlSession.delete(namespace + "deleteAll");
    }
}
