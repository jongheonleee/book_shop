package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.QaDto;
import java.util.List;

public interface QaDao {

    int count(String user_id);

    int insert(QaDto dto);

    List<QaDto> selectByUserId(String user_id);

    QaDto selectForUpdate(QaDto dto);

    int update(QaDto dto);

    int delete(QaDto dto);

    int deleteAll();
}
