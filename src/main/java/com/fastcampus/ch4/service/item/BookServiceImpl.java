package com.fastcampus.ch4.service.item;

import com.fastcampus.ch4.dao.item.BookDao;
import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.BookSearchCondition;
import com.fastcampus.ch4.dto.item.WritingContributorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 1. 도서 개수 카운트
    @Override
    public int getCount() throws Exception{
        return bookDao.count();
    }

    // 2. 도서 상품 등록
    @Override
    @Transactional
    public int write(BookDto bookDto) throws Exception {
        // 중복 키 값 검사
        if (bookDao.select(bookDto.getIsbn()) != null)
            throw new DuplicateKeyException("isbn already exists");

        // cate_num 조회

        // 도서 테이블 인서트
        int result = bookDao.insert(bookDto);
        System.out.println(bookDto);

        /* 도서 이미지 인서트 - ISBN, 일련번호, 이미지 URL(repre_img) */
        BookImageDto bookImageDto = null;

        // 이미지 일련번호 설정
        Integer maxImgSeq = bookDao.selectMaxImgSeq(bookDto.getIsbn());
        System.out.println("최대시퀀스="+maxImgSeq);
        if(maxImgSeq == null)
            bookImageDto = new BookImageDto(bookDto.getIsbn(), 1);
        else
            bookImageDto = new BookImageDto(bookDto.getIsbn(), maxImgSeq);

        // 대표_이미지 설정
        bookImageDto.setImg_url(bookDto.getRepre_img());
        bookImageDto.setMain_img_chk('Y');
        bookDao.insertToBookImage(bookImageDto);

        /* 집필 기여자 인서트 - 기여자번호(cb_num), 이름, 직업1, reg_id, up_id */

        WritingContributorDto wrWritingContributorDto = null;
        WritingContributorDto trlWritingContributorDto = null;
        // 도서-집필기여자 중간테이블에 인서트할 맵
        // BookDto의 isbn과 writingContributor의 cb_num을 맵에 담아서 넘기기
        Map map = new HashMap();
        map.put("isbn", bookDto.getIsbn());
        // 작가 이름이 넘어왔다면 다음 작가 시퀀스 번호 조회
        // cbNum생성해서 테이블 인서트
        // writingContributorDto 객체 생성(cb_num, wr_name)
        // writingContributor테이블 인서트
        if (bookDto.getWr_name() != null) {
            Integer wrSeq = bookDao.selectWrSeq();
            String wrCbNum = "wr" + wrSeq;
            wrWritingContributorDto = new WritingContributorDto(wrCbNum, bookDto.getWr_name());
            bookDao.insertToWritingContributor(wrWritingContributorDto);
            map.put("cb_num", wrWritingContributorDto.getCb_num());
        }
        // 번역자 이름이 넘어왔다면 다음 번역자 시퀀스 번호 조회
        //  cbNum생성해서 테이블 인서트
        // writingContributorDto 객체 생성(cb_num, trl_name)
        // writingContributor테이블 인서트
        if (bookDto.getTrl_name() != null) {
            Integer trlSeq = bookDao.selectTrlSeq();
            String trlCbNum = "trl"+trlSeq;
            trlWritingContributorDto = new WritingContributorDto(trlCbNum, bookDto.getTrl_name());
            bookDao.insertToWritingContributor(trlWritingContributorDto);
            map.put("cb_num", trlWritingContributorDto.getCb_num());
        }

        // 도서-집필 기여자 관계 테이블 인서트 (isbn, cb_num)
        bookDao.insertToBookContributor(map);

        return result;
    }

    // 3. 도서상세페이지 읽기
    @Override
    public BookDto read(String isbn) throws Exception {
        return bookDao.select(isbn);
    }

    // 4. 도서목록 가져오기
    @Override
    public List<BookDto> getList() throws Exception {
        return bookDao.selectAll();
    }

    // 5. 도서글 수정
    @Override
    public int modify(BookDto bookDto) throws Exception {
        return bookDao.update(bookDto);
    }

    // 6. 도서글 삭제 - isbn 일치 확인
    @Override
    public int remove(String isbn) throws Exception {
        return bookDao.delete(isbn);
    }

    // 7. 선택된 페이지 가져오기.
    @Override
    public List<BookDto> getPage(Map map) throws Exception {
        return bookDao.selectPage(map);
    }

    // 8. 전체 목록 초기화
    @Override
    public int resetList() throws Exception {
        return bookDao.deleteAll();
    }

    // 9. 도서 목록 전체 조회
    @Override
    public List<BookDto> getAllBookList() throws Exception {
        return bookDao.selectAll();
    }

    // 10. 판매량 증가
    @Override
    public int increaseSaleVol(Map map) throws Exception {
        return bookDao.increaseSaleVol(map);
    }

    // 11. 검색된 페이지 가져오기
    @Override
    public List<BookDto> getSearchResultPage(BookSearchCondition bsc) throws Exception {
        return bookDao.searchSelectPage(bsc);
    }

    // 12. 검색된 총 도서 개수 카운트
    @Override
    public int getSearchResultCnt(BookSearchCondition bsc) throws Exception {
        return bookDao.searchResultCnt(bsc);
    }
}
