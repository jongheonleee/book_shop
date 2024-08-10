package com.fastcampus.ch4.dao.order.temp;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;

public interface TempBookDao {
    public TempBookDto selectByIsbn(String isbn);
}
