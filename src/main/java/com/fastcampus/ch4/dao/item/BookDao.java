package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.BookSearchCondition;
import com.fastcampus.ch4.dto.item.WritingContributorDto;

import java.util.List;
import java.util.Map;

public interface BookDao {
    int count() throws Exception;

    int deleteAll() throws Exception;

    int delete(String isbn) throws Exception;

    int insert(BookDto dto) throws Exception;

    List<BookDto> selectAll() throws Exception;

    BookDto select(String isbn) throws Exception;

    int update(BookDto dto) throws Exception;

    int increaseSaleVol(Map map) throws Exception;

    List<BookDto> selectPage(Map map) throws Exception;


    int insertToBookImage(BookImageDto bookImageDto) throws Exception;
    int insertToWritingContributor(WritingContributorDto writingContributorDto) throws Exception;
    int insertToBookContributor(Map map) throws Exception;

    Integer selectMaxImgSeq(String isbn) throws Exception;
    Integer selectWrSeq() throws Exception;
    Integer selectTrlSeq() throws Exception;

    int searchResultCnt(BookSearchCondition bsc) throws Exception;

    List<BookDto> searchSelectPage(BookSearchCondition bsc) throws Exception;
    int deleteAllFromWritingContributor() throws Exception;
    int deleteAllFromBookContributor() throws Exception;
}
