package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.BookSearchCondition;
import com.fastcampus.ch4.dto.item.WritingContributorDto;

import java.util.List;
import java.util.Map;

public interface BookDao {
    int countBook();
    int countBookImage();

    int countBookContributor();

    int countWritingContributor();
    int countBookDiscHist();

    int deleteAllBook();
    int deleteAllWritingContributor();
    int deleteAllBookContributor();
    int deleteAllBookImage();
    int deleteAllBookDiscHist();

    int deleteBook(String isbn, String writer);
    int deleteBookImage(String isbn, String writer);
    int deleteBookDiscHist(String isbn, String writer);
    int deleteBookContributor(String isbn, String writer);
    int deleteWritingContributor(String isbn, String writer);

    int deleteBookForAdmin(String isbn);
    int deleteBookImageForAdmin(String isbn);
    int deleteBookDiscHistForAdmin(String isbn);
    int deleteBookContributorForAdmin(String isbn);
    int deleteWritingContributorForAdmin(String isbn);

    int insertBook(BookDto dto);

    List<BookDto> selectAllBook();

    BookDto selectBook(String isbn);

    int updateBook(BookDto dto);

    int increaseSaleVol(Map map);

    List<BookDto> selectPage(Map map);


    int insertBookImage(BookImageDto bookImageDto);
    int insertWritingContributor(WritingContributorDto writingContributorDto);
    int insertBookContributor(Map map);

    Integer selectMaxImgSeq(String isbn);
    Integer selectWrSeq();
    Integer selectTrlSeq();

    int searchResultCnt(BookSearchCondition bsc);

    List<BookDto> searchSelectPage(BookSearchCondition bsc);
}
