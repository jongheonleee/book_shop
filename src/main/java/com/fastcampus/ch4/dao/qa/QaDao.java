package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.domain.qa.SearchCondition;
import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.dto.qa.QaStateDto;
import java.util.List;

public interface QaDao {

    // (1) 유저의 문의글 카운팅
    int count(String user_id);

    int countBySearchCondition(String user_id, SearchCondition sc);

    // (8) 문의글 일련번호로 조회
    QaDto select(int qa_num);

    List<QaDto> selectAll();

    // (3) 유저의 문의글 등록
    int insert(QaDto dto);

    // (2) 유저의 문의글 리스트 조회
    List<QaDto> selectByUserId(String user_id);

    // (6) 구간별로 문의글 조회(페이징 처리)
    List<QaDto> selectByUserIdAndPh(String user_id, SearchCondition sc);

    QaDto selectForUpdate(int qa_num);

    // (7) 글 검색 - 기간, 제목, 기간 & 제목 대상으로 글 검색
    List<QaDto> selectBySearchCondition(String user_id, SearchCondition sc);

    // (4) 유저의 문의글 수정
    int update(QaDto dto);

    // (5) 유저의 문의글 삭제
    int delete(QaDto dto);

    int deleteAll();

    int insertState(QaStateDto dto);

    List<QaStateDto> selectStateByQaNum(int qaNum);

    List<QaStateDto> selectAllState();

    QaStateDto selectStateForLast(int qaNum);

    int updateState(QaStateDto dto);

    int deleteState(int qa_stat_seq);

    int deleteStateByQaNum(int qaNum);

    int selectMaxQaSeq();

    QaDto selectByTitle(String userId, String title);

    int countByState(String userId, String qa_stat_code);

    List<QaDto> selectByState(String userId, String qa_stat_code, SearchCondition sc);

    QaStateDto selectForUpdateState(int qaNum);
}
