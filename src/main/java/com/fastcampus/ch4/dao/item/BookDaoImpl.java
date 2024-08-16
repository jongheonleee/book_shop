package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.WritingContributorDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {

    // session - SQL을 수행하는데 필요한 메서드 제공
    @Autowired
    private SqlSession session;

    // BookListMapper.xml에 작성한 별명과 같아야됨.
    private static String namespace = "com.fastcampus.ch4.dao.item.BookMapper.";

    // 1. 카운트
    @Override
    public int count() throws Exception { //전체 개수 반환
        return session.selectOne(namespace + "count");
    }

    // 2. 전체 삭제.
    @Override
    public int deleteAll() { // 전체 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "deleteAll");
    }

    // 3. isbn일치 삭제.
    public int delete(String isbn) throws Exception { // isbn과 pub_num일치하면 삭제. 삭제된 로우수 반환
        return session.delete(namespace + "delete", isbn);
    }

    // 4. BookDto 삽입.
    @Override
    public int insert(BookDto dto) throws Exception { //BookDto book 테이블에 insert
        return session.insert(namespace + "insert", dto);
    }

    // 5. 게시판 전체 조회. List<BookDto> 받아오기
    @Override
    public List<BookDto> selectAll() throws Exception { // 전체 테이블 조회(등록일 내림차순)
        return session.selectList(namespace + "selectAll");
    }

    // 6. isbn으로 조회. BookDto 받아오기
    @Override
    public BookDto select(String isbn) throws Exception { // isbn과 일치하는 행 들고오기
        return session.selectOne(namespace + "select", isbn);
    }

    // 7. BookDto로 업데이트.
    @Override
    public int update(BookDto dto) throws Exception { // BookDto정보로 isbn이 일치하는 행 update
        return session.update(namespace + "update", dto);
    }

    // 8. isbn으로 해당 도서의 판매량 증가.
    @Override
    public int increaseSaleVol(String isbn) {
        return session.update(namespace + "increaseSaleVol", isbn);
    }

    // 9. 선택된 페이지 가져오기
    @Override
    public List<BookDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace + "selectPage", map);
    }

    // 10. 도서 이미지 테이블 인서트
    @Override
    public int insertToBook_image(BookImageDto bookImageDto) throws Exception {
        return session.insert(namespace + "insertToBook_image", bookImageDto);
    }

    // 11. 집필 기여자 테이블 인서트
    @Override
    public int insertToWriting_contributor(WritingContributorDto writingContributorDto) throws Exception {
        return session.insert(namespace + "insertToWriting_contributor", writingContributorDto);
    }

    // 12. 집필 기여자-도서 관계 테이블 인서트
    @Override
    public int insertToBookContributor(Map map) throws Exception {
        return session.insert(namespace + "insertToBook_contributor", map);
    }

    // 13. 해당 isbn의 이미지 시퀀스 최대값 조회
    @Override
    public Integer selectMaxImgSeq(String isbn) throws Exception {
        return session.selectOne(namespace + "selectMaxImgSeq", isbn);
    }

    // 14. 다음 wr_seq값 조회
    @Override
    public Integer selectWrSeq() throws Exception {
        return session.selectOne(namespace + "selectWrSeq");
    }

    // 15. 다음 trl_seq값 조회
    @Override
    public Integer selectTrlSeq() throws Exception {
        return session.selectOne(namespace + "selectTrlSeq");
    }
}
