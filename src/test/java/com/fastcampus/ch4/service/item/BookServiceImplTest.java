package com.fastcampus.ch4.service.item;

import com.fastcampus.ch4.dao.item.BookDao;
import com.fastcampus.ch4.dto.item.BookDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BookServiceImplTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    BookService bookService;

    // 테스트 데이터 주입
    public void writeTestData(int num) {
        // 데이터 주입
        for (int i = 1; i <= num; i++) {
            BookDto bookDto = new BookDto(
                    "isbn" + i,                  // isbn
                    "cate_num" + i,              // cate_num
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
                    "wr_cb_num" + i,             // wr_cb_num
                    "trl_cb_num" + i,            // trl_cb_num
                    "wr_name" + i,               // wr_name
                    "ts_name" + i                // trl_name
            );
            bookService.register(bookDto);
        }
    }

    @Before
    public void 초기화() {
        /*
            전체 삭제 대상
            (1) 도서 테이블
            (2) 도서 이미지 테이블
            (3) 도서-집필기여자(book_contributor) 관계 테이블
            (4) 집필기여자(writing_contirbutor) 테이블
         */
        /*
            (1) 지우기 전 카운트
            (2) 전체 지우고 지우기 전 개수와 비교
            (3) 완벽하게 지워졌는지 카운트
         */
        int bookCountBeforeDeleteAll = bookDao.countBook();
        assertTrue(bookDao.deleteAllBook() == bookCountBeforeDeleteAll);
        int bookCount = bookDao.countBook();
        assertTrue(bookCount == 0);

        int bookDiscHistBeforeDeleteAll = bookDao.countBookDiscHist();
        assertTrue(bookDao.deleteAllBookDiscHist() == bookDiscHistBeforeDeleteAll);
        int bookCountDiscHist = bookDao.countBookDiscHist();
        assertTrue(bookCountDiscHist == 0);

        int bookImageCountBeforeDeleteAll = bookDao.countBookImage();
        assertTrue(bookDao.deleteAllBookImage() == bookImageCountBeforeDeleteAll);
        int bookImageCount = bookDao.countBookImage();
        assertTrue(bookImageCount == 0);

        int bookContributorCountBeforeDeleteAll = bookDao.countBookContributor();
        assertTrue(bookDao.deleteAllBookContributor() == bookContributorCountBeforeDeleteAll);
        int bookContributorCount = bookDao.countBookContributor();
        assertTrue(bookContributorCount == 0);

        int writingContributorCountBeforeDeteleAll = bookDao.countWritingContributor();
        assertTrue(bookDao.deleteAllWritingContributor()==writingContributorCountBeforeDeteleAll);
        int writingContributorCount = bookDao.countWritingContributor();
        assertTrue(writingContributorCount == 0);
    }

    // 테스트 데이터 주입
    @Test
    public void 테스트데이터넣기() throws Exception {
        int inputDataNum = 250;
        writeTestData(inputDataNum);
        //카운트
        assertTrue(bookService.getCountBook() == inputDataNum);
    }

    // 전체 카운팅
    @Test
    public void 카운트_성공() throws Exception {
        int inputDataNum = 100;
        // 100개 넣고 카운트
        writeTestData(inputDataNum);
        assertTrue(bookService.getCountBook() == inputDataNum);
    }

    // 전체삭제
    @Test
    public void 전체삭제_성공() throws Exception {

        // 3개 추가
        for (int i = 1; i <= 3; i++) {
            BookDto bookDto = new BookDto(
                    "isbn" + i,                  // isbn
                    "cate_num" + i,              // cate_num
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
                    "wr_cb_num" + i,             // wr_cb_num
                    "trl_cb_num" + i,            // trl_cb_num
                    "wr_name" + i,               // wr_name
                    "ts_name" + i                // trl_name
            );
            bookService.register(bookDto);
        }

        /*
            전체 삭제 대상
            (1) 도서 테이블
            (2) 도서 이미지 테이블
            (3) 도서-집필기여자(book_contributor) 관계 테이블
            (4) 집필기여자(writing_contirbutor) 테이블
         */
        // 전체 지우고 카운트

        assertTrue(bookDao.deleteAllBook() == 3);
        int bookCount = bookDao.countBook();
        assertTrue(bookCount == 0);

        assertTrue(bookDao.deleteAllBookImage() == 3);
        int bookImageCount = bookDao.countBookImage();
        assertTrue(bookImageCount == 0);

        assertTrue(bookDao.deleteAllBookContributor() == 6);
        int bookContributorCount = bookDao.countBookContributor();
        assertTrue(bookContributorCount == 0);

        assertTrue(bookDao.deleteAllWritingContributor()==6);
        int writingContributorCount = bookDao.countWritingContributor();
        assertTrue(writingContributorCount == 0);
    }

    // remove와 resetList 다르게 동작하는지 꼼꼼하게 확인
    @Test
    public void 삭제_성공() throws Exception {
        // 3개 추가하고 삭제. 개수  테스트
        // 3개 추가
        BookDto bookDto = null;
        for (int i = 1; i <= 3; i++) {
            bookDto = new BookDto(
                    "isbn" + i,                  // isbn
                    "cate_num" + i,              // cate_num
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
                    "wr_cb_num" + i,             // wr_cb_num
                    "trl_cb_num" + i,            // trl_cb_num
                    "wr_name" + i,               // wr_name
                    "ts_name" + i                // trl_name
            );
            bookService.register(bookDto);
        }
        assertTrue(bookService.getCountBook() == 3);
        // 예상결과: 3개 --> 2개
        String writer = bookDto.getRegi_id();
        assertTrue(bookService.remove(bookService.getAllBookList().get(0).getIsbn(), writer));
        // 똑같은거 읽어와서 확인
        assertTrue(bookService.getCountBook() == 2);
    }

    // 지울 대상이 없어서 실패
    @Test
    public void 삭제_실패() throws Exception {
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);

        String wrongIsbn = bookService.getAllBookList().get(0).getIsbn() + 111;
        String writer = "test";
        // 잘못된 isbn주고 삭제
        assertTrue(bookService.remove(wrongIsbn, writer));
        assertTrue(bookService.getCountBook() == 1);
    }

    // 삽입
    @Test
    public void 쓰기_성공() throws Exception {
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
                "2024-08-07",       // pub_date
                "판매중지",                 // sale_stat
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);
    }

    // 유니크에 중복값 널기
    @Test(expected = DuplicateKeyException.class)
    public void 쓰기_예외1() throws Exception {
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);

        // 중복값 불가 - DuplicateKeyException발생
        bookDto = bookService.getAllBookList().get(0);
        bookService.register(bookDto);
    }

    // Not_Null에 Null넣기
    @Test(expected = DataIntegrityViolationException.class)
    public void 쓰기_예외2() throws Exception {
        // nullTitle
        String title = null;
        // 1개 추가 - DataIntegrityViolationException발생
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                title,            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );

        bookService.register(bookDto);
    }

    // 도메인 범위 넘어서는 값 넣기
    @Test(expected = DataIntegrityViolationException.class)
    public void 쓰기_예외3() throws Exception {
        // 도메인 범위 넘어선 pub_name
        String wrong_pub_name = "pub_name123141432523534875629865826598763749851232532535434534534234634";

        // 1개 추가 - DataIntegrityViolationException 발생
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                wrong_pub_name,              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );

        bookService.register(bookDto);
    }

    // 전체 조회
    @Test
    public void 전체조회_성공() throws Exception {
        // 데이터 없는 상태에서 카운트
        List<BookDto> bookList = bookService.getAllBookList();
        assertTrue(bookService.getCountBook() == 0);

        // 한개 넣고 카운트
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);
        bookList = bookService.getAllBookList();
        assertTrue(bookList.size() == 1);

        초기화();

        // 100개 추가하고 개수 카운트
        writeTestData(100);
        bookList = bookService.getAllBookList();
        assertTrue(bookList.size() == 100);
    }

    // 조건(isbn)으로 조회
    @Test
    public void 조회_성공() throws Exception {
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);

        //isbn 가져오기
        String isbn = bookService.getAllBookList().get(0).getIsbn();

        // 다른 참조변수에 조회한 값 넣고 같은지 비교
        BookDto bookDto2 = bookService.read(isbn);
        assertTrue((bookService.getAllBookList().get(0)).equals(bookDto2));
    }

    // 없는 isbn으로 조회
    @Test
    public void 조회_실패() throws Exception {
        // 없는 isbn으로 조회
        BookDto bookDto = bookService.read("isbn2746");
        assertTrue(bookDto == null);
    }

    // 업데이트
    @Test
    public void 수정_성공() throws Exception {
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);

        //isbn 가져오기
        String isbn = bookService.getAllBookList().get(0).getIsbn();

        // bookDto 객체 속성값 바꾸고 업데이트
        bookDto = bookService.getAllBookList().get(0);
        bookDto.setIsbn(isbn);
        bookDto.setTitle("내가 바꾼 타이틀");
        assertTrue(bookService.modify(bookDto) == 1);

        // 업데이트한 객체와 같은지 체크
        BookDto bookDto2 = bookService.read(isbn);
        assertTrue(bookDto2.equals(bookDto));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 수정_예외1() throws Exception {
        // 1개 삽입
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);

        // NOT NULL에 NULL
        String isbn = bookService.getAllBookList().get(0).getIsbn();
        bookDto = bookService.read(isbn);
        bookDto.setIsbn(isbn);
        bookDto.setPub_name(null);
        bookDto.setTitle("내가 바꾼 타이틀");
        bookService.modify(bookDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 수정_예외2() throws Exception {
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "book_title",            // title
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
                "repre_img",             // repre_img_url
                1,                           // papr_disc
                1,                           // e_disc
                "whol_layr_name",        // whol_layr_name
                "wr_cb_num",             // wr_cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        bookService.register(bookDto);
        assertTrue(bookService.getCountBook() == 1);

        // 도메인 범위 벗어난 값 업데이트
        String isbn = bookService.getAllBookList().get(0).getIsbn();
        bookDto = bookService.read(isbn);
        bookDto.setIsbn(isbn);
        bookDto.setTitle("qiehgwoeighowieghwohgwoghwoighe");
        assertTrue(bookService.modify(bookDto) == 0);
    }
}