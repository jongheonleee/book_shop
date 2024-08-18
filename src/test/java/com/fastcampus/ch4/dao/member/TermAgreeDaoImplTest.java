package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.service.member.TermService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) // Spring 설정 파일 경로
public class TermAgreeDaoImplTest {

  @Autowired
  private TermAgreeDao termAgreeDao;

  @Autowired
  private TermService termService; // 서비스 추가

  // 테스트 전 데이터베이스 초기화
  @Before
  public void setUp() {
    termAgreeDao.deleteAllTermAgreements(); // 모든 약관 동의 삭제
  }

  @Test
  public void testInsertAndGetTermAgreement() {
    TermAgreeDto termAgreeDto = new TermAgreeDto();
    termAgreeDto.setId("testUser");
    termAgreeDto.setTermId(1);
    termAgreeDto.setTermAgree("Y");
    termAgreeDto.setRegDate(LocalDateTime.now());
    termAgreeDto.setRegId("testUser");
    termAgreeDto.setUpDate(LocalDateTime.now());
    termAgreeDto.setUpId("testUser");

    termService.saveTermAgreements(Arrays.asList(termAgreeDto)); // 리스트로 전달

    TermAgreeDto retrieved = termAgreeDao.getTermAgree("testUser", 1);

    Assert.assertNotNull(retrieved);
    Assert.assertEquals("testUser", retrieved.getId());
    Assert.assertEquals(1, retrieved.getTermId());
    Assert.assertEquals("Y", retrieved.getTermAgree());
  }

  @Test
  public void testUpdateTermAgreement() {
    TermAgreeDto termAgreeDto = new TermAgreeDto();
    termAgreeDto.setId("testUser");
    termAgreeDto.setTermId(1);
    termAgreeDto.setTermAgree("Y");
    termAgreeDto.setRegDate(LocalDateTime.now());
    termAgreeDto.setRegId("testUser");
    termAgreeDto.setUpDate(LocalDateTime.now());
    termAgreeDto.setUpId("testUser");

    termService.saveTermAgreements(Arrays.asList(termAgreeDto)); // 리스트로 전달

    termAgreeDto.setTermAgree("N");
    termAgreeDao.updateTermAgreement(termAgreeDto); // 업데이트 메서드가 리스트가 아닌 단일 객체를 처리한다고 가정

    TermAgreeDto updated = termAgreeDao.getTermAgree("testUser", 1);

    Assert.assertNotNull(updated);
    Assert.assertEquals("N", updated.getTermAgree());
  }

  @Test
  public void testDeleteTermAgreement() {
    TermAgreeDto termAgreeDto = new TermAgreeDto();
    termAgreeDto.setId("testUser");
    termAgreeDto.setTermId(1);
    termAgreeDto.setTermAgree("Y");
    termAgreeDto.setRegDate(LocalDateTime.now());
    termAgreeDto.setRegId("testUser");
    termAgreeDto.setUpDate(LocalDateTime.now());
    termAgreeDto.setUpId("testUser");

    termService.saveTermAgreements(Arrays.asList(termAgreeDto)); // 리스트로 전달
    termAgreeDao.deleteTermAgreement("testUser", 1); // 단일 객체 삭제 메서드

    TermAgreeDto deleted = termAgreeDao.getTermAgree("testUser", 1);

    Assert.assertNull(deleted);
  }

  @Test
  public void testDeleteAllTermAgreements() {
    TermAgreeDto termAgreeDto1 = new TermAgreeDto();
    termAgreeDto1.setId("testUser1");
    termAgreeDto1.setTermId(1);
    termAgreeDto1.setTermAgree("Y");
    termAgreeDto1.setRegDate(LocalDateTime.now());
    termAgreeDto1.setRegId("testUser1");
    termAgreeDto1.setUpDate(LocalDateTime.now());
    termAgreeDto1.setUpId("testUser1");

    TermAgreeDto termAgreeDto2 = new TermAgreeDto();
    termAgreeDto2.setId("testUser2");
    termAgreeDto2.setTermId(2);
    termAgreeDto2.setTermAgree("N");
    termAgreeDto2.setRegDate(LocalDateTime.now());
    termAgreeDto2.setRegId("testUser2");
    termAgreeDto2.setUpDate(LocalDateTime.now());
    termAgreeDto2.setUpId("testUser2");

    termService.saveTermAgreements(Arrays.asList(termAgreeDto1, termAgreeDto2)); // 리스트로 전달

    termAgreeDao.deleteAllTermAgreements();

    List<TermAgreeDto> allAgreements1 = termAgreeDao.getTermAgreements("testUser1");
    List<TermAgreeDto> allAgreements2 = termAgreeDao.getTermAgreements("testUser2");

    Assert.assertTrue(allAgreements1.isEmpty());
    Assert.assertTrue(allAgreements2.isEmpty());
  }
}
