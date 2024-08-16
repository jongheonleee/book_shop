//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dao.member.TermAgreeDao;
//import com.fastcampus.ch4.dao.member.TermDao;
//import com.fastcampus.ch4.dto.member.TermAgreeDto;
//import com.fastcampus.ch4.dto.member.TermDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
//public class TermServiceImplTest {
//
//    @Autowired
//    private TermService termService;
//
//    @Autowired
//    private TermDao termDao;
//
//    @Autowired
//    private TermAgreeDao termAgreeDao;
//
//    @Before
//    public void setUp() {
//        // 데이터베이스 초기화
//        termDao.deleteAllTerms();  // 모든 약관 삭제 (테스트 전 초기화)
//        termAgreeDao.deleteAllTermAgrees();  // 모든 약관 동의 삭제 (테스트 전 초기화)
//
//        // 약관 데이터 설정
//        termDao.insertTerm(new TermDto(1, "약관 1", "내용 1", "Y", LocalDateTime.now(), "admin", LocalDateTime.now(), "admin"));
//        termDao.insertTerm(new TermDto(2, "약관 2", "내용 2", "N", LocalDateTime.now(), "admin", LocalDateTime.now(), "admin"));
//
//        // 약관 동의 데이터 설정
//        termAgreeDao.insertTermAgree(new TermAgreeDto("member1", 1, "Y", LocalDateTime.now(), "member1", LocalDateTime.now(), "member1"));
//        termAgreeDao.insertTermAgree(new TermAgreeDto("member1", 2, "N", LocalDateTime.now(), "member1", LocalDateTime.now(), "member1"));
//    }
//
//    @Test
//    public void testGetAllTerms() {
//        List<TermDto> terms = termService.getAllTerms();
//        assertEquals(2, terms.size());
//    }
//
//    @Test
//    public void testGetTermById() {
//        TermDto term = termService.getTermById(1);
//        assertNotNull(term);
//        assertEquals(1, term.getTermId());
//        assertEquals("약관 1", term.getTermName());
//    }
//
//    @Test
//    public void testValidateTermAgreements_valid() {
//        List<TermAgreeDto> termAgreeDtos = Arrays.asList(
//                new TermAgreeDto("member1", 1, "Y", LocalDateTime.now(), "member1", LocalDateTime.now(), "member1"),
//                new TermAgreeDto("member1", 2, "N", LocalDateTime.now(), "member1", LocalDateTime.now(), "member1")
//        );
//        termService.validateTermAgreements(termAgreeDtos);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testValidateTermAgreements_invalid() {
//        List<TermAgreeDto> termAgreeDtos = Arrays.asList(
//                new TermAgreeDto("member1", 2, "N", LocalDateTime.now(), "member1", LocalDateTime.now(), "member1")
//        );
//        termService.validateTermAgreements(termAgreeDtos);
//    }
//
//    @Test
//    public void testSaveTermAgreements() {
//        termService.saveTermAgreements("member2", Arrays.asList(
//                new TermAgreeDto("member2", 1, "Y", LocalDateTime.now(), "member2", LocalDateTime.now(), "member2"),
//                new TermAgreeDto("member2", 2, "Y", LocalDateTime.now(), "member2", LocalDateTime.now(), "member2")
//        ));
//
//        List<TermAgreeDto> agreements = termAgreeDao.getTermAgreements("member2");
//        assertEquals(2, agreements.size());
//    }
//
//    @Test
//    public void testGetAllTermIds() {
//        List<Integer> termIds = termService.getAllTermIds();
//        assertEquals(Arrays.asList(1, 2), termIds);
//    }
//
//    @Test
//    public void testGetTermAgreements() {
//        List<TermAgreeDto> agreements = termService.getTermAgreements("member1");
//        assertEquals(2, agreements.size());
//    }
//}
