package com.fastcampus.ch4.service.faq;

import com.fastcampus.ch4.dto.faq.FaqCateDto;

import java.util.List;

public interface FaqCateService {

    List<FaqCateDto> readAll();

    List<FaqCateDto> readChildByParent(String cateCode);

    List<FaqCateDto> readChildAndParent(String cateCode);

    List<FaqCateDto> readMainCate();

    List<FaqCateDto> readSubCate();

    int create(FaqCateDto dto);

}
