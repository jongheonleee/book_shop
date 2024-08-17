package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.item.BookImageDto;
import com.fastcampus.ch4.dto.item.BookSearchCondition;
import com.fastcampus.ch4.dto.item.WritingContributorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BookDaoImplTest {

    @Autowired
    BookDao bookDao;

    @Test
    public void searchSelectPage() throws Exception {
        //전체 삭제하고 카운트
        bookDao.deleteAll();
        bookDao.deleteAllFromWritingContributor();
        bookDao.deleteAllFromBookContributor();
        assertTrue(bookDao.count() == 0);

        // 원하는 개수의 bookDto객체 인서트해보고 (인서트 됐는지도 체크)
        // searchCondition 객체에 원하는 키워드를 생성자에 줘서 생성한 다음
        // bookDao.searchSelectPage(sc)로 리스트를 받아와서
        // 리스트 개수 체크
        for (int i = 0; i <= 20; i++) {
            BookDto bookDto = new BookDto(
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
            );
            assertTrue(bookDao.insert(bookDto) == 1);

            WritingContributorDto wcb = new WritingContributorDto(bookDto.getWr_cb_num(), bookDto.getWr_name(), 'Y');
            assertTrue(bookDao.insertToWritingContributor(wcb) == 1);
            Map map = new HashMap();
            map.put("isbn", bookDto.getIsbn());
            map.put("cb_num", bookDto.getWr_cb_num());
            assertTrue(bookDao.insertToBookContributor(map) == 1);

            wcb = new WritingContributorDto(bookDto.getTrl_cb_num(), bookDto.getTrl_name(), 'N');
            assertTrue(bookDao.insertToWritingContributor(wcb) == 1);
            map = new HashMap();
            map.put("isbn", bookDto.getIsbn());
            map.put("cb_num", bookDto.getTrl_cb_num());
            assertTrue(bookDao.insertToBookContributor(map) == 1);
        }
        // searchCondition 객체를 생성하고 searchSelectPage에 매개변수로 넘겨서 제목을 키워드로 읽어온 리스트의 사이즈를 체크한다.
        BookSearchCondition sc = new BookSearchCondition(1, 10, "book_reg_date", "DESC", "book_title2", "T");
        List<BookDto> list = bookDao.searchSelectPage(sc);
//        System.out.println("list = "+ list);
        assertTrue(list.size() == 2); //book_title2, book_title20

        // searchCondition 객체를 생성하고 searchSelectPage에 매개변수로 넘겨서 저자를 키워드로 읽어온 리스트의 사이즈를 체크한다.
        sc = new BookSearchCondition(1, 10, "book_reg_date", "DESC", "wr_name2", "W");
        list = bookDao.searchSelectPage(sc);
        System.out.println("list = "+ list);
        assertTrue(list.size() == 2); //wr_name2, wr_name20

        // searchCondition 객체를 생성하고 searchSelectPage에 매개변수로 넘겨서 출판사를 키워드로 읽어온 리스트의 사이즈를 체크한다.
        sc = new BookSearchCondition(1, 10, "book_reg_date", "DESC", "pub_name2", "P");
        list = bookDao.searchSelectPage(sc);
        System.out.println("list = "+ list);
        assertTrue(list.size() == 2); //pub_name2, pub_name20
    }

    @Test
    public void searchResultCnt() throws Exception {
        //전체 삭제하고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 원하는 개수의 bookDto객체 인서트해보고 (인서트 됐는지도 체크)
        // searchCondition 객체에 원하는 키워드를 생성자에 줘서 생성한 다음
        // bookDao.searchSelectPage(sc)로 리스트를 받아와서
        // 리스트 개수 체크
        for (int i = 0; i <= 20; i++) {
            BookDto bookDto = new BookDto(
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
                    "ts_name" + i                // trl_name
            );
            assertTrue(bookDao.insert(bookDto) == 1);
        }
        // searchCondition 객체를 생성하고 searchSelectPage에 매개변수로 넘겨서 제목을 키워드로 읽어온 리스트의 개수를 체크한다.
        BookSearchCondition sc = new BookSearchCondition(1, 10, "book_reg_date", "DESC", "book_title2", "T");
        int cnt = bookDao.searchResultCnt(sc);
        assertTrue(cnt == 2); //book_title2, bookt_title20

        // searchCondition 객체를 생성하고 searchSelectPage에 매개변수로 넘겨서 제목을 키워드로 읽어온 리스트의 개수를 체크한다.
        sc = new BookSearchCondition(1, 10, "book_reg_date", "DESC", "wr_name2", "W");
        cnt = bookDao.searchResultCnt(sc);
        assertTrue(cnt == 2); //wr_name2, wr_name20

        // searchCondition 객체를 생성하고 searchSelectPage에 매개변수로 넘겨서 제목을 키워드로 읽어온 리스트의 개수를 체크한다.
        sc = new BookSearchCondition(1, 10, "book_reg_date", "DESC", "pub_name2", "P");
        cnt = bookDao.searchResultCnt(sc);
        assertTrue(cnt == 2); //pub_name2, pub_name20
    }

    // 테스트 데이터 주입
    public void insertTestData(int num) throws Exception{
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
                    "wr_cb_num" + i,             // cb_num
                    "trl_cb_num" + i,            // trl_cb_num
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
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "title",            // title
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);

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
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "title",            // title
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);
    }

    // 유니크에 중복값 널기
    @Test(expected = DuplicateKeyException.class)
    public void 인서트_예외1() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                "title",            // title
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);

        // 중복값 불가 - DuplicateKeyException발생
        assertTrue(bookDao.insert(bookDto) == 0);
    }

    // Not_Null에 Null넣기
    @Test(expected = DataIntegrityViolationException.class)
    public void 인서트_예외2() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // null title
        String nullTitle = null;
        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                nullTitle,            // title
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
        );

        bookDao.insert(bookDto);
    }

    // 도메인 범위 넘어서는 값 넣기
    @Test(expected = DataIntegrityViolationException.class)
    public void 인서트_예외3() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 도메인 범위 넘어선 pub_name
        String wrong_pub_name = "pub_name123141432523534875629865826598763749851232532535434534534234634";
        // 1개 추가
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"             // trl_name
        );

        assertTrue(bookDao.insert(bookDto) == 0);
    }

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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"             // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"             // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);
        bookDto = bookDao.selectAll().get(0);
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

        // 없는 isbn
        String wrong_isbn = "isbn2746";

        // 없는 isbn으로 조회
        BookDto bookDto = bookDao.select(wrong_isbn);
        assertTrue(bookDto == null);
    }

    // 업데이트
    @Test
    public void 업데이트_성공() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"             // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);

        //isbn 가져오기
        String isbn = bookDao.selectAll().get(0).getIsbn();
        bookDto = bookDao.selectAll().get(0);
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"             // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);

        // NOT NULL에 NULL
        String isbn = bookDao.selectAll().get(0).getIsbn();
        bookDto = bookDao.select(isbn);
        bookDto.setIsbn(isbn);
        bookDto.setPub_name(null);
        bookDto.setTitle("내가 바꾼 타이틀");
        assertTrue(bookDao.update(bookDto) == 0);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 업데이트_예외2() throws Exception {
        // 전체 지우고 카운트
        bookDao.deleteAll();
        assertTrue(bookDao.count() == 0);

        // 1개 추가
        BookDto bookDto = new BookDto(
                "isbn",                  // isbn
                "cate_num",              // cate_num
                "pub_name",              // pub_name
                null,            // title
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"                // trl_name
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
                "wr_cb_num",             // cb_num
                "trl_cb_num",            // trl_cb_num
                "wr_name",               // wr_name
                "ts_name"             // trl_name
        );
        assertTrue(bookDao.insert(bookDto) == 1);

        //도메인 값 벗어난 제목
        String wrong_title = "qiadfawfqwfwfwfwfwqsdfhisfuhsefewoeighowieghwohgwoghwoighe";

        // 도메인 범위 벗어난 값 업데이트
        String isbn = bookDao.selectAll().get(0).getIsbn();
        bookDto = bookDao.select(isbn);
        bookDto.setIsbn(isbn);
        bookDto.setTitle(wrong_title);
        assertTrue(bookDao.update(bookDto) == 0);
    }
}