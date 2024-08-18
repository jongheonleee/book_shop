package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.domain.qa.SearchCondition;
import com.fastcampus.ch4.dto.qa.QaStateDto;
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

    public int countBySearchCondition(String user_id, SearchCondition sc) {
        Map map = new HashMap();

        map.put("user_id", user_id);
        map.put("option", sc.getOption());
        map.put("titleKeyword", sc.getTitleKeyword());
        map.put("period", sc.getPeriod());

        return session.selectOne(namespace + "countBySearchCondition", map);
    }

    // (8) 문의글 일련번호로 조회
    @Override
    public QaDto select(int qa_num) {
        return session.selectOne(namespace + "select", qa_num);
    }

    public List<QaDto> selectAll() {
        return session.selectList(namespace + "selectAll");
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
        session.delete(namespace + "deleteAllForQaState");
        return session.delete(namespace + "deleteAll");
    }

    /**
     * 3차 기능 요구 사항 정리
     * - (1) 문의글 상태 등록
     * - (2) 문의글 상태 조회 (모두 조회)
     * - (3) 특정 문의글 상태 이력 조회(해당 문의글 상태 이력)
     * - (4) 특정 문의글에 한 가지 상태 조회
     * - (5) 특정 문의글 상태 내용 업데이트
     * - (6) 문의글 상태 모두 삭제
     * - (7) 특정 문의글에 대한 상태 이력 모두 삭제
     * - (8) 특정 문의글의 한 가지 상태 삭제
     **/

    public int insertState(QaStateDto dto) {
        return session.insert(namespace + "insertForQaState", dto);
    }

    public List<QaStateDto> selectStateByQaNum(int qaNum) {
        return session.selectList(namespace + "selectForQaStateByQaNum", qaNum);
    }

    public List<QaStateDto> selectAllState() {
        return session.selectList(namespace + "selectAllState");
    }

    public QaStateDto selectStateForLast(int qaNum) {
        return session.selectOne(namespace + "selectStateForLast", qaNum);
    }

    public int updateState(QaStateDto dto) {
        return session.update(namespace + "updateForQaState", dto);
    }

    public int deleteState(int qa_stat_seq) {
        return session.delete(namespace + "deleteForQaState", qa_stat_seq);
    }

    public int deleteStateByQaNum(int qaNum) {
        return session.delete(namespace + "deleteForQaStateByQaNum", qaNum);
    }

    public int selectMaxQaSeq() {
        return session.selectOne(namespace + "selectMaxQaSeq");
    }

    public QaDto selectByTitle(String userId, String title) {
        HashMap map = new HashMap();
        map.put("user_id", userId);
        map.put("title", title);
        return session.selectOne(namespace + "selectByTitle", map);
    }

    public int countByState(String userId, String qa_stat_code) {
        HashMap map = new HashMap();
        map.put("user_id", userId);
        map.put("qa_stat_code", qa_stat_code);

        return session.selectOne(namespace + "countByState", map);
    }

    public List<QaDto> selectByState(String userId, String qa_stat_code, SearchCondition sc) {
        HashMap map = new HashMap();
        map.put("user_id", userId);
        map.put("qa_stat_code", qa_stat_code);
        map.put("offSet", (sc.getPage() - 1) * sc.getPageSize());
        map.put("pageSize", sc.getPageSize());

        return session.selectList(namespace + "selectByState", map);
    }

}
