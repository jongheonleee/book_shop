package com.fastcampus.ch4.service.notice;

import com.fastcampus.ch4.dto.notice.NoticeCateDto;

public interface NoticeCateService {
    int count();

    int save(NoticeCateDto noticeCateDto);

    Integer getIdByCode(String code);

    int removeAll();
}
