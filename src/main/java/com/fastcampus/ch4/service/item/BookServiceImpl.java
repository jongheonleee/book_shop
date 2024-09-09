package com.fastcampus.ch4.service.item;

import com.fastcampus.ch4.dao.item.BookDao;
import com.fastcampus.ch4.dao.item.CategoryDao;
import com.fastcampus.ch4.dto.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements com.fastcampus.ch4.service.item.BookService {
    /**
     *  도서글 쓰거나 수정할 때
     *  1. 중복 ISBN은 있으면 안됨.
     *  2. 한 ISBN당 할인시작일자와 종료일자 기간이 겹치면 안됨.
     *
     */

    @Autowired
    private BookDao bookDao;
    @Autowired
    private CategoryDao categoryDao;

    // 1. 도서 개수 카운트
    @Override
    public int getCountBook() {
        return bookDao.countBook();
    }

    // 2. 도서 상품 등록
    @Override
    @Transactional
    public void write(BookDto bookDto) {
        // sale_stat 임의 설정
//        bookDto.setSale_stat("판매중");
        // e_book_url 임의 설정
        bookDto.setEbook_url("");

        // 도서 테이블 인서트
        int result = bookDao.insertBook(bookDto);

        /* 도서 이미지 인서트 - ISBN, 일련번호, 이미지 URL(repre_img) */
        BookImageDto bookImageDto = null;

        // 이미지 일련번호 설정
        Integer maxImgSeq = bookDao.selectMaxImgSeq(bookDto.getIsbn());
        if(maxImgSeq == null)
            bookImageDto = new BookImageDto(bookDto.getIsbn(), 1);
        else
            bookImageDto = new BookImageDto(bookDto.getIsbn(), maxImgSeq);

        // 대표_이미지 설정
        bookImageDto.setImg_url(bookDto.getRepre_img());
        bookImageDto.setMain_img_chk('Y');

        bookDao.insertBookImage(bookImageDto);

        /* 집필 기여자 인서트 - 기여자번호(cb_num), 이름, 직업1, reg_id, up_id */

        WritingContributorDto wrWritingContributorDto = null;
        WritingContributorDto trlWritingContributorDto = null;
        // 도서-집필기여자 중간테이블에 인서트할 맵
        // BookDto의 isbn과 writingContributor의 cb_num을 맵에 담아서 넘기기
        Map map = new HashMap();
        map.put("isbn", bookDto.getIsbn());
        /*
            (1) 작가 이름이 넘어왔다면 다음 작가 시퀀스 번호 조회
            (2) cbNum생성해서 테이블 인서트
            (3) writingContributorDto 객체 생성(cb_num, wr_name)
            (4) writingContributor테이블 인서트
         */

        if (bookDto.getWr_name() != null) {
            Integer wrSeq = bookDao.selectWrSeq();

            String wrCbNum = "wr" + wrSeq;
            wrWritingContributorDto = new WritingContributorDto(wrCbNum, bookDto.getWr_name());
            wrWritingContributorDto.setWr_chk('Y');

            bookDao.insertWritingContributor(wrWritingContributorDto);
            map.put("cb_num", wrWritingContributorDto.getCb_num());

            // 도서-집필 기여자 관계 테이블 인서트 (isbn, cb_num)
            bookDao.insertBookContributor(map);
        }
        /*
            (1) 번역자 이름이 넘어왔다면 다음 번역자 시퀀스 번호 조회
            (2) cbNum생성해서 테이블 인서트
            (3) writingContributorDto 객체 생성(cb_num, trl_name)
            (4) writingContributor테이블 인서트
         */
        if (bookDto.getTrl_name() != null) {
            Integer trlSeq = bookDao.selectTrlSeq();

            String trlCbNum = "trl"+trlSeq;
            trlWritingContributorDto = new WritingContributorDto(trlCbNum, bookDto.getTrl_name());
            trlWritingContributorDto.setWr_chk('N');

            bookDao.insertWritingContributor(trlWritingContributorDto);
            map.put("cb_num", trlWritingContributorDto.getCb_num());

            // 도서-집필 기여자 관계 테이블 인서트 (isbn, cb_num)
            bookDao.insertBookContributor(map);
        }
    }

    // 3. 도서상세페이지 읽기
    @Override
    public BookDto read(String isbn) {
        return bookDao.selectBook(isbn);
    }

    // 4. 도서목록 가져오기
    @Override
    public List<BookDto> getList() {
        return bookDao.selectAllBook();
    }

    // 5. 도서 수정
    @Override
    public int modify(BookDto bookDto) {
        return bookDao.updateBook(bookDto);
    }

    // 6. 도서 삭제 - isbn 일치 확인
    @Override
    @Transactional
    public void remove(String isbn, String writer) {
        /*
            삭제 대상(isbn으로 조회)
            (1) book 테이블
            (2) book_image테이블
            (3) book_disc_hist 테이블
            (4) book_contributor 테이블
         */
        bookDao.deleteBook(isbn, writer);
        bookDao.deleteBookImage(isbn,writer);
        bookDao.deleteBookDiscHist(isbn,writer);
        bookDao.deleteBookContributor(isbn,writer);
    }

    // 7. 선택된 페이지 가져오기.
    @Override
    public List<BookDto> getPage(Map map) {
        return bookDao.selectPage(map);
    }

    // 8. 도서 목록 전체 조회
    @Override
    public List<BookDto> getAllBookList() {
        return bookDao.selectAllBook();
    }

    // 9. 판매량 증가
    @Override
    public int increaseSaleVol(Map map) {
        return bookDao.increaseSaleVol(map);
    }

    // 10. 검색된 페이지 가져오기
    @Override
    public List<BookDto> getSearchResultPage(BookSearchCondition bsc) {
        return bookDao.searchSelectPage(bsc);
    }

    // 11. 검색된 총 도서 개수 카운트
    @Override
    public int getSearchResultCnt(BookSearchCondition bsc) {
        return bookDao.searchResultCnt(bsc);
    }

    // 12. 전체 카테고리 리스트 받아오기
    @Override
    public List<CategoryDto> getCategoryList() {
        return categoryDao.selectAll();
    }
}
