package com.fastcampus.ch4.service.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookSearchCondition;
import com.fastcampus.ch4.dto.item.CategoryDto;

import java.util.List;
import java.util.Map;

public interface BookService {
    // 도서 개수 카운트
    int getCountBook();

    // 도서글 쓰기
    void register(BookDto bookDto);

    // 도서글 읽기
    BookDto read(String isbn);

    // 도서목록 가져오기
    List<BookDto> getList();

    // 도서글 수정
    int modify(BookDto bookDto);

    // 도서글 삭제 - 작성자
    boolean remove(String isbn, String writer);

    // 도서글 삭제 - 관리자
    public boolean removeForAdmin(String isbn);

    // 선택된 페이지 가져오기.
    List<BookDto> getPage(Map map);

    // 도서 목록 전체 조회
    List<BookDto> getAllBookList();

    // 판매량 증가
    int increaseSaleVol(Map map);

    // 검색된 페이지 가져오기
    List<BookDto> getSearchResultPage(BookSearchCondition bsc);

    // 검색된 총 도서 개수 카운트
    int getSearchResultCnt(BookSearchCondition bsc);

    // 전체 카테고리 리스트 받아오기
    List<CategoryDto> getCategoryList();

}
