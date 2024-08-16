package com.fastcampus.ch4.dao.faq;

import com.fastcampus.ch4.dto.faq.FaqCateDto;

import java.util.List;

public interface FaqCateDao {
    int count();

    FaqCateDto selectByCode(String cate_code);

    List<FaqCateDto> selectChildByParent(String cate_code);

    List<FaqCateDto> selectChildAndParent(String cate_code);

    FaqCateDto selectByName(String name);

    List<FaqCateDto> selectAll();

    List<FaqCateDto> selectMainCate();

    List<FaqCateDto> selectSubCate();

    int deleteAll();

    int insert(FaqCateDto faqCateDto);

    int update(FaqCateDto faqCateDto);
}
