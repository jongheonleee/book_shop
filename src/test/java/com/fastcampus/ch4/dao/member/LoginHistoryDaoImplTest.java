//package com.fastcampus.ch4.dao.member;
//
//import com.fastcampus.ch4.dto.member.LoginHistoryDto;
//import com.fastcampus.ch4.dto.member.MemberDto;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class LoginHistoryDaoImplTest {
//
//  @Autowired
//  private LoginHistoryDao loginHistoryDao;
//
//  @Autowired
//  private MemberDao memberDao;
//
//  private String uniqueId;
//
//  @Before
//  public void setUp() {
//    // 모든 로그인 기록 삭제
//    loginHistoryDao.deleteAll();
//
//    // 모든 회원 데이터 삭제
//    memberDao.deleteAll();
//
//    // 고유 ID 생성
//    uniqueId = "testId_" + System.currentTimeMillis();
//
//    // 부모 테이블 데이터 준비
//    MemberDto memberDto = new MemberDto();
//    memberDto.setId(uniqueId); // 외래키로 사용될 고유 ID
//    memberDto.setPswd("testPassword");
//    memberDto.setEmail("test@example.com");
//    memberDto.setPhnNumb("123-456-7890");
//    memberDto.setJoinDate(LocalDate.now());
//    memberDto.setFailLognAtmt(0);
//    memberDto.setAcntLock("N");
//    memberDto.setIsMailVrfi("Y");
//    memberDto.setMailTokn("token123");
//    memberDto.setName("Test User");
//    memberDto.setBrthDate(LocalDate.of(1990, 1, 1));
//    memberDto.setIsAcntActv("Y");
//    memberDto.setAdsReceMthd("Email");
//    memberDto.setGrad("A");
//    memberDto.setIsCanc("N");
//    memberDto.setRegDate(LocalDate.now());
//    memberDto.setRegId("admin");
//    memberDto.setUpDate(LocalDateTime.now());
//    memberDto.setUpId("admin");
//
//    memberDao.insertMember(memberDto); // 부모 테이블에 데이터 삽입
//  }
//
//  @After
//  public void tearDown() {
//    // 로그인 기록과 회원 데이터 삭제
//    loginHistoryDao.deleteAll();
//    memberDao.deleteAll();
//  }
//
//  @Test
//  public void testInsertLoginHistory() {
//    LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
//    loginHistoryDto.setLognId(1); // 로그 ID
//    loginHistoryDto.setLognTime(LocalDateTime.now());
//    loginHistoryDto.setIpAddr("192.168.1.1");
//    loginHistoryDto.setRegDate(LocalDateTime.now());
//    loginHistoryDto.setRegId("admin");
//    loginHistoryDto.setUpDate(LocalDateTime.now());
//    loginHistoryDto.setUpId("admin");
//    loginHistoryDto.setId(uniqueId); // 부모 테이블의 고유 ID
//
//    int result = loginHistoryDao.insertLoginHistory(loginHistoryDto);
//
//    assertEquals(1, result); // 삽입 성공 시 1이 반환되어야 함
//
//    List<LoginHistoryDto> resultList = loginHistoryDao.selectLoginHistoryById(uniqueId);
//    assertNotNull(resultList);
//    assertEquals(1, resultList.size());
//
//    LoginHistoryDto resultDto = resultList.get(0);
//    assertEquals("192.168.1.1", resultDto.getIpAddr());
//  }
//
//  @Test
//  public void testSelectLoginHistoryById() {
//    // 데이터 준비
//    LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
//    loginHistoryDto.setLognId(2); // 로그 ID
//    loginHistoryDto.setLognTime(LocalDateTime.now());
//    loginHistoryDto.setIpAddr("192.168.1.1");
//    loginHistoryDto.setRegDate(LocalDateTime.now());
//    loginHistoryDto.setRegId("admin");
//    loginHistoryDto.setUpDate(LocalDateTime.now());
//    loginHistoryDto.setUpId("admin");
//    loginHistoryDto.setId(uniqueId); // 부모 테이블의 고유 ID
//
//    loginHistoryDao.insertLoginHistory(loginHistoryDto);
//
//    // 데이터 조회
//    List<LoginHistoryDto> resultList = loginHistoryDao.selectLoginHistoryById(uniqueId);
//    assertNotNull(resultList);
//    assertEquals(1, resultList.size());
//
//    LoginHistoryDto resultDto = resultList.get(0);
//    assertEquals("192.168.1.1", resultDto.getIpAddr());
//  }
//
//  @Test
//  public void testGetAllLoginHistories() {
//    // 데이터 준비
//    LoginHistoryDto loginHistoryDto1 = new LoginHistoryDto();
//    loginHistoryDto1.setLognId(3); // 로그 ID
//    loginHistoryDto1.setLognTime(LocalDateTime.now());
//    loginHistoryDto1.setIpAddr("192.168.1.1");
//    loginHistoryDto1.setRegDate(LocalDateTime.now());
//    loginHistoryDto1.setRegId("admin");
//    loginHistoryDto1.setUpDate(LocalDateTime.now());
//    loginHistoryDto1.setUpId("admin");
//    loginHistoryDto1.setId(uniqueId); // 부모 테이블의 고유 ID
//
//    LoginHistoryDto loginHistoryDto2 = new LoginHistoryDto();
//    loginHistoryDto2.setLognId(4); // 로그 ID
//    loginHistoryDto2.setLognTime(LocalDateTime.now());
//    loginHistoryDto2.setIpAddr("192.168.1.2");
//    loginHistoryDto2.setRegDate(LocalDateTime.now());
//    loginHistoryDto2.setRegId("admin");
//    loginHistoryDto2.setUpDate(LocalDateTime.now());
//    loginHistoryDto2.setUpId("admin");
//    loginHistoryDto2.setId(uniqueId); // 부모 테이블의 고유 ID
//
//    loginHistoryDao.insertLoginHistory(loginHistoryDto1);
//    loginHistoryDao.insertLoginHistory(loginHistoryDto2);
//
//    // 데이터 조회
//    List<LoginHistoryDto> resultList = loginHistoryDao.getAllLoginHistories();
//    assertNotNull(resultList);
//    assertEquals(2, resultList.size()); // 두 개의 기록이 있어야 함
//  }
//
//  @Test
//  public void testDeleteAll() {
//    // 데이터 준비
//    LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
//    loginHistoryDto.setLognId(5); // 로그 ID
//    loginHistoryDto.setLognTime(LocalDateTime.now());
//    loginHistoryDto.setIpAddr("192.168.1.1");
//    loginHistoryDto.setRegDate(LocalDateTime.now());
//    loginHistoryDto.setRegId("admin");
//    loginHistoryDto.setUpDate(LocalDateTime.now());
//    loginHistoryDto.setUpId("admin");
//    loginHistoryDto.setId(uniqueId); // 부모 테이블의 고유 ID
//
//    loginHistoryDao.insertLoginHistory(loginHistoryDto);
//    loginHistoryDao.deleteAll();
//
//    // 모든 로그인 기록 조회
//    List<LoginHistoryDto> resultList = loginHistoryDao.getAllLoginHistories();
//    assertNotNull(resultList);
//    assertEquals(0, resultList.size()); // 삭제 후 기록이 없어야 함
//  }
//}
