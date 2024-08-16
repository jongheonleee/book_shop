package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.WritingContributorDto;

import java.util.List;
import java.util.Map;

public interface BookDao {
    int count() throws Exception;

    int deleteAll();

    int delete(String isbn) throws Exception;

    int insert(BookDto dto) throws Exception;

    List<BookDto> selectAll() throws Exception;

    BookDto select(String isbn) throws Exception;

    int update(BookDto dto) throws Exception;

    int increaseSaleVol(String isbn) throws Exception;

    List<BookDto> selectPage(Map map) throws Exception;


    int insertToBook_image(BookImageDto bookImageDto) throws Exception;
    int insertToWriting_contributor(WritingContributorDto writingContributorDto) throws Exception;
    int insertToBookContributor(Map map) throws Exception;

    Integer selectMaxImgSeq(String isbn) throws Exception;
    Integer selectWrSeq() throws Exception;
    Integer selectTrlSeq() throws Exception;
}
