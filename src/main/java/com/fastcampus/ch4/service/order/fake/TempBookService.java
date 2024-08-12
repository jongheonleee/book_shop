package com.fastcampus.ch4.service.order.fake;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;

public interface TempBookService {
    TempBookDto getBookByIsbn (String isbn);
}
