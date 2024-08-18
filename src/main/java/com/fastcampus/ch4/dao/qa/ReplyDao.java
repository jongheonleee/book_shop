package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.ReplyDto;
import java.util.List;

public interface ReplyDao {

    int insert(ReplyDto dto);

    int deleteAll();

    int count();

    ReplyDto select(int qa_num);

    List<ReplyDto> selectAll();

    int delete(int qa_num);

    int update(ReplyDto dto);
}
