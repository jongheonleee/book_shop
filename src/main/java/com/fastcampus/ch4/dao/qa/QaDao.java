package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.dto.qa.SearchCondition;
import java.util.List;

public interface QaDao {

    int count(String user_id);

    QaDto select(int qa_num);

    // 사용자 문의 등록시 발생하는 예외 세분화해서 재정의 반환
    int insert(QaDto dto);

    List<QaDto> selectByUserId(String user_id);

    List<QaDto> selectByUserIdAndPh(String user_id, SearchCondition sc);

    QaDto selectForUpdate(QaDto dto);

    List<QaDto> selectBySearchCondition(String user_id, SearchCondition sc);

    int update(QaDto dto);

    int delete(QaDto dto);

    int deleteAll();
}
