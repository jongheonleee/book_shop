package com.fastcampus.ch4.service.order.fake;

import com.fastcampus.ch4.dto.item.BookDto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeBookServiceImpl implements TempBookService {
    private List<BookDto> BookDtoList = new ArrayList<>();

    @Override
    public BookDto read(String isbn) {
        for (BookDto bookDto : BookDtoList) {
            if (bookDto.getIsbn().equals(isbn)) {
                return bookDto;
            }
        }
        return null;
    }

    @Override
    public List<BookDto> getBookList() {
        for (int i = 0; i <= 20; i++) {
            BookDtoList.add(new BookDto(
                    "isbn" + i,                  // isbn
                    "01",                       // cate_num
                    "pub_name" + i,              // pub_name
                    "book_title" + i,            // title
                    "2024-08-07 15:35:58",       // pub_date
                    "Available",                 // sale_stat
                    i,                           // sale_vol
                    i,                           // papr_pric
                    5.0,                         // e_pric
                    5.0,                         // papr_point
                    5.0,                         // e_point
                    i,                           // tot_page_num
                    i,                           // tot_book_num
                    "",                          // sale_com
                    "",                          // cont
                    4.5,                         // rating
                    "",                          // info
                    "",                          // intro_award
                    "",                          // rec
                    "",                          // pub_review
                    i,                           // pre_start_page
                    i,                           // pre_end_page
                    "",                          // ebook_url
                    new Date(),                  // book_reg_date
                    "test",                      // regi_id
                    new Date(),                  // reg_date
                    "test",                      // reg_id
                    new Date(),                  // up_date
                    "test",                      // up_id
                    "repre_img" + i,             // repre_img_url
                    i,                           // papr_disc
                    i,                           // e_disc
                    "whol_layr_name" + i,        // whol_layr_name
                    "wr_cb_num" + i,             // cb_num
                    "trl_cb_num" + i,            // trl_cb_num
                    "wr_name" + i,               // wr_name
                    "trl_name" + i               // trl_name
            ));
        }
        return BookDtoList;
    }
}