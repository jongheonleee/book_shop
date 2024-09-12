package com.fastcampus.ch4.dao.notice;

import com.fastcampus.ch4.dto.notice.NoticeCateDto;

import java.util.List;

public interface NoticeCateDao {
    int count();

    int insert(NoticeCateDto noticeCateDto);

    NoticeCateDto selectById(Integer ntc_cate_id);

    NoticeCateDto selectByCode(String ntc_cate_code);

    List<NoticeCateDto> selectAll();

    Integer selectIdByCode(String ntc_cate_code);

    int update(NoticeCateDto noticeCateDto);

    int delete(Integer ntc_cate_id);

    int deleteAll();
}
