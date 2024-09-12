package com.fastcampus.ch4.service.faq;

import com.fastcampus.ch4.domain.faq.SearchCondition;
import com.fastcampus.ch4.dto.faq.FaqDto;

import java.util.List;

public interface FaqService {

    // 전체 게시글 건수 확인
    public int count();

    // 게시글 읽는 기능
    FaqDto read(int faq_seq);

    // 게시글 전체 목록 읽기
    List<FaqDto> readAll();

    // 노출되는 게시글 목록 읽기
    List<FaqDto> readDisplay();

    // 게시글 등록 기능
    int write(FaqDto faqDto);

    // 게시글 삭제 기능
    int remove(int faq_seq, String admin_id);

    // 게시글 전체 삭제 기능
    int removeAll(String admin_id);

    // 게시글 수정 기능
    int modify(FaqDto faqDto);

    // 키워드로 게시글 검색 기능
    List<FaqDto> searchByKeyword(SearchCondition condition);

    // 특정 카테고리의 게시글 목록 읽기
    List<FaqDto> readByCatgCode(String faq_catg_code);
}
