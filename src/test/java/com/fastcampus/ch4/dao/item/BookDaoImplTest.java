package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BookDaoImplTest {

    @Autowired
    BookDao bookDao;


    // 테스트 데이터 주입
    public void insertTestData(int num) throws Exception{
        // 데이터 주입
        for (int i = 1; i <= num; i++) {
            BookDto bookDto = new BookDto(
                    "isbn" + i,                  // isbn
                    "cate_num" + i,              // cate_num
                    "pub_name" + i,              // pub_name
                    "book_title" + i,            // title
                    "pub_num" + i,               // pub_num
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
                    "wr_name" + i,               // wr_name
                    "ts_name" + i                // trl_name
            );

            assertTrue(bookDao.insert(bookDto) == 1);
        }
    }

    // 테스트 데이터 주입
    @Test
    public void 테스트데이터넣기() throws Exception{
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);
        // 데이터 주입
        insertTestData(250);
    }

    // 전체 카운팅
    @Test
    public void 카운트_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 100개 넣고 카운트
        insertTestData(100);
        assertTrue(bookDao.count() == 100);
    }

    // 전체삭제
    @Test
    public void 전체삭제_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 3개 추가하고 삭제 개수 카운트
        insertTestData(3);
        assertTrue(bookDao.deleteAll() == 3);
        assertTrue(bookDao.count() == 0);
    }

    // delete와 deleteAll 다르게 동작하는지 꼼꼼하게 확인
    @Test
    public void 삭제테스트_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 3개 추가하고 삭제
        insertTestData(3);

        // 예상결과: 3개 --> 2개
        assertTrue(bookDao.delete(bookDao.selectAll().get(0).getIsbn()) == 1);
        assertTrue(bookDao.count() == 2);
    }

    // 지울 대상이 없어서 실패
    @Test
    public void 삭제테스트_실패() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        insertTestData(1);

        // 잘못된 isbn주고 삭제
        assertTrue(bookDao.delete(bookDao.selectAll().get(0).getIsbn()+ 111) == 0);
        assertTrue(bookDao.count() == 1);
    }

    // 삽입
    @Test
    public void 인서트_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        insertTestData(1);
    }

    // 유니크에 중복값 널기
    @Test(expected = DuplicateKeyException.class)
    public void 인서트_예외1() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        insertTestData(1);

        // 중복값 불가 - DuplicateKeyException발생
        BookDto bookDto = new BookDto(
                "isbn" + 1,                  // isbn
                "cate_num" + 1,              // cate_num
                "pub_name" + 1,              // pub_name
                "book_title" + 1,            // title
                "pub_num" + 1,               // pub_num
                "2024-08-07 15:35:58",       // pub_date
                "Available",                 // sale_stat
                1,                           // sale_vol
                1,                           // papr_pric
                5.0,                         // e_pric
                5.0,                         // papr_point
                5.0,                         // e_point
                1,                           // tot_page_num
                1,                           // tot_book_num
                "",                          // sale_com
                "",                          // cont
                4.5,                         // rating
                "",                          // info
                "",                          // intro_award
                "",                          // rec
                "",                          // pub_review
                1,                           // pre_start_page
                1,                           // pre_end_page
                "",                          // ebook_url
                new Date(),                  // book_reg_date
                "test",                      // regi_id
                new Date(),                  // reg_date
                "test",                      // reg_id
                new Date(),                  // up_date
                "test",                      // up_id
                "repre_img" + 1,             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name" + 1,        // whol_layr_name
                "wr_name" + 1,               // wr_name
                "ts_name" + 1                // trl_name
        );

        assertTrue(bookDao.insert(bookDto) == 0);
    }

    // Not_Null에 Null넣기
    @Test(expected = DataIntegrityViolationException.class)
    public void 인서트_예외2() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn" + 1,                  // isbn
                "cate_num" + 1,              // cate_num
                "pub_name" + 1,              // pub_name
                null,            // title
                "pub_num" + 1,               // pub_num
                "2024-08-07 15:35:58",       // pub_date
                "Available",                 // sale_stat
                1,                           // sale_vol
                1,                           // papr_pric
                5.0,                         // e_pric
                5.0,                         // papr_point
                5.0,                         // e_point
                1,                           // tot_page_num
                1,                           // tot_book_num
                "",                          // sale_com
                "",                          // cont
                4.5,                         // rating
                "",                          // info
                "",                          // intro_award
                "",                          // rec
                "",                          // pub_review
                1,                           // pre_start_page
                1,                           // pre_end_page
                "",                          // ebook_url
                new Date(),                  // book_reg_date
                "test",                      // regi_id
                new Date(),                  // reg_date
                "test",                      // reg_id
                new Date(),                  // up_date
                "test",                      // up_id
                "repre_img" + 1,             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name" + 1,        // whol_layr_name
                "wr_name" + 1,               // wr_name
                "ts_name" + 1                // trl_name
        );

        bookDao.insert(bookDto);
    }

    // 도메인 범위 넘어서는 값 넣기
    @Test(expected = DataIntegrityViolationException.class)
    public void 인서트_예외3() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn" + 1,                  // isbn
                "cate_num" + 1,              // cate_num
                "pub_name123141432523534875629865826598763749851232532535434534534234634",              // pub_name
                "book_title" + 1,            // title
                "pub_num" + 1,               // pub_num
                "2024-08-07 15:35:58",       // pub_date
                "Available",                 // sale_stat
                1,                           // sale_vol
                1,                           // papr_pric
                5.0,                         // e_pric
                5.0,                         // papr_point
                5.0,                         // e_point
                1,                           // tot_page_num
                1,                           // tot_book_num
                "",                          // sale_com
                "",                          // cont
                4.5,                         // rating
                "",                          // info
                "",                          // intro_award
                "",                          // rec
                "",                          // pub_review
                1,                           // pre_start_page
                1,                           // pre_end_page
                "",                          // ebook_url
                new Date(),                  // book_reg_date
                "test",                      // regi_id
                new Date(),                  // reg_date
                "test",                      // reg_id
                new Date(),                  // up_date
                "test",                      // up_id
                "repre_img" + 1,             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name" + 1,        // whol_layr_name
                "wr_name" + 1,               // wr_name
                "ts_name" + 1                // trl_name
        );

        bookDao.insert(bookDto);
    }

