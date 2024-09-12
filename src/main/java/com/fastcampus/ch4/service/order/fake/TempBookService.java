package com.fastcampus.ch4.service.order.fake;

import com.fastcampus.ch4.dto.item.BookDto;

import java.util.List;

public interface TempBookService {
    BookDto read(String isbn);
    List<BookDto> getBookList();
}
