//package com.fastcampus.ch4.dao.member;
//
//import com.fastcampus.ch4.dto.member.MemberDto;
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
//import static org.junit.Assert.assertNull;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class MemberDaoImplTest {
//
//  @Autowired
//  private MemberDao memberDao;
//
//  @Before
//  public void setUp() {
//    // 데이터베이스 초기화
//    memberDao.deleteAll();
//
//    // 초기 데이터 설정
//    MemberDto initialDto = new MemberDto();
//    initialDto.setId("initialId");
//    initialDto.setEmail("initial@example.com");
//    initialDto.setPswd("initialPassword");
//    initialDto.setJoinDate(LocalDate.of(2024, 8, 10)); // LocalDate 사용
//    initialDto.setFailLognAtmt(0);
//    initialDto.setAcntLock("N");
//    initialDto.setIsMailVrfi("Y");
//    initialDto.setMailTokn(null);
//    initialDto.setName("Initial User");
//    initialDto.setBrthDate(LocalDate.of(1990, 1, 1)); // LocalDate 사용
//    initialDto.setIsAcntActv("Y");
//    initialDto.setAdsReceMthd("Email");
//    initialDto.setGrad("A");
//    initialDto.setIsCanc("N");
//    initialDto.setRegDate(LocalDate.of(2024, 8, 10)); // LocalDate 사용
//    initialDto.setRegId("admin");
//    initialDto.setUpDate(LocalDateTime.of(2024, 8, 10, 0, 0, 0)); // LocalDateTime 사용
//    initialDto.setUpId("admin");
//    memberDao.insertMember(initialDto);
//  }
//
//  @Test
//  public void testInsertMember() {
//    MemberDto memberDto = new MemberDto();
//    memberDto.setId("testId");
//    memberDto.setEmail("test@example.com");
//    memberDto.setPswd("testPassword");
//    memberDto.setJoinDate(LocalDate.of(2024, 8, 11)); // LocalDate 사용
//    memberDto.setFailLognAtmt(1);
//    memberDto.setAcntLock("N");
//    memberDto.setIsMailVrfi("Y");
//    memberDto.setMailTokn("token123");
//    memberDto.setName("Test User");
//    memberDto.setBrthDate(LocalDate.of(1995, 5, 15)); // LocalDate 사용
//    memberDto.setIsAcntActv("Y");
//    memberDto.setAdsReceMthd("SMS");
//    memberDto.setGrad("B");
//    memberDto.setIsCanc("N");
//    memberDto.setRegDate(LocalDate.of(2024, 8, 11)); // LocalDate 사용
//    memberDto.setRegId("user");
//    memberDto.setUpDate(LocalDateTime.of(2024, 8, 11, 10, 0, 0)); // LocalDateTime 사용
//    memberDto.setUpId("user");
//
//    memberDao.insertMember(memberDto);
//
//    MemberDto result = memberDao.selectMemberById("testId");
//    assertNotNull(result);
//    assertEquals("testId", result.getId());
//    assertEquals("test@example.com", result.getEmail());
//
//    //없는 것 조회
//  }
//
//  @Test
//  public void testUpdateMember() {
//    MemberDto memberDto = new MemberDto();
//    memberDto.setId("testId");
//    memberDto.setEmail("test@example.com");
//    memberDto.setPswd("testPassword");
//    memberDto.setJoinDate(LocalDate.of(2024, 8, 11)); // LocalDate 사용
//    memberDto.setFailLognAtmt(1);
//    memberDto.setAcntLock("N");
//    memberDto.setIsMailVrfi("Y");
//    memberDto.setMailTokn("token123");
//    memberDto.setName("Test User");
//    memberDto.setBrthDate(LocalDate.of(1995, 5, 15)); // LocalDate 사용
//    memberDto.setIsAcntActv("Y");
//    memberDto.setAdsReceMthd("SMS");
//    memberDto.setGrad("B");
//    memberDto.setIsCanc("N");
//    memberDto.setRegDate(LocalDate.of(2024, 8, 11)); // LocalDate 사용
//    memberDto.setRegId("user");
//    memberDto.setUpDate(LocalDateTime.of(2024, 8, 11, 10, 0, 0)); // LocalDateTime 사용
//    memberDto.setUpId("user");
//
//    memberDao.insertMember(memberDto);
//    //인서트 됐는지 확인
//    memberDto.setPswd("updatedPassword");
//    memberDao.updateMember(memberDto);
//
//    MemberDto result = memberDao.selectMemberById("testId");
//    assertNotNull(result);
//    assertEquals(memberDto.getPswd(), result.getPswd()); //하드코딩 조심
//  }
//
//  @Test
//  public void testDeleteMember() {
//    MemberDto memberDto = new MemberDto();
//    memberDto.setId("testId");
//    memberDto.setEmail("test@example.com");
//    memberDto.setPswd("testPassword");
//    memberDto.setJoinDate(LocalDate.of(2024, 8, 11)); // LocalDate 사용
//    memberDto.setFailLognAtmt(1);
//    memberDto.setAcntLock("N");
//    memberDto.setIsMailVrfi("Y");
//    memberDto.setMailTokn("token123");
//    memberDto.setName("Test User");
//    memberDto.setBrthDate(LocalDate.of(1995, 5, 15)); // LocalDate 사용
//    memberDto.setIsAcntActv("Y");
//    memberDto.setAdsReceMthd("SMS");
//    memberDto.setGrad("B");
//    memberDto.setIsCanc("N");
//    memberDto.setRegDate(LocalDate.of(2024, 8, 11)); // LocalDate 사용
//    memberDto.setRegId("user");
//    memberDto.setUpDate(LocalDateTime.of(2024, 8, 11, 10, 0, 0)); // LocalDateTime 사용
//    memberDto.setUpId("user");
//
//    memberDao.insertMember(memberDto);
//    memberDao.deleteMember(memberDto.getId());
//
//    MemberDto result = memberDao.selectMemberById(memberDto.getId());
//    assertNull(result);
//  }
//
//  @Test
//  public void testSelectAllMembers() {
//    MemberDto member1 = new MemberDto();
//    member1.setId("testId1");
//    member1.setEmail("test1@example.com");
//    member1.setPswd("password1");
//    member1.setJoinDate(LocalDate.of(2024, 8, 12)); // LocalDate 사용
//    member1.setFailLognAtmt(0);
//    member1.setAcntLock("N");
//    member1.setIsMailVrfi("Y");
//    member1.setMailTokn(null);
//    member1.setName("User One");
//    member1.setBrthDate(LocalDate.of(1992, 2, 20)); // LocalDate 사용
//    member1.setIsAcntActv("Y");
//    member1.setAdsReceMthd("Email");
//    member1.setGrad("C");
//    member1.setIsCanc("N");
//    member1.setRegDate(LocalDate.of(2024, 8, 12)); // LocalDate 사용
//    member1.setRegId("user1");
//    member1.setUpDate(LocalDateTime.of(2024, 8, 12, 0, 0, 0)); // LocalDateTime 사용
//    member1.setUpId("user1");
//
//    MemberDto member2 = new MemberDto();
//    member2.setId("testId2");
//    member2.setEmail("test2@example.com");
//    member2.setPswd("password2");
//    member2.setJoinDate(LocalDate.of(2024, 8, 12)); // LocalDate 사용
//    member2.setFailLognAtmt(0);
//    member2.setAcntLock("N");
//    member2.setIsMailVrfi("Y");
//    member2.setMailTokn(null);
//    member2.setName("User Two");
//    member2.setBrthDate(LocalDate.of(1985, 3, 15)); // LocalDate 사용
//    member2.setIsAcntActv("Y");
//    member2.setAdsReceMthd("SMS");
//    member2.setGrad("D");
//    member2.setIsCanc("N");
//    member2.setRegDate(LocalDate.of(2024, 8, 12)); // LocalDate 사용
//    member2.setRegId("user2");
//    member2.setUpDate(LocalDateTime.of(2024, 8, 12, 0, 0, 0)); // LocalDateTime 사용
//    member2.setUpId("user2");
//
//    memberDao.insertMember(member1);
//    memberDao.insertMember(member2);
//
//    List<MemberDto> result = memberDao.getAllMembers();
//    assertNotNull(result);
//    assertEquals(3, result.size()); // 기존 데이터와 삽입한 두 데이터 포함, 내용까지 비교할 것
//  }
//}
