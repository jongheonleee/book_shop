package com.fastcampus.ch4.dao.qa;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class QaCategoryDaoImpTest {

    @Autowired
    private QaCategoryDaoImp dao;

    @Autowired
    private QaDaoImp helper;

    @Before
    public void 초기화() {
        helper.deleteAll();
        assertTrue(dao != null);
        dao.deleteAll();
    }

    @Test
    public void 데이터_비우기() {
        helper.deleteAll();
        dao.deleteAll();
    }

    @Test
    public void 테스트용_데이타() {
        for (int i=1; i<=5; i++) {
            QaCategoryDto dto = create(i);
            assertTrue(1 == dao.insert(dto));
        }
    }

    /**
     *
     * 1차 기능 구현[✅]
     * - (0) 문의 카테고리 카운팅
     * - (1) 문의 카테고리를 등록함
     * - (2) 문의 카테고리를 모두 조회함
     * - (3) 문의 카테고리를 검색(카테고리 번호)
     * - (4) 문의 카테고리 사용 가능 여부에 따라서 조회함
     * - (5) 문의 카테고리를 업데이트함
     * - (6) 문의 카테고리를 삭제함
     * - (7) 문의 카테고리 모두를 삭제함
     *
     * 1차 요구사항 정리
     * - (0) 문의 카테고리 카운팅[✅]
     * - 0개 일 때 0
     * - n개 일 때 n
     *
     * - (1) 문의 카테고리를 등록함[✅]
     * - 필수값 null인 경우 예외 발생
     * - 카테고리 이름 최소 2글자 이상, 이를 어길 시 예외 발생
     * - 같은 번호의 카테고리 중복 등록 시 예외 발생
     * - chk_use 값이 Y, N 이 아닌 경우 예외 발생
     * - 카테고리 성공적으로 등록
     *
     * - (2) 문의 카테고리를 모두 조회함[✅]
     * - 0개 일 때 0개
     * - n개 일 때 n개
     *
     * - (3) 문의 카테고리를 검색(카테고리 번호)[✅]
     * - 카테고리 번호 공백 실패
     * - 카테고리 번호 null 인 경우 실패
     * - 등록되지 않은 카테고리 번호 조회시, 실패
     * - 카테고리 번호로 조회 성공
     *
     * - (4) 문의 카테고리 사용 가능 여부에 따라서 조회함[]
     * - 공백인 경우, 실패
     * - null 인 경우, 실패
     * - Y, N 이 아닌 값인 경우, 실패
     * - Y/N 개수 만큼 조회
     *
     * - (5) 문의 카테고리를 업데이트함[]
     * - 카테고리 번호 공백, 실패
     * - 카테고리 번호 null 인 경우, 실패
     * - 등록되지 않은 카테고리 번호 업데이트시, 실패
     * - 필수값 null인 경우, 실패
     * - 카테고리 이름 최소 2글자 이상, 이를 어길 시 예외 발생
     * - chk_use 값이 Y, N 이 아닌 경우 예외 발생
     * - 카테고리 업데이트 성공
     *
     * - (6) 문의 카테고리를 삭제함[✅]
     * - 카테고리 번호 공백 경우, 실패
     * - 카테고리 번호 null 인 경우, 실패
     * - 등록되지 않은 카테고리 번호 삭제시, 실패
     * - 카테고리 삭제 성공
     *
     * - (7) 문의 카테고리 모두를 삭제함[✅]
     * - 0 개일 때 0개
     * - n개일 때 n개
     *
     *
     * 파악된 예외
     *
     * - BadSqlGrammarException, UncategorizedSQLException, DataIntegrityViolationException, DuplicateKeyException, DataIntegrityViolationException
     */

    // (0) 문의 카테고리 카운팅
    @Test
    public void 없음_0() {
        // 문의글 등록 x
        // 0개 카운팅
        int expected = 0;
        int actual = dao.count();
        assertTrue(expected == actual);
    }

    @Test
    public void 있음_n() {
        // 문의글 등록할 만큼 등록 -> n
        for (int expected = 1; expected <= 20; expected++) {
            // 초기화
            assertTrue(expected - 1 == dao.deleteAll());

            // n 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));
            }

            // n 카운팅
            int actual = dao.count();
            assertTrue(expected == actual);
        }
    }

    // (1) 문의 카테고리를 등록함
    @Test
    public void 필수값_null_예외() {
        // dto에 필수값 null
        // 등록시 예외 발생 DataIntegrityViolationException
        QaCategoryDto dto = create(0);
        dto.setName(null);
        assertThrows(DataIntegrityViolationException.class,
                () -> dao.insert(dto));
    }

    @Test
    public void 제약조건_위배_예외1() {
        // 카테고리 이름 2글자 이내
        // 등록시 예외 발생
        QaCategoryDto dto = create(0);
        dto.setName("ㅇ");
        assertThrows(UncategorizedSQLException.class,
                () -> dao.insert(dto));
    }

    @Test
    public void 제약조건_위배_예외2() {
        // 카테고리 chk_use 값이 Y, N 이 아닌 경우
        // 등록시 예외 발생
        QaCategoryDto dto = create(0);
        dto.setChk_use("K");
        assertThrows(UncategorizedSQLException.class,
                () -> dao.insert(dto));
    }

    @Test
    public void 중복키_예외() {
        // 같은 키 값 재 등록 예외 발생
        QaCategoryDto dto = create(0);
        int rowCnt = 1;
        assertTrue(rowCnt == dao.insert(dto));
        assertThrows(DuplicateKeyException.class,
                () -> dao.insert(dto));
    }

    @Test
    public void 등록_성공() {
        // n번 진행, 카테고리 등록 성공
        for (int expected = 1; expected <= 20; expected++) {
            // 초기화
            assertTrue(expected - 1 == dao.deleteAll());

            // n개 생성 및 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));
            }

            // 최종 개수 확인
            int totalCnt = dao.count();
            assertTrue(totalCnt == expected);
        }
    }

    // (2) 문의 카테고리를 모두 조회함
    @Test
    public void 없음_사이즈_0() {
        // 등록된 카테고리 없음
        // 조회하면 사이즈 0

        int expected = 0;
        List<QaCategoryDto> selected = dao.selectAll();
        assertTrue(expected == selected.size());

    }

    @Test
    public void 있음_사이즈_n() {
        // n개 카테고리 등록
            // 조회하면 사이즈 k

        for (int expected = 1; expected <= 20; expected++) {
            // 초기화
            assertTrue(expected - 1 == dao.deleteAll());

            // k개 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));
            }

            // 조회
            List<QaCategoryDto> selected = dao.selectAll();

            // 총 사이즈 비교
            assertTrue(selected.size() == expected);
        }
    }


    // (3) 문의 카테고리를 검색(카테고리 번호)
    @Test
    public void 번호_공백_실패() {
        // 번호 공백
        // 조회 실패, 사이즈 0

        // 1개 생성, 등록
        QaCategoryDto dto = create(0);
        assertTrue(1 == dao.insert(dto));

        // 조회할 때 번호 공백, 실패
        QaCategoryDto found = dao.select("");

        // 조회시 null
        assertNull(found);
    }

    @Test
    public void 번호_null_실패() {
        // 번호 null
        // 조회 실패, 사이즈 0

        // 1개 생성, 등록
        QaCategoryDto dto = create(0);
        assertTrue(1 == dao.insert(dto));

        // 조회할 때 번호 공백, 실패
        QaCategoryDto found = dao.select(null);

        // 조회시 null
        assertNull(found);
    }

    @Test
    public void 등록_x_번호_실패() {
        // 등록안된 번호
        // 조회 실패, 사이즈 0

        // 1개 생성, 등록
        QaCategoryDto dto = create(0);
        assertTrue(1 == dao.insert(dto));

        // 조회할 때 등록되지 않은 번호, 실패
        QaCategoryDto found = dao.select("not-register-cate-num");

        // 조회시 null
        assertNull(found);
    }

    @Test
    public void 등록_번호_성공() {
        // 등록된 번호
        // 조회 성공, 사이즈 n


        // n번 반복
        for (int expected = 1; expected <= 20; expected++) {
            // 초기화
            assertTrue(expected - 1 == dao.deleteAll());

            // k개 생성 및 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));

                // 1개 조회, 성공
                QaCategoryDto found = dao.select(dto.getQa_cate_num());

                // 내용 비교
                assertNotNull(found);
                assertEquals(dto.getName(), found.getName());
                assertEquals(dto.getChk_use(), found.getChk_use());
                assertEquals(dto.getQa_cate_num(), found.getQa_cate_num());

            }

        }

    }

    // (4) 문의 카테고리 사용 가능 여부에 따라서 조회함
    @Test
    public void 사용가능_여부_공백_실패() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 공백으로 조회
        List<QaCategoryDto> selected = dao.selectByChkUse("");

        // 리스트 사이즈 0
        assertTrue(0 == selected.size());
    }

    @Test
    public void 사용가능_여부_null_실패() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // null로 조회
        List<QaCategoryDto> selected = dao.selectByChkUse(null);

        // 리스트 사이즈 0
        assertTrue(0 == selected.size());
    }


    @Test
    public void Y_N_아님_실패() {
        // n개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // K 로 조회
        List<QaCategoryDto> selected = dao.selectByChkUse("K");

        // 리스트 사이즈 0
        assertTrue(0 == selected.size());
    }

    @Test
    public void Y_N_이면_성공() {
        for (int expected = 1; expected<=20; expected++) {
            // 초기화
            dao.deleteAll();

            // n개 생성 및 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));
            }

            // Y 로 조회
            List<QaCategoryDto> selected = dao.selectByChkUse("Y");

            // 리스트 사이즈 n
            assertTrue(expected == selected.size());
        }
    }

    // (5) 문의 카테고리를 업데이트함
    @Test
    public void 업데이트_번호_공백_실패() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 내용 수정, 번호 공백
        dto.setQa_cate_num("");

        // 업데이트 처리
        int rowCnt = dao.update(dto);

        // 실패
        assertTrue(0 == rowCnt);
    }

    @Test
    public void 업데이트_번호_null_실패() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 내용 수정, 번호 null
        dto.setQa_cate_num(null);

        // 업데이트 처리
        int rowCnt = dao.update(dto);

        // 실패
        assertTrue(0 == rowCnt);
    }

    @Test
    public void 업데이트_제약조건_위배1_실패() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 내용 수정, 제목 길이 2이내
        dto.setName("K");

        // 업데이트 처리, 예외 발생
        assertThrows(UncategorizedSQLException.class, () -> dao.update(dto));

 }

    @Test
    public void 업데이트_제약조건_위배2_실패() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 내용 수정, 사용 가능 여부 K 지정
        dto.setChk_use("K");

        // 업데이트 처리, 예외 발생
        assertThrows(UncategorizedSQLException.class, () -> dao.update(dto));
    }

    @Test
    public void 업데이트_성공() {
        // 1개 생성 및 등록
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 내용 수정
        dto.setName("New Category Name");

        // 업데이트 처리
        int rowCnt = dao.update(dto);

        // 적용된 로우수 1
        // 내용 비교
        assertTrue(1 == rowCnt);
        QaCategoryDto found = dao.select(dto.getQa_cate_num());

        assertNotNull(found);
        assertEquals(dto.getName(), found.getName());
    }

    // (6) 문의 카테고리를 삭제함
    @Test
    public void 번호_공백_삭제_실패() {
        // 1개 생성 및 등록
        int expected = 0;
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 공백으로 삭제
        int rowCnt = dao.delete("");

        // 적용된 로우수 0
        assertTrue(expected == rowCnt);
    }

    @Test
    public void 번호_null_삭제_실패() {
        // 1개 생설 및 삭제
        int expected = 0;
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // null로 삭제
        int rowCnt = dao.delete(null);

        // 적용된 로우수 0
        assertTrue(expected == rowCnt);
    }

    @Test
    public void 등록안된_번호_실패() {
        // 1개 생성 및 삭제
        int expected = 0;
        QaCategoryDto dto = create(1);
        assertTrue(1 == dao.insert(dto));

        // 등록안된 번호로 삭제
        int rowCnt = dao.delete("non-register-cate-num");

        // 적용된 로우수 0
        assertTrue(expected == rowCnt);
    }

    @Test
    public void 등록된_번호_삭제_성공() {
        // n개 등록
        for (int expected = 1; expected <=20; expected++) {
            // 생성 및 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));

                // 삭제, 적용 로우수 1
                int rowCnt = dao.delete(dto.getQa_cate_num());
                assertTrue(1 == rowCnt);
            }

            // 카운팅 개수 0
            assertTrue(0 == dao.count());
        }
    }

    // (7) 문의 카테고리 모두를 삭제함
    @Test
    public void 없음_삭제수_0() {
        // 등록 없이, 모두 삭제함 -> 0
        int expected = 0;
        int actual = dao.deleteAll();
        assertTrue(expected == actual);
    }

    @Test
    public void 있음_삭제수_n() {
        // n개 반복 등록
        for (int expected = 1; expected <= 20; expected++) {
            // 생성 및 등록
            for (int i=1; i<=expected; i++) {
                QaCategoryDto dto = create(i);
                assertTrue(1 == dao.insert(dto));
            }

            // 모두 삭제
            int actual = dao.deleteAll();

            // 적용된 로우수 비교
            assertTrue(expected == actual);
        }
    }

    private QaCategoryDto create(int i) {
        QaCategoryDto dto = new QaCategoryDto();
        dto.setQa_cate_num("qa-cate-" + i);
        dto.setName("환불요청");
        dto.setComt("comt" + i);
        dto.setChk_use("Y");
        return dto;
    }
}