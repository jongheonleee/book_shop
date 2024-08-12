package com.fastcampus.ch4.dao.qa;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fastcampus.ch4.domain.qa.PageHandler;
import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import com.fastcampus.ch4.dto.qa.QaDto;

import com.fastcampus.ch4.domain.qa.SearchCondition;
import com.fastcampus.ch4.dto.qa.QaStateDto;
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

    @Autowired
    private QaCategoryDaoImp helper;


    @Before
    public void 초기화() {
        assertTrue(dao != null);
        dao.deleteAll();
        helper.deleteAll();

        QaCategoryDto dto = new QaCategoryDto();
        dto.setQa_cate_num("qa_cate_num1");
        dto.setName("교환/환불 요청");
        dto.setComt("comt1");
        dto.setReg_date("2021-01-01");
        dto.setReg_id("reg_id1");
        dto.setUp_date("2021-01-01");
        dto.setUp_id("up_id1");
        dto.setChk_use("Y");

        assertTrue(1 == helper.insert(dto));
    }

    @Test
    public void 데이터_넣기() {
        for (int i=0; i<20; i++) {
            QaDto dto = create(i);

            assertTrue(1 == dao.insert(dto));
        }
    }

    @Test
    public void 데이터_비움() {
        dao.deleteAll();
    }

    private static QaDto create(int i) {
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
        return dto;
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


    /**
     * 3차 기능 구현[]
     * - (1) 문의글 상태 등록
     * - (2) 문의글 상태 조회 (모두 조회)
     * - (3) 특정 문의글 상태 이력 조회(해당 문의글 상태 이력)
     * - (4) 특정 문의글에 한 가지 상태 조회
     * - (5) 특정 문의글 상태 내용 업데이트
     * - (6) 문의글 상태 모두 삭제
     * - (7) 특정 문의글에 대한 상태 이력 모두 삭제
     * - (8) 특정 문의글의 한 가지 상태 삭제
     *
     * 3차 요구사항 정리
     * - (1) 문의글 상태 등록[✅]
     * - 관리자가 아닌 경우 상태 등록 실패
     * - not null 칼럼에 null 넣으면 예외 발생
     * - 관리자의 경우, 상태 등록 성공
     *
     * - (2) 문의글 상태 조회 (모두 조회)[✅]
     * - 회원이 아닌 경우, 상태 조회 실패
     * - 회원의 경우, n개 만큼 상태 조회
     * -
     * - (3) 특정 문의글 상태 이력 조회(해당 문의글 상태 이력)[✅]
     * - 비회원의 경우, 특정 문의글의 상태 이력 조회 실패
     * - 회원의 경우, 특정 문의글의 상태 이력 모두 조회 (등록된 상태 이력만큼 조회)
     *
     * - (4) 특정 문의글에 가장 최근 상태 조회[✅]
     * - 비회원의 경우, 조회 실패
     * - 회원의 경우, 특정 문의글의 최근 상태 조회 성공
     *
     * - (5) 특정 문의글 상태 내용 업데이트[✅]
     * - 관리자가 아닌 경우, 상태 내용 업데이트 실패
     * - 관리자의 경우, 상태 내용 업데이트 가능. 하지만, 이력으로서 등록해야함
     *
     * - (6) 문의글 상태 모두 삭제[✅]
     * - 관리자가 아닌 경우, 상태 모두 삭제 실패
     * - 관리자의 경우, 상태 모두 삭제 성공
     * -
     * - (7) 특정 문의글에 대한 상태 이력 모두 삭제[]
     * - 관리자가 아닌 경우, 특정 문의글의 상태 이력 모두 삭제 실패
     * - 관리자의 경우, 특정 문의글의 상태 이력 모두 삭제 성공
     *
     * - (8) 특정 문의글의 한 가지 상태 삭제[]
     * - 관리자가 아닌 경우, 특정 문의글의 특정 상태 삭제 실패
     * - 관리자의 경우, 특정 문의글의 특정 상태 삭제 성공
     *
     */

    // 1차 기능 구현 테스트 -> 문의글 자체 작업#
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
            QaDto dto = create(i);
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
            QaDto dto = create(i);
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
        QaDto dto = create(0);
        dto.setUser_id(null);

        // 💥 제약 조건 위배 -> DataIntegrityViolationException 발생
        // 필수값 넣지 않아서 발생하는 예외
        assertThrows(DataIntegrityViolationException.class, () -> dao.insert(dto));

    }

    @Test
    @DisplayName("회원의 경우 등록 성공")
    public void 회원_등록_성공() {
        // given
        QaDto dto = create(0);
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
        QaDto dto = create(0);
        dto.setContent(null);

        // 💥 제약 조건 위배 -> DataIntegrityViolationException 발생
        // 필수값 넣지 않아서 발생하는 예외
        assertThrows(DataIntegrityViolationException.class, () -> dao.insert(dto));

    }


    @Test
    @DisplayName("회원의 경우, 필수값 null인 경우 예외 발생")
    public void 회원_등록_실패2() {
        // given
        QaDto dto = create(0);
        dto.setTitle(null);

        // 💥 제약 조건 위배 -> DataIntegrityViolationException 발생
        // 필수값 작성하지 않아서 발생하는 예외
        assertThrows(DataIntegrityViolationException.class, () -> dao.insert(dto));

    }

    @Test
    @DisplayName("회원의 경우, 공백 문의글은 예외 발생 ")
    public void 공백_문의글_예외() {
        QaDto dto = create(0);
        dto.setContent("");

        // 스프링 예외, UncategorizedSQLException -> 예외에 대해서 명확히 파악 못한 경우 발생
        // 💥
        // 유효한 값 넣지 않아서 발생하는 예외
        assertThrows(UncategorizedSQLException.class, () -> dao.insert(dto));

    }

    @Test
    @DisplayName("회원의 경우, 공백 제목인 경우 예외 발생 ")
    public void 공백_제목_예외() {
        QaDto dto = create(0);
        dto.setTitle("");

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
        QaDto dto = create(0);
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
        String user_id = "non-member";
        int expected = 1;

        QaDto dto = create(0);
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

        QaDto dto = create(0);
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

        QaDto dto = create(0);
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
            QaDto dto = create(i);
            assertTrue(1 == dao.insert(dto));
        }

        // when
        SearchCondition sc = new SearchCondition(1, 10, "", "", 0);
        PageHandler ph = new PageHandler(100, sc);
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
            QaDto dto = create(i);

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
            QaDto dto = create(i);

            assertTrue(1 == dao.insert(dto));
        }

        SearchCondition sc = new SearchCondition(1, 10, "period", "", 3);
        List<QaDto> selected = dao.selectBySearchCondition("user1", sc);
        assertTrue(10 == selected.size());


    }

    // 2차 기능 테스트 -> 문의글 카테고리 관련 작업

    // 3차 기능 테스트 -> 문의글 상태 관련 작업

    // (1) 문의글 상태 등록
    @Test
    public void 관리자_아님_상태_등록_실패() {
        // 서비스 로직이나 컨트롤러 로직에서 처리
    }

    @Test
    public void not_null_칼럼_제약_위배() {
        // 데이터 생성
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);
        // not null 필드에 null 할당
        state.setQa_stat_code(null);

        // insert 수행 -> 예외 발생
        assertThrows(DataIntegrityViolationException.class,
                () -> dao.insertState(state));

    }

    @Test
    public void 관리자_상태_등록_성공() {
        // 데이터 생성
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);

        // insert 수행, 로우수 = 1
        assertTrue(1 == dao.insertState(state));
    }

    // (2) 문의글 상태 조회 (모두 조회)
    @Test
    public void 회원_아님_상태_조회_실패() {
        // 서비스 로직이나 컨트롤러 로직에서 처리
    }

    @Test
    public void 회원_상태_조회_성공() {
        // 데이터 생성
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);

        // insert 수행 * n
        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            assertTrue(1 == dao.insertState(state));
        }


        // n개 만큼 조회
        List<QaStateDto> target = dao.selectAllState();
        assertTrue(5 == target.size());

        // 내용 비교
        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            QaStateDto found = target.get(expected - 1);


            assertTrue(state.getName().equals(found.getName()));
            assertTrue(state.getQa_stat_code().equals(found.getQa_stat_code()));

