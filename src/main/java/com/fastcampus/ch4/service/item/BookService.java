package com.fastcampus.ch4.service.item;

import com.fastcampus.ch4.dto.item.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {
    // 1. 도서 개수 카운트
    int getCount() throws Exception;

    // 2. 도서글 쓰기
    int write(BookDto bookDto) throws Exception;

    // 3. 도서글 읽기
    BookDto read(String isbn) throws Exception;

    // 4. 도서목록 가져오기
    List<BookDto> getList() throws Exception;

    // 5. 도서글 수정
    int modify(BookDto bookDto) throws Exception;

    // 6. 도서글 삭제 - isbn 일치 확인
    int remove(String isbn) throws Exception;

    // 7. 선택된 페이지 가져오기.
    List<BookDto> getPage(Map map) throws Exception;

    // 8. 전체 목록 초기화
    int resetList() throws Exception;

    // 9. 도서 목록 전체 조회
    List<BookDto> getAllBookList() throws Exception;

    // 10. 판매량 증가
    int increaseSaleVol(String isbn) throws Exception;
}
