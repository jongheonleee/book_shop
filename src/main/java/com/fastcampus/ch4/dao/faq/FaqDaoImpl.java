package com.fastcampus.ch4.dao.faq;

import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.dto.faq.FaqDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FaqDaoImpl implements FaqDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.faq.FaqMapper.";

    @Override
    public int count() {
        return sqlSession.selectOne(namespace + "count");
    }

    @Override
    public int deleteAll() {
        return sqlSession.delete(namespace + "deleteAll");
    }

    @Override
    public int delete(int faq_seq) {
        return sqlSession.delete(namespace + "delete", faq_seq);
    }

    @Override
    public int deleteByRegId(int faq_seq, String adminId) {
        Map map = new HashMap();
        map.put("faq_seq", faq_seq);
        map.put("admin_id", adminId);
        return sqlSession.delete(namespace + "deleteByRegId", map);
    }

    @Override
    public int insert(FaqDto faqDto) {
        return sqlSession.insert(namespace + "insert", faqDto);
    }

    @Override
    public int update(FaqDto faqDto) {
        return sqlSession.update(namespace + "update", faqDto);
    };

    @Override
    public FaqDto select(Integer faq_seq) {
        return sqlSession.selectOne(namespace + "select", faq_seq);
    }

    @Override
    public List<FaqDto> selectAll() {
        return sqlSession.selectList(namespace + "selectAll");
    }

    @Override
    public List<FaqDto> selectDisplay() {
        return sqlSession.selectList(namespace + "selectDisplay");
    }

    @Override
    public List<FaqDto> search(SearchCondition sc) {
        return sqlSession.selectList(namespace + "search", sc);
    };

    @Override
    public List<FaqDto> selectByCatg(String faq_catg_code) {
        return sqlSession.selectList(namespace + "selectByCatg", faq_catg_code);
    }

    @Override
    public int increaseViewCnt(int feq_seq) {
        return sqlSession.update(namespace + "increaseViewCnt", feq_seq);
    };

    @Override
    public List<FaqDto> selectPage(Map map) {
        return sqlSession.selectList(namespace + "selectPage", map);
    }
}
