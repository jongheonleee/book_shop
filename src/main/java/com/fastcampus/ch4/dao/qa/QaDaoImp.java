package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.domain.qa.SearchCondition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QaDaoImp implements QaDao {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.QaMapper.";

    /**
     *  1차 기능 요구 사항 정리
     *  - (1) 유저의 문의글 카운팅
     *  - (2) 유저의 문의글 리스트 조회
     *  - (3) 유저의 문의글 등록
     *  - (4) 유저의 문의글 수정
     *  - (5) 유저의 문의글 삭제
     *  - (6) 구간별로 문의글 조회(페이징 처리)
     *  - (7) 글 검색 - 기간, 제목, 기간 & 제목 대상으로 글 검색
     *  - (8) 문의글 일련번호로 조회
     **/

    // (1) 유저의 문의글 카운팅
    @Override
    public int count(String user_id) {
        return session.selectOne(namespace + "count", user_id);
    }

    // (8) 문의글 일련번호로 조회
    @Override
    public QaDto select(int qa_num) {
        return session.selectOne(namespace + "select", qa_num);
    }

    // (3) 유저의 문의글 등록
    @Override
    public int insert(QaDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    // (2) 유저의 문의글 리스트 조회
    @Override
    public List<QaDto> selectByUserId(String user_id) {
        return session.selectList(namespace + "selectByUserId", user_id);
    }

    // (6) 구간별로 문의글 조회(페이징 처리)
    @Override
    public List<QaDto> selectByUserIdAndPh(String user_id, SearchCondition sc) {
        Map map = new HashMap();

        map.put("user_id", user_id);
        map.put("offSet", (sc.getPage() - 1) * sc.getPageSize());
        map.put("pageSize", sc.getPageSize());

        return session.selectList(namespace + "selectByUserIdAndPh", map);
    }


    @Override
    public QaDto selectForUpdate(QaDto dto) {
        return session.selectOne(namespace + "selectForUpdate", dto);
    }


    // (7) 글 검색 - 기간, 제목, 기간 & 제목 대상으로 글 검색
    @Override
    public List<QaDto> selectBySearchCondition(String user_id, SearchCondition sc) {
        Map map = new HashMap();

        map.put("user_id", user_id);
        map.put("option", sc.getOption());
        map.put("titleKeyword", sc.getTitleKeyword());
        map.put("period", sc.getPeriod());
        map.put("offSet", (sc.getPage() - 1) * sc.getPageSize());
        map.put("pageSize", sc.getPageSize());

        return session.selectList(namespace + "selectBySearchCondition", map);
    }

    // (4) 유저의 문의글 수정
    @Override
    public int update(QaDto dto) {
        return session.update(namespace + "update", dto);
    }

    // (5) 유저의 문의글 삭제
    @Override
    public int delete(QaDto dto) {
        return session.delete(namespace + "delete", dto);
    }

    @Override
    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }


}
