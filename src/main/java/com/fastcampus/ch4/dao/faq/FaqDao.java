package com.fastcampus.ch4.dao.faq;

import com.fastcampus.ch4.domain.faq.SearchCondition;
import com.fastcampus.ch4.dto.faq.FaqDto;

import java.util.List;
import java.util.Map;

public interface FaqDao {
    int count();

    int deleteAll();

    int delete(int faq_seq);

    int deleteByRegId(int faqSeq, String adminId);

    int insert(FaqDto faqDto);

    int update(FaqDto faqDto);

    FaqDto select(Integer faq_seq);

    List<FaqDto> selectAll();

    List<FaqDto> selectDisplay();

    List<FaqDto> search(SearchCondition sc);

    List<FaqDto> selectByCatg(String faq_catg_code);

    int increaseViewCnt(int feqSeq);

    List<FaqDto> selectPage(Map map);
}
