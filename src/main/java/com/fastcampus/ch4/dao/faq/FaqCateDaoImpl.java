package com.fastcampus.ch4.dao.faq;

import com.fastcampus.ch4.dto.faq.FaqCateDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FaqCateDaoImpl implements FaqCateDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.faq.FaqCateMapper.";

    @Override
    public int count() {
        return sqlSession.selectOne(namespace + "count");
    }

    @Override
    public FaqCateDto selectByCode(String cate_code) {
        return sqlSession.selectOne(namespace + "selectByCode", cate_code);
    }

    @Override
    public List<FaqCateDto> selectChildByParent(String cate_code) {
        return sqlSession.selectList(namespace + "selectChildByParent", cate_code);
    }

    @Override
    public List<FaqCateDto> selectChildAndParent(String cate_code) {
        return sqlSession.selectList(namespace + "selectChildAndParent", cate_code);
    }

    @Override
    public FaqCateDto selectByName(String name) {
        return sqlSession.selectOne(namespace + "selectByName", name);
    }

    @Override
    public List<FaqCateDto> selectMainCate() {
        return sqlSession.selectList(namespace + "selectMainCate");
    }

    @Override
    public List<FaqCateDto> selectSubCate() {
        return sqlSession.selectList(namespace + "selectSubCate");
    }

    @Override
    public List<FaqCateDto> selectAll() {
        return sqlSession.selectList(namespace + "selectAll");
    }

    @Override
    public int deleteAll() {
        return sqlSession.delete(namespace + "deleteAll");
    }

    @Override
    public int insert(FaqCateDto faqCateDto) {
        return sqlSession.insert(namespace + "insert", faqCateDto);
    }

    @Override
    public int update(FaqCateDto faqCateDto) {
        return sqlSession.update(namespace + "update", faqCateDto);
    }
}
