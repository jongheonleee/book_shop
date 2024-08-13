package com.fastcampus.ch4.dao.global;

import static org.junit.Assert.*;

import com.fastcampus.ch4.dto.global.CodeDto;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CodeDaoImpTest {

    @Autowired
    private CodeDaoImp dao;

    @Before
    public void 초기화() {
        assertNotNull(dao);
        dao.deleteAll();
    }

    @Test
    public void 테스트_데이터_생성() {
        for (int i=1; i<=5; i++) {
            CodeDto dto = create(i);
            assertTrue(1 == dao.insert(dto));
        }

        for (int i=1; i<=5; i++) {
            CodeDto dto = create2(i);
            assertTrue(1 == dao.insert(dto));
        }
    }

    /**
     * 1차 기능 요구 사항 정리
     *
     * - 문의 카테고리 CRUD
     * - (1) 문의 카테고리 등록
     * - (2) 문의 카테고리 조회
     * - (3) 문의 카테고리 수정
     * - (4) 문의 카테고리 삭제
     *
     * - 문의 상태 CRUD
     * - (1) 문의 상태 등록
     * - (2) 문의 상태 조회
     * - (3) 문의 상태 수정
     * - (4) 문의 상태 삭제
     */

    // (1) 문의 카테고리 등록
    @Test
    public void 문의_카테고리_등록() {
        CodeDto dto = create(1);
        System.out.println(dto);
        assertTrue(1 == dao.insert(dto));
    }

    // (2) 문의 카테고리 조회
    @Test
    public void 문의_카테고리_카테고리별_조회() {
        for (int i=1; i<=5; i++) {
            CodeDto dto = create(i);
            assertTrue(1 == dao.insert(dto));
        }

        // 문의글 관련 코드는 카테고리 01
        List<CodeDto> selected = dao.selectByCate("01");
        assertTrue(5 == selected.size());
        selected.stream().forEach(System.out::println);
    }

    @Test
    public void 문의_카테고리_조회() {
        for (int i=1; i<=5; i++) {
            CodeDto dto = create(i);
            assertTrue(1 == dao.insert(dto));
        }

        // 문의글 관련 코드는 카테고리 01
        CodeDto selected = dao.selectByCode("TEST1");
        System.out.println(selected);
    }

    // (3) 문의 카테고리 수정
    @Test
    public void 문의_카테고리_수정() {

    }


    // (4) 문의 카테고리 삭제
    @Test
    public void 문의_카테고리_전체_삭제() {
        for (int i=1; i<=5; i++) {
            CodeDto dto = create(i);
            assertTrue(1 == dao.insert(dto));
        }

        int rowCnt = dao.deleteAll();
        assertTrue(5 == rowCnt);

    }

    private CodeDto create(int i) {
        CodeDto dto = new CodeDto();
        dto.setCode("qa-cate-" + i);
        dto.setCate_num("01");
        dto.setCode_name("테스트");
        dto.setChk_use('Y');
        dto.setOrd(i);

        return dto;
    }

    private CodeDto create2(int i) {
        CodeDto dto = new CodeDto();
        dto.setCode("qa-stat-" + i);
        dto.setCate_num("02");
        dto.setCode_name("준비중" + i);
        dto.setChk_use('Y');
        dto.setOrd(i);

        return dto;
    }
}