//    // 삽입
//    @Test
//    public void 출판사_인서트_성공() throws Exception {
//        // 전체 지우고 카운트
//        bookDao.deleteAll();
//        assertTrue(bookDao.count() == 0);
//
//        // 1개 추가
//        insertTestData2(1);
//    }
//
//    // 유니크에 중복값 널기
//    @Test(expected = DuplicateKeyException.class)
//    public void 출판사_인서트_예외1() throws Exception {
//        // 전체 지우고 카운트
//        bookDao.deleteAll();
//        assertTrue(bookDao.count() == 0);
//
//        // 1개 추가
//        insertTestData(1);
//
//        // 중복값 불가 - DuplicateKeyException발생
//        BookDto bookDto = new BookDto("isbn" + 1, "pub_num" + 1,"cate_num" + 1, "repre_img" + 1, "pub_name" + 1, "wr_name" + 1,"ts_name" + 1, 5, 5, "whole_layr_name", "book_title" + 1,
//                1, 1, "2024-08-07 15:35:58", 1, 1, "", 1, 1, "", "", "", "", "", "", "", "", 1,1,"","", new Date(),"test", "test");
//        assertTrue(bookDao.insert(bookDto) == 0);
//    }
//
//    // Not_Null에 Null넣기
//    @Test(expected = DataIntegrityViolationException.class)
//    public void 출판사_인서트_예외2() throws Exception {
//        // 전체 지우고 카운트
//        bookDao.deleteAll();
//        assertTrue(bookDao.count() == 0);
//
//        // 1개 추가
//        BookDto bookDto = new BookDto("isbn", "pub_num", null, "repre_img", "pub_name", "wr_name","ts_name", 5, 5, "whole_layer_name",  "book_title",
//                5, 5, "2024-08-07 15:35:58", 5, 5, "", 5, 5, "", "", "", "", "", "", "", "",  5,5, "","", new Date(),"test",  "test");
//
//        bookDao.insert(bookDto);
//    }
//
//    // 도메인 범위 넘어서는 값 넣기
//    @Test(expected = DataIntegrityViolationException.class)
//    public void 출판사_인서트_예외3() throws Exception {
//        // 전체 지우고 카운트
//        bookDao.deleteAll();
//        assertTrue(bookDao.count() == 0);
//
//        // 1개 추가
//        BookDto bookDto = new BookDto("isbn", "pub_num423492835723895729835781241241232123123112335", null, "repre_img", "pub_name", "wr_name","ts_name", 5, 5, "whole_layr_name", "book_title",
//                5, 5, "2024-08-07 15:35:58", 5, 5, "", 5, 5, "", "", "", "", "", "", "", "",  5,5, "","", new Date(),"test",  "test");
//
//        bookDao.insert(bookDto);
//    }

    // 전체 조회
    @Test
    public void 전체조회_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 데이터 없는 상태에서 카운트
        List<BookDto> bookList = bookDao.selectAll();
        assertTrue(bookDao.count() == 0);

        // 한개 넣고 카운트
        insertTestData(1);
        bookList = bookDao.selectAll();
        assertTrue(bookList.size() == 1);

        bookDao.deleteAll();

        // 100개 추가하고 개수 카운트
        insertTestData(100);
        bookList = bookDao.selectAll();
        assertTrue(bookList.size() == 100);
    }

    // 조건(isbn)으로 조회
    @Test
    public void 조회_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        insertTestData(1);
        BookDto bookDto = bookDao.selectAll().get(0);
        //isbn 가져오기
        String isbn = bookDao.selectAll().get(0).getIsbn();

        // 다른 참조변수에 조회한 값 넣고 같은지 비교
        BookDto bookDto2 = bookDao.select(isbn);
        assertTrue((bookDto).equals(bookDto2));
    }

    // 없는 isbn으로 조회
    @Test
    public void 조회_실패() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 없는 isbn으로 조회
        BookDto bookDto = bookDao.select("isbn2746");
        assertTrue(bookDto == null);
    }

    // 업데이트
    @Test
    public void 업데이트_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        insertTestData(1);

        //isbn 가져오기
        String isbn = bookDao.selectAll().get(0).getIsbn();
        BookDto bookDto = bookDao.selectAll().get(0);
        // bookDto 객체 속성값 바꾸고 업데이트
        bookDto.setIsbn(isbn);
        bookDto.setTitle("내가 바꾼 타이틀");
        assertTrue(bookDao.update(bookDto) == 1);

        // 업데이트한 객체와 같은지 체크
