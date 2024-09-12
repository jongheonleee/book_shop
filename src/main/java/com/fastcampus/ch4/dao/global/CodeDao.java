package com.fastcampus.ch4.dao.global;

import com.fastcampus.ch4.dto.global.CodeDto;
import java.util.List;

public interface CodeDao {

    int insert(CodeDto dto);

    List<CodeDto> selectByCate(String cate_num);

    CodeDto selectByCode(String code);

    CodeDto selectByCodeName(String codeName);

    int deleteAll();

    int delete(String code);
}
