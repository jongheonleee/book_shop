package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.BookSearchCondition;
import com.fastcampus.ch4.dto.item.WritingContributorDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.rmi.server.ExportException;
import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {

    // session - SQL을 수행하는데 필요한 메서드 제공
    @Autowired
    private SqlSession session;

    // BookListMapper.xml에 작성한 별명과 같아야됨.
    private static String namespace = "com.fastcampus.ch4.dao.item.BookMapper.";

    // book 테이블 카운트
    @Override
    public int countBook() { //전체 개수 반환
        return session.selectOne(namespace + "countBook");
    }
    // book_disc_hist 테이블 카운트
    @Override
    public int countBookDiscHist() { //전체 개수 반환
        return session.selectOne(namespace + "countBookDiscHist");
    }

    // book_image 테이블 카운트
    @Override
    public int countBookImage() { //전체 개수 반환
        return session.selectOne(namespace + "countBookImage");
    }

    // book_contributor 테이블 카운트
    @Override
    public int countBookContributor() { //전체 개수 반환
        return session.selectOne(namespace + "countBookContributor");
    }

    // writing_contributor 테이블 카운트
    @Override
    public int countWritingContributor() { //전체 개수 반환
        return session.selectOne(namespace + "countWritingContributor");
    }

    // 도서 테이블 전체 삭제.
    @Override
    public int deleteAllBook() { // 전체 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteAllBook");
    }

    // 집필 기여자 테이블 전체 삭제
    @Override
    public int deleteAllWritingContributor() { // 전체 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteAllWritingContributor");
    }

    // 도서 기여자 테이블 전체 삭제
    @Override
    public int deleteAllBookContributor() { // 전체 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteAllBookContributor");
    }

    // 도서 이미지 테이블 전체 삭제
    @Override
    public int deleteAllBookImage() { // 전체 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteAllBookImage");
    }

    // 도서 할인 이력 테이블 전체 삭제
    @Override
    public int deleteAllBookDiscHist() {
        return session.delete(namespace + "deleteAllBookDiscHist");
    }

    // isbn일치 삭제 - 도서 테이블
    @Override
    public int deleteBook(String isbn, String writer) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        Map map = new HashMap();
        map.put("isbn", isbn);
        map.put("writer", writer);
        return session.delete(namespace + "deleteBook", map);
    }

    // isbn일치 삭제 - 도서 할인 이력 테이블
    @Override
    public int deleteBookDiscHist(String isbn, String writer) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        Map map = new HashMap();
        map.put("isbn", isbn);
        map.put("writer", writer);
        return session.delete(namespace + "deleteBookDiscHist", map);
    }

    @Override
    public int deleteBookImage(String isbn, String writer) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        Map map = new HashMap();
        map.put("isbn", isbn);
        map.put("writer", writer);
        return session.delete(namespace + "deleteBookImage", map);
    }

    @Override
    public int deleteBookContributor(String isbn, String writer) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        Map map = new HashMap();
        map.put("isbn", isbn);
        map.put("writer", writer);
        return session.delete(namespace + "deleteBookContributor", map);
    }

    @Override
    public int deleteWritingContributor(String isbn, String writer) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        Map map = new HashMap();
        map.put("isbn", isbn);
        map.put("writer", writer);
        return session.delete(namespace + "deleteWritingContributor", map);
    }

    @Override
    public int deleteBookForAdmin(String isbn) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteBookForAdmin", isbn);
    }

    @Override
    public int deleteBookDiscHistForAdmin(String isbn) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteBookDiscHistForAdmin", isbn);
    }

    @Override
    public int deleteBookImageForAdmin(String isbn) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteBookImageForAdmin", isbn);
    }

    @Override
    public int deleteBookContributorForAdmin(String isbn) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteBookContributorForAdmin", isbn);
    }

    @Override
    public int deleteWritingContributorForAdmin(String isbn) { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteWritingContributorForAdmin", isbn);
    }


    // BookDto 삽입.
    @Override
    public int insertBook(BookDto dto) { //BookDto book 테이블에 insert
        return session.insert(namespace + "insertBook", dto);
    }

    // 게시판 전체 조회. List<BookDto> 받아오기
    @Override
    public List<BookDto> selectAllBook() { // 전체 테이블 조회(등록일 내림차순)
        return session.selectList(namespace + "selectAllBook");
    }

    // isbn으로 조회. BookDto 받아오기
    @Override
    public BookDto selectBook(String isbn) { // isbn과 일치하는 행 들고오기
        return session.selectOne(namespace + "selectBook", isbn);
    }

    // BookDto로 업데이트.
    @Override
    public int updateBook(BookDto dto) { // BookDto정보로 isbn이 일치하는 행 update
        return session.update(namespace + "updateBook", dto);
    }

    // isbn으로 해당 도서의 판매량 증가.
    @Override
    public int increaseSaleVol(Map map) {
        return session.update(namespace + "increaseSaleVol", map);
    }

    // 선택된 페이지 가져오기
    @Override
    public List<BookDto> selectPage(Map map) {
        return session.selectList(namespace + "selectPage", map);
    }

    // 도서 이미지 테이블 인서트
    @Override
    public int insertBookImage(BookImageDto bookImageDto) {
        return session.insert(namespace + "insertBookImage", bookImageDto);
    }

    // 집필 기여자 테이블 인서트
    @Override
    public int insertWritingContributor(WritingContributorDto writingContributorDto) {
        return session.insert(namespace + "insertWritingContributor", writingContributorDto);
    }

    // 집필 기여자-도서 관계 테이블 인서트
    @Override
    public int insertBookContributor(Map map) {
        return session.insert(namespace + "insertBookContributor", map);
    }

    // 해당 isbn의 이미지 시퀀스 최대값 조회
    @Override
    public Integer selectMaxImgSeq(String isbn) {
        return session.selectOne(namespace + "selectMaxImgSeq", isbn);
    }

    // 다음 wr_seq값 조회
    @Override
    public Integer selectWrSeq() {
        return session.selectOne(namespace + "selectWrSeq");
    }

    // 다음 trl_seq값 조회
    @Override
    public Integer selectTrlSeq() {
        return session.selectOne(namespace + "selectTrlSeq");
    }

    // 검색된 전체 도서 개수
    @Override
    public int searchResultCnt(BookSearchCondition bsc) {
        return session.selectOne(namespace + "searchResultCnt", bsc);
    }

    // 검색된 페이지
    @Override
    public List<BookDto> searchSelectPage(BookSearchCondition bsc) {
        return session.selectList(namespace + "searchSelectPage", bsc);
    }

}
