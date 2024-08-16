package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import java.util.List;

public interface QaCategoryDao {

    int count();

    int insert(QaCategoryDto dto);

    int deleteAll();

    List<QaCategoryDto> selectAll();

    QaCategoryDto select(String qa_cate_num);

    List<QaCategoryDto> selectByChkUse(String chk_use);

    int delete(String qa_cate_num);

    int update(QaCategoryDto dto);
}