//        BookDto bookDto2 = bookDao.select(isbn);
//        assertTrue(bookDto2.equals(bookDto));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 업데이트_예외1() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 삽입
        insertTestData(1);

        // NOT NULL에 NULL
        String isbn = bookDao.selectAll().get(0).getIsbn();
        BookDto bookDto = bookDao.select(isbn);
        bookDto.setIsbn(isbn);
        bookDto.setPub_num(null);
        bookDto.setTitle("내가 바꾼 타이틀");
        bookDao.update(bookDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 업데이트_예외2() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn" + 1,                  // isbn
                "cate_num" + 1,              // cate_num
                "pub_name" + 1,              // pub_name
                null,            // title
                "pub_num" + 1,               // pub_num
                "2024-08-07 15:35:58",       // pub_date
                "Available",                 // sale_stat
                1,                           // sale_vol
                1,                           // papr_pric
                5.0,                         // e_pric
                5.0,                         // papr_point
                5.0,                         // e_point
                1,                           // tot_page_num
                1,                           // tot_book_num
                "",                          // sale_com
                "",                          // cont
                4.5,                         // rating
                "",                          // info
                "",                          // intro_award
                "",                          // rec
                "",                          // pub_review
                1,                           // pre_start_page
                1,                           // pre_end_page
                "",                          // ebook_url
                new Date(),                  // book_reg_date
                "test",                      // regi_id
                new Date(),                  // reg_date
                "test",                      // reg_id
                new Date(),                  // up_date
                "test",                      // up_id
                "repre_img" + 1,             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name" + 1,        // whol_layr_name
                "wr_name" + 1,               // wr_name
                "ts_name" + 1                // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);

        // 유니크에 중복값 업데이트
        String isbn = bookDao.selectAll().get(0).getIsbn();
        bookDto = bookDao.select(isbn);
        bookDto.setIsbn(isbn);
        assertTrue(bookDao.update(bookDto) == 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 업데이트_예외3() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        insertTestData(1);

        // 도메인 범위 벗어난 값 업데이트
        String isbn = bookDao.selectAll().get(0).getIsbn();
        BookDto bookDto = bookDao.select(isbn);
        bookDto.setIsbn(isbn);
        bookDto.setTitle("qiadfawfqwfwfwfwfwqsdfhisfuhsefewoeighowieghwohgwoghwoighe");
        assertTrue(bookDao.update(bookDto) == 0);
    }
}