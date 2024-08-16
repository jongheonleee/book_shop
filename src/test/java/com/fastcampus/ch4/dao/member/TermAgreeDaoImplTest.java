package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TermAgreeDaoImplTest {

  @Autowired
  private TermAgreeDao termAgreeDao;

  @Autowired
  private TermDao termDao;

  private TermAgreeDto termAgreeDto;

  @Before
  public void setUp() {
    // 모든 TermAgree 데이터 삭제
    termAgreeDao.deleteAllTermAgrees();

    // 모든 Term 데이터 삭제
    termDao.deleteAllTerms();

    // Term 데이터 준비 및 삽입
    TermDto termDto1 = new TermDto();
    termDto1.setTermId(1);
    termDto1.setTermName("Sample Term 1");
    termDto1.setTermContent("Content for Sample Term 1");
    termDto1.setRequired("Y");
    termDto1.setRegDate(LocalDateTime.now());
    termDto1.setRegId("admin");
    termDto1.setUpDate(LocalDateTime.now());
    termDto1.setUpId("admin");
    termDao.insertTerm(termDto1);

    TermDto termDto2 = new TermDto();
    termDto2.setTermId(2);
    termDto2.setTermName("Sample Term 2");
    termDto2.setTermContent("Content for Sample Term 2");
    termDto2.setRequired("N");
    termDto2.setRegDate(LocalDateTime.now());
    termDto2.setRegId("admin");
    termDto2.setUpDate(LocalDateTime.now());
    termDto2.setUpId("admin");
    termDao.insertTerm(termDto2);

    TermDto termDto3 = new TermDto();
    termDto3.setTermId(3);
    termDto3.setTermName("Sample Term 3");
    termDto3.setTermContent("Content for Sample Term 3");
    termDto3.setRequired("Y");
    termDto3.setRegDate(LocalDateTime.now());
    termDto3.setRegId("admin");
    termDto3.setUpDate(LocalDateTime.now());
    termDto3.setUpId("admin");
    termDao.insertTerm(termDto3);

    // TermAgree 데이터 준비
    termAgreeDto = new TermAgreeDto();
    termAgreeDto.setId("user01");
    termAgreeDto.setTermId(1); // Term 테이블에 존재하는 term_id 값을 사용
    termAgreeDto.setTermAgree("Y");
    termAgreeDto.setRegDate(LocalDateTime.now());
    termAgreeDto.setRegId("admin");
    termAgreeDto.setUpDate(LocalDateTime.now());
    termAgreeDto.setUpId("admin");

    // TermAgree 데이터 삽입
    termAgreeDao.insertTermAgree(termAgreeDto);
  }

  @Test
  public void testInsertTermAgree() {
    // 새로운 TermAgree 데이터 삽입 테스트
    TermAgreeDto newTermAgreeDto = new TermAgreeDto();
    newTermAgreeDto.setId("user02");
    newTermAgreeDto.setTermId(2);
    newTermAgreeDto.setTermAgree("N");
    newTermAgreeDto.setRegDate(LocalDateTime.now());
    newTermAgreeDto.setRegId("admin");
    newTermAgreeDto.setUpDate(LocalDateTime.now());
    newTermAgreeDto.setUpId("admin");

    termAgreeDao.insertTermAgree(newTermAgreeDto);

    // 데이터 조회
    TermAgreeDto resultDto = termAgreeDao.selectTermAgreeById("user02");
    assertNotNull(resultDto);
    assertEquals("N", resultDto.getTermAgree());
  }

  @Test
  public void testUpdateTermAgree() {
    // TermAgree 데이터 업데이트 테스트
    termAgreeDto.setTermAgree("N");
    termAgreeDao.updateTermAgree(termAgreeDto);

    TermAgreeDto updatedDto = termAgreeDao.selectTermAgreeById("user01");
    assertNotNull(updatedDto);
    assertEquals("N", updatedDto.getTermAgree());
  }

  @Test
  public void testDeleteTermAgree() {
    // 데이터 삭제 테스트
    termAgreeDao.deleteTermAgree("user01");

    TermAgreeDto resultDto = termAgreeDao.selectTermAgreeById("user01");
    assertNull(resultDto);
  }

  @Test
  public void testDeleteAllTermAgrees() {
    // 모든 TermAgree 데이터 삭제 테스트
    termAgreeDao.deleteAllTermAgrees();

    List<TermAgreeDto> resultList = termAgreeDao.selectAllTermAgrees();
    assertNotNull(resultList);
    assertEquals(0, resultList.size());
  }

  @Test
  public void testSelectAllTermAgrees() {
    // 데이터 준비
    TermAgreeDto anotherTermAgreeDto = new TermAgreeDto();
    anotherTermAgreeDto.setId("user03");
    anotherTermAgreeDto.setTermId(3);
    anotherTermAgreeDto.setTermAgree("Y");
    anotherTermAgreeDto.setRegDate(LocalDateTime.now());
    anotherTermAgreeDto.setRegId("admin");
    anotherTermAgreeDto.setUpDate(LocalDateTime.now());
    anotherTermAgreeDto.setUpId("admin");

    termAgreeDao.insertTermAgree(anotherTermAgreeDto);

    // 모든 데이터 조회
    List<TermAgreeDto> resultList = termAgreeDao.selectAllTermAgrees();
    assertNotNull(resultList);
    assertEquals(2, resultList.size()); // 2개의 데이터가 있어야 함 (기존 데이터 + 새로운 데이터)
  }

  @Test
  public void testGetTermAgree() {
    // 특정 회원의 약관 동의 이력 조회 테스트
    TermAgreeDto resultDto = termAgreeDao.getTermAgree("user01", 1);
    assertNotNull(resultDto);
    assertEquals("Y", resultDto.getTermAgree());
  }
}
