package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TermDaoImplTest {

  @Autowired
  private TermDao termDao;

  private TermDto testTerm1;
  private TermDto testTerm2;

  @Before
  public void setUp() {
    // 테스트용 데이터 초기화
    testTerm1 = new TermDto(1, "Test Term 1", "Test Content 1", "Y", LocalDateTime.now(), "testuser1", LocalDateTime.now(), "testuser1");
    testTerm2 = new TermDto(2, "Test Term 2", "Test Content 2", "N", LocalDateTime.now(), "testuser2", LocalDateTime.now(), "testuser2");

    // 기존 데이터 정리 (필요시)
    termDao.deleteAllTerms(); // 모든 약관 삭제 (테스트 전)
  }

  @Test
  public void testInsertAndGetTermById() {
    // Insert 테스트
    termDao.insertTermAgreements(Arrays.asList(testTerm1));

    // Get by ID 테스트
    TermDto retrievedTerm = termDao.getTermById(testTerm1.getTermId());
    assertNotNull(retrievedTerm);
    assertEquals(testTerm1.getTermId(), retrievedTerm.getTermId());
    assertEquals(testTerm1.getTermName(), retrievedTerm.getTermName());
    assertEquals(testTerm1.getTermContent(), retrievedTerm.getTermContent());
    assertEquals(testTerm1.getRequired(), retrievedTerm.getRequired());
    assertEquals(testTerm1.getRegId(), retrievedTerm.getRegId());
    assertEquals(testTerm1.getUpId(), retrievedTerm.getUpId());
  }

  @Test
  public void testGetAllTerms() {
    // Insert 테스트
    termDao.insertTermAgreements(Arrays.asList(testTerm1, testTerm2));

    // Get all terms 테스트
    List<TermDto> terms = termDao.getAllTerms();
    assertEquals(2, terms.size()); // 두 개의 약관이 삽입되어야 함

    assertTrue(terms.stream().anyMatch(t -> t.getTermId() == testTerm1.getTermId()));
    assertTrue(terms.stream().anyMatch(t -> t.getTermId() == testTerm2.getTermId()));
  }

  @Test
  public void testUpdateTerm() {
    // Insert 테스트
    termDao.insertTermAgreements(Arrays.asList(testTerm1));

    // 기존 약관 정보 수정
    testTerm1.setTermName("Updated Term");
    testTerm1.setTermContent("Updated Content");
    testTerm1.setRequired("N");
    testTerm1.setUpDate(LocalDateTime.now().plusDays(1));
    testTerm1.setUpId("updateduser");

    termDao.updateTerm(testTerm1);

    // 업데이트 후 조회 테스트
    TermDto updatedTerm = termDao.getTermById(testTerm1.getTermId());
    assertNotNull(updatedTerm);
    assertEquals("Updated Term", updatedTerm.getTermName());
    assertEquals("Updated Content", updatedTerm.getTermContent());
    assertEquals("N", updatedTerm.getRequired());
    assertEquals("updateduser", updatedTerm.getUpId());
  }
}
