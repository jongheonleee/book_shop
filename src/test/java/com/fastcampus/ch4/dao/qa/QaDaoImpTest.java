package com.fastcampus.ch4.dao.qa;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fastcampus.ch4.dto.qa.PageHandler;
import com.fastcampus.ch4.dto.qa.QaDto;

import com.fastcampus.ch4.dto.qa.SearchCondition;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class QaDaoImpTest {


    @Autowired
    private QaDaoImp dao;


    @Before
    public void 초기화() {
        assertTrue(dao != null);
        dao.deleteAll();
    }

    @Test
    public void 데이터_넣기() {
        for (int i=0; i<20; i++) {
            QaDto dto = new QaDto();
            dto.setUser_id("user1");
            dto.setQa_cate_num("qa_cate_num1");
            dto.setTitle("title" + i);
            dto.setContent("content" + i);
            dto.setCreated_at("2021-01-01");
            dto.setEmail("email1");
            dto.setTele_num("010-1234-5678");
            dto.setPhon_num("010-1234-5678");
            dto.setImg1("img1");
            dto.setImg2("img2");
            dto.setImg3("img3");

            assertTrue(1 == dao.insert(dto));
        }
    }

    /**
     * 1차 기능 구현[✅]
     * - (1) 유저의 문의글 카운팅
     * - (2) 유저의 문의글 리스트 조회
     * - (3) 유저의 문의글 등록
     * - (4) 유저의 문의글 수정
     * - (5) 유저의 문의글 삭제
     * - (6) 구간별로 문의글 조회(페이징 처리)
     * - (7) 글 검색 - 기간, 제목, 기간 & 제목 대상으로 글 검색
     * - (8) 문의글 일련번호로 조회
     *
     * 1차 요구사항 정리
     * - (1) 유저의 문의글 카운팅 [✅]
     * - 비회원의 경우, 카운팅 개수 0
     * - 회원의 경우, 등록한 문의글 카운팅 n개(여러개)
     * - 회원 필드값 null, 카운팅 개수 0
     *
     * - (2) 유저의 문의글 리스트 조회 [✅]
     * - 비회원의 경우, 조회 리스트 길이 0
     * - 회원의 경우, 조회 리스트 길이 N
     * - 회원의 경우, 문의글이 없으면 조회 리스트 길이 0
     *
     *
     * - (3) 유저의 문의글 등록 [✅]
     * - 비회원의 경우, 등록 실패
     * - 회원의 경우, 등록 성공
     * - 회원의 경우, null 문의글은 예외 발생 (제약 조건 위배 : DataIntegrityViolationException)
     * - 회원의 경우, 필수값 null인 경우 예외 발생 (제약 조건 위배 : DataIntegrityViolationException)
     * - 회원의 경우, 공백 문의글은 예외 발생
     * - 회원의 경우, 공백 제목인 경우 예외 발생
     * - 중복된 키값 등록시 예외 발생
     *
     * - (4) 유저의 문의글 수정 [✅]
     *  - 비회원의 경우, 수정 실패
     *  - 회원이지만, 해당 회원의 문의 글이 아닌 경우 실패
     *  - 회원의 경우, 수정 성공
     *  - 회원의 경우, 공백 문의글로 수정 작업 예외 발생 💥-> 자스, 컨트롤러단에서 유효성 검증
     *  - 회원의 경우, 필수값 null일 때 수정 작업 예외 발생 💥 -> 자스, 컨트롤러단에서 유효성 검증
     *
     * - (5) 유저의 문의글 삭제 [✅]
     * - 비회원의 경우, 삭제 실패
     * - 회원이지만, 해당 회원의 문의 글이 아닌 경우 삭제 실패
     * - 회원의 경우, 삭제 성공
     *
     * - (6) 페이징 처리로 글 조회[✅]
     * - (7) 글 검색 - 기간, 제목 대상으로 글 검색[✅]
     * - (8) 문의글 일련번호로 조회[✅]
     *
     */

    // 1차 기능 구현 테스트
    // (1) 기능 테스트
    @Test
    @DisplayName("비회원 유저 문의글 카운팅 0")
    public void 비회원_카운팅_0() {
        // given
        int expected = 0;
        String user_id = "non-member";

        // when
        int count = dao.count(user_id);

        // then
        assertEquals(expected, count);
    }


    @Test
    @DisplayName("회원 유저 문의글 카운팅 n개")
    public void 회원_카운팅_n() {
        // given
        int expected = 3;
        String user_id = "user1";

        for (int i=0; i<expected; i++) {
            QaDto dto = new QaDto();
            dto.setUser_id(user_id);
            dto.setQa_cate_num("qa_cate_num1");
            dto.setTitle("title" + i);
            dto.setContent("content" + i);
            dto.setCreated_at("2021-01-01");
            dto.setEmail("email1");
            dto.setTele_num("010-1234-5678");
            dto.setPhon_num("010-1234-5678");
            dto.setImg1("img1");
            dto.setImg2("img2");
            dto.setImg3("img3");

            assertTrue(1 == dao.insert(dto));
        }


        // when
        int count = dao.count(user_id);

        // then
        assertEquals(expected, count);
    }

    @Test
    @DisplayName("회원 유저 필드 null인 경우, 0 카운팅")
    public void 회원_null_0() {
        // given
        int expected = 0;
        String user_id = "null";

        // when
        int count = dao.count(user_id);

        // then
        assertEquals(expected, count);
    }



    // (2) 기능 테스트
    @Test
    @DisplayName("비회원의 경우, 조회 리스트 길이 0")
    public void 비회원_조회_리스트_0() {
        // given
        String user_id = "non-member";
        int expected = 0;

        // when
        List<QaDto> selected = dao.selectByUserId(user_id);
        int actual = selected.size();

        // then
         assertEquals(expected, actual);
    }

    @Test
    @DisplayName("회원의 경우, 조회 리스트 길이 N")
    public void 회원_조회_리스트_n() {
        // given
        String user_id = "user1";
        int expected = 5;
        for (int i=0; i<expected; i++) {
            QaDto dto = new QaDto();
            dto.setUser_id(user_id);
            dto.setQa_cate_num("qa_cate_num1");
            dto.setTitle("title" + i);
            dto.setContent("content" + i);
            dto.setCreated_at("2021-01-01");
            dto.setEmail("email1");
            dto.setTele_num("010-1234-5678");
            dto.setPhon_num("010-1234-5678");
            dto.setImg1("img1");
            dto.setImg2("img2");
            dto.setImg3("img3");

            assertTrue(1 == dao.insert(dto));
        }

        // when
        List<QaDto> selected = dao.selectByUserId(user_id);
        int actual = selected.size();

        // then
         assertEquals(expected, actual);
    }

    @Test
    @DisplayName("회원의 경우, 문의글이 없으면 조회 리스트 길이 0")
    public void 회원_문의글_없음_리스트_0() {
        // given
        String user_id = "member";
        int expected = 0;

        // when
        List<QaDto> selected = dao.selectByUserId(user_id);
        int actual = selected.size();

        // then
        assertEquals(expected, actual);
    }

    // (3) 기능 테스트
    @Test
    @DisplayName("회원 필드값(필수값)이 null, 예외 발생")
    public void 비회원_등록_실패() {
        // given
        QaDto dto = new QaDto();
        dto.setUser_id(null);
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");

        // 💥 제약 조건 위배 -> DataIntegrityViolationException 발생
        // 필수값 넣지 않아서 발생하는 예외
        assertThrows(DataIntegrityViolationException.class, () -> dao.insert(dto));

    }

    @Test
    @DisplayName("회원의 경우 등록 성공")
    public void 회원_등록_성공() {
        // given
        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        int expected = 1;

        // when
        int rowCnt = dao.insert(dto);

        // then
        assertTrue(expected == rowCnt);
    }

    @Test
    @DisplayName("회원의 경우, 문의글이 null 예외 발생")
    public void 회원_등록_실패1() {
        // given
        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent(null); //  칼럼 최소 길이 설정 필요 - 최소 길이 3 설정함
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");

        // 💥 제약 조건 위배 -> DataIntegrityViolationException 발생
        // 필수값 넣지 않아서 발생하는 예외
        assertThrows(DataIntegrityViolationException.class, () -> dao.insert(dto));

    }


    @Test
    @DisplayName("회원의 경우, 필수값 null인 경우 예외 발생")
    public void 회원_등록_실패2() {
        // given
        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle(null);
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");

        // 💥 제약 조건 위배 -> DataIntegrityViolationException 발생
        // 필수값 작성하지 않아서 발생하는 예외
        assertThrows(DataIntegrityViolationException.class, () -> dao.insert(dto));

    }

    @Test
    @DisplayName("회원의 경우, 공백 문의글은 예외 발생 ")
    public void 공백_문의글_예외() {
        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent(""); // 최소 길이 3 이상
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");

        // 스프링 예외, UncategorizedSQLException -> 예외에 대해서 명확히 파악 못한 경우 발생
        // 💥
        // 유효한 값 넣지 않아서 발생하는 예외
        assertThrows(UncategorizedSQLException.class, () -> dao.insert(dto));

    }

    @Test
    @DisplayName("회원의 경우, 공백 제목인 경우 예외 발생 ")
    public void 공백_제목_예외() {
        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle(""); // 최소 길이 3 이상
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");

        // 스프링 예외, UncategorizedSQLException -> 예외에 대해서 명확히 파악 못한 경우 발생
        // 사용자 예외 재정의
        // 💥 유효한 값 넣지 않아서 발생하는 예외
        assertThrows(UncategorizedSQLException.class, () -> dao.insert(dto));

    }

//    @Test
//    @DisplayName("중복된 키 값 등록시 예외 발생")
//    public void 중복된_키_등록_예외() {
//        // given
//        QaDto dto = new QaDto();
//        dto.setUser_id("user1");
//        dto.setQa_cate_num("qa_cate_num1");
//        dto.setTitle("title1");
//        dto.setContent("content1");
//        dto.setCreated_at("2021-01-01");
//        dto.setEmail("email1");
//        dto.setTele_num("010-1234-5678");
//        dto.setPhon_num("010-1234-5678");
//        dto.setImg1("img1");
//        dto.setImg2("img2");
//        dto.setImg3("img3");
//
//
//        // when
//        dao.insert(dto);
//        assertThrows(DuplicateKeyException.class, () -> dao.insert(dto));
//
//    }

    // (4) 기능 테스트
    @Test
    @DisplayName("비회원의 경우, 수정 실패")
    public void 비회원_수정_실패() {
        // given
        String user_id = "non-member";

        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        assertTrue(1 == dao.insert(dto));

        int expected = 0;

        // when
        dto.setUser_id("non-member");
        dto.setTitle("updated title");
        int rowCnt = dao.update(dto);

        // then
        assertTrue(expected == rowCnt);
    }

    @Test
    @DisplayName("회원이지만, 해당 회원의 문의 글이 아닌 경우 실패")
    public void 회원_문의글_아님_수정_실패() {
        // given
        String user_id = "user1";
        int expected = 1;

        QaDto dto = new QaDto();
        dto.setUser_id("user2");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        assertTrue(1 == dao.insert(dto));


        dto.setTitle("updated title");

        // when
        dto.setUser_id(user_id);
        int rowCnt = dao.update(dto); // 예외 발생

        assertTrue(0 == rowCnt);

    }

    @Test
    @DisplayName("회원의 경우, 수정 성공")
    public void 회원_수정_성공() {
        // given
        String user_id = "user1";
        int expected = 1;

        QaDto dto = new QaDto();
        dto.setUser_id(user_id);
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        assertTrue(1 == dao.insert(dto));

        List<QaDto> qaDtos = dao.selectByUserId("user1");
        QaDto target = qaDtos.get(0);
        assertTrue(target != null);
        System.out.println(target);
        target.setTitle("updated title");

        // when
        int rowCnt = dao.update(target);
        List<QaDto> updated = dao.selectByUserId(user_id);

        // then
        assertTrue(expected == rowCnt);
        assertTrue(target.getTitle().equals(updated.get(0).getTitle()));

    }
//
//    @Test
//    @DisplayName("회원의 경우, 공백 문의글로 수정 작업 예외 발생")
//    public void 회원_수정_공백글_예외() {
//        // given
//        String user_id = "user1";
//
//        QaDto dto = new QaDto();
//        dto.setUser_id(user_id);
//        dto.setQa_cate_num("qa_cate_num1");
//        dto.setTitle("title1");
//        dto.setContent("content1");
//        dto.setCreated_at("2021-01-01");
//        dto.setEmail("email1");
//        dto.setTele_num("010-1234-5678");
//        dto.setPhon_num("010-1234-5678");
//        dto.setImg1("img1");
//        dto.setImg2("img2");
//        dto.setImg3("img3");
//        assertTrue(1 == dao.insert(dto));
//
//
//        dto.setTitle("");
//
//        // when
//
//    }
//
//    @Test
//    @DisplayName("회원의 경우, 필수 값이 null일 때 수정 작업 예외 발생")
//    public void 회원_수정_필수값_null_예외() {
//        // given
//        String user_id = "user1";
//        int expected = 1;
//
//        QaDto dto = new QaDto();
//        dto.setUser_id(user_id);
//        dto.setQa_cate_num("qa_cate_num1");
//        dto.setTitle("title1");
//        dto.setContent("content1");
//        dto.setCreated_at("2021-01-01");
//        dto.setEmail("email1");
//        dto.setTele_num("010-1234-5678");
//        dto.setPhon_num("010-1234-5678");
//        dto.setImg1("img1");
//        dto.setImg2("img2");
//        dto.setImg3("img3");
//        assertTrue(1 == dao.insert(dto));
//
//        QaDto target = dao.selectForUpdate(dto);
//        target.setTitle(null);
//
//
//        // when
//        assertThrows(UncategorizedSQLException.class, () -> dao.update(dto)); // 예외 발생
//    }



    // (5) 기능 테스트
    @Test
    @DisplayName("비회원의 경우, 삭제 실패")
    public void 비회원_삭제_실패() {
        // given
        String user_id = "non-member";

        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        assertTrue(1 == dao.insert(dto));

        int expected = 0;

        // when
        dto.setUser_id(user_id);
        int rowCnt = dao.delete(dto);

        // then
        assertTrue(expected == rowCnt);
    }

    @Test
    @DisplayName("비회원의 경우, 삭제 실패")
    public void 회원_문의글_아님_삭제_실패() {
        // given
        String user_id = "user1";

        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        assertTrue(1 == dao.insert(dto));

        dto.setUser_id("user2");

        int expected = 0;

        // when
        int rowCnt = dao.delete(dto);


        // then
        assertTrue(expected == rowCnt);
    }

    @Test
    @DisplayName("회원의 경우 삭제 성공")
    public void 회원_삭제_성공() {
        // given
        String user_id = "user1";

        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa_cate_num1");
        dto.setTitle("title1");
        dto.setContent("content1");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        assertTrue(1 == dao.insert(dto));


        int expected = 1;
        List<QaDto> qaDtos = dao.selectByUserId(user_id);
        dto = qaDtos.get(0);
        System.out.println(dto);

        // when
        int rowCnt = dao.delete(dto);

        // then
        System.out.println("rowCnt = " + rowCnt);
        assertTrue(expected == rowCnt);
    }

    // (6) 페이징 처리로 글 조회
    @Test
    @DisplayName("페이징 처리로 글 조회")
    public void 구간_문의글_조회() {
        // given
        for (int i=0; i<=100; i++) {
            QaDto dto = new QaDto();
            dto.setUser_id("user1");
            dto.setQa_cate_num("qa_cate_num1");
            dto.setTitle("title" + i);
            dto.setContent("content" + i);
            dto.setCreated_at("2021-01-01");
            dto.setEmail("email1");
            dto.setTele_num("010-1234-5678");
            dto.setPhon_num("010-1234-5678");
            dto.setImg1("img1");
            dto.setImg2("img2");
            dto.setImg3("img3");

            assertTrue(1 == dao.insert(dto));
        }

        // when
        PageHandler ph = new PageHandler(1, 100);
        SearchCondition sc = new SearchCondition(1, 10, "", "", 0);
        int offSet = (ph.getPage() - 1) * ph.getPageSize();
        int pageSize = ph.getPageSize();
        List<QaDto> selected = dao.selectByUserIdAndPh("user1", sc);


        // then
        assertTrue(10 == selected.size());
    }

    // (7) 글 검색 - 기간, 제목 대상으로 글 검색
    // 제목 대상으로 검색
    @DisplayName("제목 대상으로 글 검색")
    @Test
    public void 제목_검색() {
        for (int i=0; i<10; i++) {
            QaDto dto = new QaDto();
            dto.setUser_id("user1");
            dto.setQa_cate_num("qa_cate_num1");
            dto.setTitle("title" + i);
            dto.setContent("content" + i);
            dto.setCreated_at("2021-01-01");
            dto.setEmail("email1");
            dto.setTele_num("010-1234-5678");
            dto.setPhon_num("010-1234-5678");
            dto.setImg1("img1");
            dto.setImg2("img2");
            dto.setImg3("img3");

            assertTrue(1 == dao.insert(dto));
        }

        SearchCondition sc = new SearchCondition(1, 10, "title", "title1", 0);
        List<QaDto> selected = dao.selectBySearchCondition("user1", sc);
        assertTrue(1 == selected.size());


    }

    // 기간 대상으로 검색
    @DisplayName("기간으로 글 검색")
    @Test
    public void 기간_검색() {
        for (int i=0; i<10; i++) {
            QaDto dto = new QaDto();
            dto.setUser_id("user1");
            dto.setQa_cate_num("qa_cate_num1");
            dto.setTitle("title" + i);
            dto.setContent("content" + i);
            dto.setCreated_at("2021-01-01");
            dto.setEmail("email1");
            dto.setTele_num("010-1234-5678");
            dto.setPhon_num("010-1234-5678");
            dto.setImg1("img1");
            dto.setImg2("img2");
            dto.setImg3("img3");

            assertTrue(1 == dao.insert(dto));
        }

        SearchCondition sc = new SearchCondition(1, 10, "period", "", 3);
        List<QaDto> selected = dao.selectBySearchCondition("user1", sc);
        assertTrue(10 == selected.size());


    }

}