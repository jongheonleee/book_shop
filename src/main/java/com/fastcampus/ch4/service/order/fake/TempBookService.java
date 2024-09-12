package com.fastcampus.ch4.service.order.fake;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;

import java.util.List;

public interface TempBookService {
    TempBookDto read(String isbn);
    List<TempBookDto> getBookList ();
}
