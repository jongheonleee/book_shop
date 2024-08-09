package com.fastcampus.ch4.dao.qa;


import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QaCategoryDaoImp {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.QaCategoryMapper.";

    /**
     *  1차 기능 요구 사항 정리
     * - (0) 문의 카테고리 카운팅
     * - (1) 문의 카테고리를 등록함
     * - (2) 문의 카테고리를 모두 조회함
     * - (3) 문의 카테고리를 검색(카테고리 번호)
     * - (4) 문의 카테고리 사용 가능 여부에 따라서 조회함
     * - (5) 문의 카테고리를 업데이트함
     * - (6) 문의 카테고리를 삭제함
     * - (7) 문의 카테고리 모두를 삭제함
     **/

    public int count() {
        return session.selectOne(namespace + "count");
    }

    public int insert(QaCategoryDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

    public List<QaCategoryDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }

    public QaCategoryDto select(String qa_cate_num) {
        return session.selectOne(namespace + "select", qa_cate_num);
    }

    public List<QaCategoryDto> selectByChkUse(String chk_use) {
        return session.selectList(namespace + "selectByChkUse", chk_use);
    }

    public int delete(String qa_cate_num) {
        return session.delete(namespace + "delete", qa_cate_num);
    }

    public int update(QaCategoryDto dto) {
        return session.update(namespace + "update", dto);
    }


}
