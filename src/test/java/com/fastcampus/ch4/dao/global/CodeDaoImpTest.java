package com.fastcampus.ch4.dao.global;

import static org.junit.Assert.*;

import com.fastcampus.ch4.dao.qa.QaCategoryDaoImp;
import com.fastcampus.ch4.dao.qa.QaDao;
import com.fastcampus.ch4.dto.global.CodeDto;
import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import java.util.ArrayList;
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

    @Autowired
    private QaCategoryDaoImp helper;

    @Autowired
    private QaDao helper2;

    @Before
    public void 초기화() {
        assertNotNull(dao);
        dao.deleteAll();
    }

    // 테스트 환경을 위한 메서드
    @Test
    public void 테스트_데이터_생성() {
        dao.deleteAll();
        helper2.deleteAll();
        helper.deleteAll();

        List<CodeDto> states = 홈_상태_카테고리_생성();
        states.stream().forEach(dto -> {
            assertTrue(1 == dao.insert(dto));
        });


        List<CodeDto> categories = 문의_카테고리_생성();
        categories.stream().forEach(dto -> {
            assertTrue(1 == dao.insert(dto));
        });

        List<QaCategoryDto> categories2 = 카테고리_생성();
        categories2.stream().forEach(dto -> {
            assertTrue(1 == helper.insert(dto));
        });
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

    private List<CodeDto> 홈_상태_카테고리_생성() {
        List<CodeDto> list = new ArrayList<>();

        CodeDto dto = new CodeDto();
        dto.setCode("");
        dto.setCate_num("01");
        dto.setCode_name("전체");
        dto.setChk_use('Y');
        dto.setOrd(1);
        list.add(dto);


        CodeDto dto2 = new CodeDto();
        dto2.setCode("qa-stat-01");
        dto2.setCate_num("01");
        dto2.setCode_name("처리 대기중");
        dto2.setChk_use('Y');
        dto2.setOrd(2);
        list.add(dto2);

        CodeDto dto3 = new CodeDto();
        dto3.setCode("qa-stat-02");
        dto3.setCate_num("01");
        dto3.setCode_name("처리중");
        dto3.setChk_use('Y');
        dto3.setOrd(3);
        list.add(dto3);

        CodeDto dto4 = new CodeDto();
        dto4.setCode("qa-stat-03");
        dto4.setCate_num("01");
        dto4.setCode_name("답변완료");
        dto4.setChk_use('Y');
        dto4.setOrd(4);
        list.add(dto4);

        return list;
    }

    private List<CodeDto> 문의_카테고리_생성() {
        List<CodeDto> list = new ArrayList<>();

        CodeDto dto = new CodeDto();
        dto.setCode("qa-cate-01");
        dto.setCate_num("02");
        dto.setCode_name("배송/수령예정일안내");
        dto.setChk_use('Y');
        dto.setOrd(1);
        list.add(dto);

        CodeDto dto2 = new CodeDto();
        dto2.setCode("qa-cate-02");
        dto2.setCate_num("02");
        dto2.setCode_name("주문/결제 기프트 카드");
        dto2.setChk_use('Y');
        dto2.setOrd(1);
        list.add(dto2);

        CodeDto dto3 = new CodeDto();
        dto3.setCode("qa-cate-03");
        dto3.setCate_num("02");
        dto3.setCode_name("검색 기능 관련");
        dto3.setChk_use('Y');
        dto3.setOrd(1);
        list.add(dto3);

        CodeDto dto4 = new CodeDto();
        dto4.setCode("qa-cate-04");
        dto4.setCate_num("02");
        dto4.setCode_name("반품/교환/환불(도서)");
        dto4.setChk_use('Y');
        dto4.setOrd(1);
        list.add(dto4);

        CodeDto dto5 = new CodeDto();
        dto5.setCode("qa-cate-05");
        dto5.setCate_num("02");
        dto5.setCode_name("회원 정보 서비스");
        dto5.setChk_use('Y');
        dto5.setOrd(1);
        list.add(dto5);

        return list;
    }

    private List<QaCategoryDto> 카테고리_생성() {
        List<QaCategoryDto> list = new ArrayList<>();

        QaCategoryDto dto = new QaCategoryDto();
        dto.setQa_cate_num("qa-cate-01");
        dto.setName("배송/수령예정일안내");
        dto.setChk_use("Y");
        list.add(dto);

        QaCategoryDto dto2 = new QaCategoryDto();
        dto2.setQa_cate_num("qa-cate-02");
        dto2.setName("주문/결제 기프트 카드");
        dto2.setChk_use("Y");
        list.add(dto2);

        QaCategoryDto dto3 = new QaCategoryDto();
        dto3.setQa_cate_num("qa-cate-03");
        dto3.setName("검색 기능 관련");
        dto3.setChk_use("Y");
        list.add(dto3);

        QaCategoryDto dto4 = new QaCategoryDto();
        dto4.setQa_cate_num("qa-cate-04");
        dto4.setName("반품/교환/환불(도서)");
        dto4.setChk_use("Y");
        list.add(dto4);

        QaCategoryDto dto5 = new QaCategoryDto();
        dto5.setQa_cate_num("qa-cate-05");
        dto5.setName("회원 정보 수정");
        dto5.setChk_use("Y");
        list.add(dto5);

        return list;
    }

    private CodeDto create2(int i) {
        CodeDto dto = new CodeDto();
        dto.setCode("qa-cate-" + i);
        dto.setCate_num("02");
        dto.setCode_name("준비중" + i);
        dto.setChk_use('Y');
        dto.setOrd(i);

        return dto;
    }
}