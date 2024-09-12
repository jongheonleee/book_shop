//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dao.member.LoginHistoryDao;
//import com.fastcampus.ch4.dto.member.LoginHistoryDto;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
//public class LoginHistoryServiceImplTest {
//
//  @Autowired
//  private LoginHistoryServiceImpl loginHistoryService;
//
//  @Autowired
//  private LoginHistoryDao loginHistoryDao;
//
//  private LoginHistoryDto loginHistory;
//
//  @Before
//  public void setUp() {
//    // 테스트를 위한 로그인 이력 생성
//    loginHistory = new LoginHistoryDto();
//    loginHistory.setId("testUser");
//    loginHistory.setIpAddr("127.0.0.1");
//    loginHistory.setLognTime(LocalDateTime.now());
//    loginHistory.setRegDate(LocalDateTime.now());  // reg_date 설정
//    loginHistory.setRegId("testUser");
//    loginHistory.setUpDate(LocalDateTime.now());
//    loginHistory.setUpId("testUser");
//  }
//
//  @After
//  public void tearDown() {
//    // 테스트가 끝난 후 데이터 정리
//    loginHistoryService.deleteAllLoginHistories();
//  }
//
//  @Test
//  public void testSaveLoginHistory() {
//    // 로그인 이력을 저장
//    loginHistoryService.saveLoginHistory(loginHistory);
//
//    // 로그인 이력이 데이터베이스에 정상적으로 저장되었는지 검증
//    List<LoginHistoryDto> histories = loginHistoryService.getAllLoginHistories();
//    assertTrue(histories.contains(loginHistory));
//  }
//
//  @Test
//  public void testGetAllLoginHistories() {
//    // 로그인 이력을 미리 저장
//    loginHistoryService.saveLoginHistory(loginHistory);
//
//    // 모든 로그인 이력을 가져와서 검증
//    List<LoginHistoryDto> result = loginHistoryService.getAllLoginHistories();
//    assertEquals(1, result.size());
//    assertEquals("testUser", result.get(0).getId());
//  }
//
//  @Test
//  public void testDeleteAllLoginHistories() {
//    // 로그인 이력을 미리 저장
//    loginHistoryService.saveLoginHistory(loginHistory);
//
//    // 모든 로그인 이력을 삭제
//    loginHistoryService.deleteAllLoginHistories();
//
//    // 로그인 이력이 모두 삭제되었는지 검증
//    List<LoginHistoryDto> result = loginHistoryService.getAllLoginHistories();
//    assertTrue(result.isEmpty());
//  }
//}