//            assertTrue(found.equals(state));
        }

    }

    // (3) 특정 문의글 상태 이력 조회(해당 문의글 상태 이력)
    @Test
    public void 회원_아님_상태_이력_조회_실패() {
        // 서비스 로직이나 컨트롤러 로직에서 처리
    }

    @Test
    public void 회원_상태_이력_조회_성공() {
        // 데이터 생성 및 등록 * n
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);

        // insert 수행 * n
        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            assertTrue(1 == dao.insertState(state));
        }


        // 특정 문의글의 상태 이력 조회
        List<QaStateDto> target = dao.selectStateByQaNum(qaNum);

        // 사이즈 n, 내용 비교
        assertTrue(target.size() == 5);

        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            QaStateDto found = target.get(expected-1);

            System.out.println(state);
            System.out.println(found);

            assertTrue(state.getQa_stat_code().equals(found.getQa_stat_code()));
            assertTrue(state.getName().equals(found.getName()));
        }
    }

    // (4) 특정 문의글에 한 가지 상태 조회
    @Test
    public void 회원_아님_가장_최근_상태_조회_실패() {
        // 서비스 로직이나 컨트롤러 로직에서 처리
    }

    @Test
    public void 회원_가장_최근_상태_조회_성공() {
        // 데이터 생성 및 등록 * n
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);

        // insert 수행 * n
        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            // 1초씩 딜레이
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }


            assertTrue(1 == dao.insertState(state));
        }

        // 특정 문의글의 가장 최근 상태 이력 조회
        QaStateDto target = dao.selectStateForLast(qaNum);
        System.out.println(target);

        // 내용 비교
        state = createState(5, qaNum);
        System.out.println(state);

        assertTrue(target.getName().equals(state.getName()));
        assertTrue(target.getQa_stat_code().equals(state.getQa_stat_code()));

    }

    // (5) 특정 문의글 상태 내용 업데이트
    @Test
    public void 관리자_아님_상태_업데이트_실패() {
        // 관리자 확인 로직은 서비스나 컨트롤러에서 처리
    }

    @Test
    public void 관리자_상태_업데이트_성공() {
        // 데이터 생성 및 등록
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);
        assertTrue(1 == dao.insertState(state));

        // 해당 데이터 조회
        QaStateDto target = dao.selectStateForLast(qaNum);

        // 해당 데이터 필드 수정
        target.setName("new name!");

        // 업데이트 작업 수행
        int rowCnt = dao.updateState(target);

        // 로우수 1, 내용 비교
        assertTrue(1 == rowCnt);
        assertTrue(target.getName().equals(dao.selectStateForLast(qaNum).getName()));
        assertTrue(target.getQa_stat_code().equals(dao.selectStateForLast(qaNum).getQa_stat_code()));
    }

    // (6) 문의글 상태 모두 삭제
    @Test
    public void 관리자_아님_상태_삭제_실패() {
        // 관리자 확인 로직은 서비스나 컨트롤러에서 처리
    }
    @Test
    public void 관리자_상태_삭제_성공() {
        // 데이터 생성 및 등록
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);
        assertTrue(1 == dao.insertState(state));

        // 해당 데이터 삭제
        QaStateDto target = dao.selectStateForLast(qaNum);
        int seq = target.getQa_stat_seq();
        int rowCnt = dao.deleteState(seq);

        // 로우수 1, 조회 안됨
        assertTrue(1 == rowCnt);
        assertTrue(dao.selectStateForLast(qaNum) == null);
    }


    // (7) 특정 문의글에 대한 상태 이력 모두 삭제
    @Test
    public void 관리자_아님_특정_문의글_상태_이력_삭제_실패() {
        // 관리자 확인 로직은 서비스나 컨트롤러에서 처리
    }
    @Test
    public void 관리자_특정_문의글_상태_이력_삭제() {
        // 특정 문의글에 n개의 상태 데이터 생성 및 등록
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);

        // insert 수행 * n
        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            // 1초씩 딜레이
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            assertTrue(1 == dao.insertState(state));
        }


        // 해당 상태 이력 전체 삭제
        int rowCnt = dao.deleteAllStateOnQaNum(qaNum);

        // 적용 로우수 n, 조회 안됨
        assertTrue(5 == rowCnt);
        List<QaStateDto> target = dao.selectStateByQaNum(qaNum);
        assertTrue(0 == target.size());
    }

    // (8) 특정 문의글의 한 가지 상태 삭제
    @Test
    public void 관리자_아님_특정_문의글_특정_상태_삭제_실패() {
        // 관리자 확인 로직은 서비스나 컨트롤러에서 처리
    }
    @Test
    public void 관리자_특정_문의글_특정_상태_삭제_성공() {
        // 특정 문의글에 n개의 상태 데이터 생성 및 등록
        QaDto dto = create(1);
        assertTrue(1 == dao.insert(dto));
        QaDto selected = dao.selectByUserId("user1").get(0);
        int qaNum = selected.getQa_num();

        QaStateDto state = createState(1, qaNum);

        // insert 수행 * n
        for (int expected = 1; expected <= 5; expected++) {
            state = createState(expected, qaNum);
            // 1초씩 딜레이
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            assertTrue(1 == dao.insertState(state));
        }

        // 특정 상태 삭제
        QaStateDto target = dao.selectStateForLast(qaNum);
        int seq = target.getQa_stat_seq();

        // 적용 로우수 1, 조회 안됨
        int rowCnt = dao.deleteState(seq);
        assertTrue(1 == rowCnt);
        assertTrue(dao.selectStateByQaNum(qaNum).size() == 4);
    }

    private QaStateDto createState(int i, int qaNum) {
        QaStateDto dto = new QaStateDto();
        dto.setQa_stat_code("qa_stat_code" + i);
        dto.setQa_num(qaNum);
        dto.setName("state" + i);
        dto.setReg_date("2021-01-01");
        dto.setReg_id("reg_id1");
        dto.setUp_date("2021-01-01");
        dto.setUp_id("up_id1");
        dto.setAppl_begin("2021-01-01");
        dto.setAppl_end("2021-01-01");
        return dto;
    }

}