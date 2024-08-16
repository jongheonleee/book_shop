package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TermDaoImplTest {

    @Autowired
    private TermDao termDao;

    private TermDto termDto;

    @Autowired
    private TermAgreeDao termAgreeDao;

    @Before
    public void setUp() {
        // 모든 TermAgree 데이터 삭제
        termAgreeDao.deleteAllTermAgrees();

        // 모든 Term 데이터 삭제
        termDao.deleteAllTerms();

        // Term 데이터 준비
        termDto = new TermDto();
        termDto.setTermName("Test Term Name");
        termDto.setTermContent("Test Term Content");
        termDto.setRequired("Y");
        termDto.setRegDate(LocalDateTime.now());
        termDto.setRegId("admin");
        termDto.setUpDate(LocalDateTime.now());
        termDto.setUpId("admin");

        // Term 데이터 삽입
        termDao.insertTerm(termDto);

        // Term 데이터 삽입 후 termId가 설정되었는지 확인
        assertNotNull("termId should not be null", termDto.getTermId());
    }

    @Test
    public void testInsertTerm() {
        // 새로운 Term 데이터 삽입 테스트
        TermDto newTermDto = new TermDto();
        newTermDto.setTermName("New Term Name");
        newTermDto.setTermContent("New Term Content");
        newTermDto.setRequired("N"); // 예시로 "N" 설정
        newTermDto.setRegDate(LocalDateTime.now());
        newTermDto.setRegId("admin");
        newTermDto.setUpDate(LocalDateTime.now());
        newTermDto.setUpId("admin");

        termDao.insertTerm(newTermDto);

        // 데이터 조회
        TermDto resultDto = termDao.getTermById(newTermDto.getTermId());
        assertNotNull(resultDto);
        assertEquals("New Term Name", resultDto.getTermName());
    }

    @Test
    public void testGetTermById() {
        // 데이터 조회 테스트
        TermDto resultDto = termDao.getTermById(termDto.getTermId());
        assertNotNull(resultDto);
        assertEquals("Test Term Name", resultDto.getTermName());
    }

    @Test
    public void testUpdateTerm() {
        // Term 데이터 업데이트 테스트
        termDto.setTermName("Updated Term Name");
        termDao.updateTerm(termDto);

        TermDto updatedDto = termDao.getTermById(termDto.getTermId());
        assertNotNull(updatedDto);
        assertEquals("Updated Term Name", updatedDto.getTermName());
    }

    @Test
    public void testDeleteTerm() {
        // 데이터 삭제 테스트
        termDao.deleteTerm(termDto.getTermId());

        TermDto resultDto = termDao.getTermById(termDto.getTermId());
        assertNull(resultDto);
    }

    @Test
    public void testDeleteAllTerms() {
        // 모든 Term 데이터 삭제 테스트
        termDao.deleteAllTerms();

        List<TermDto> resultList = termDao.getAllTerms();
        assertNotNull(resultList);
        assertEquals(0, resultList.size());
    }

    @Test
    public void testGetAllTerms() {
        // 데이터 준비
        TermDto anotherTermDto = new TermDto();
        anotherTermDto.setTermName("Another Term");
        anotherTermDto.setTermContent("Another Term Content");
        anotherTermDto.setRequired("Y");
        anotherTermDto.setRegDate(LocalDateTime.now());
        anotherTermDto.setRegId("admin");
        anotherTermDto.setUpDate(LocalDateTime.now());
        anotherTermDto.setUpId("admin");

        termDao.insertTerm(anotherTermDto);

        // 모든 데이터 조회
        List<TermDto> resultList = termDao.getAllTerms();
        assertNotNull(resultList);
        assertEquals(2, resultList.size()); // 2개의 데이터가 있어야 함
    }
}
