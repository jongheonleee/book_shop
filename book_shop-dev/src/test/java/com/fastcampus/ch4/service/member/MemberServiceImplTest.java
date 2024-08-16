package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.*;
import com.fastcampus.ch4.dto.member.*;
import com.fastcampus.ch4.service.member.MemberServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class MemberServiceImplTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private LoginHistoryDao loginHistoryDao;

  @Autowired
  private TermAgreeDao termAgreeDao;

  @Autowired
  private TermDao termDao;

  @Autowired
  private InfoChangeHistoryDao infoChangeHistoryDao;

  @Before
  public void setUp() {
    // Set up initial data for tests
    MemberDto member1 = new MemberDto();
    member1.setId("testId");
    member1.setPswd("testPassword");
    member1.setPhnNumb("1234567890");
    member1.setEmail("test@example.com");
    memberDao.insertMember(member1);

    MemberDto member2 = new MemberDto();
    member2.setId("duplicateId");
    member2.setPswd("password1");
    memberDao.insertMember(member2);

    // Additional setup if needed (e.g., loginHistoryDao, termAgreeDao, etc.)
  }

  @After
  public void tearDown() {
    // Clean up data after each test
    memberDao.deleteAll();
    loginHistoryDao.deleteAll();
    termAgreeDao.deleteAllTermAgrees();
    termDao.deleteAllTerms();
    infoChangeHistoryDao.deleteAllChangeHistories();

    System.out.println("Data cleanup completed");
  }

  @Test
  public void testAddMember() {
    MemberDto member = new MemberDto();
    member.setId("newTestId");
    member.setPswd("newPassword");
    member.setPhnNumb("0987654321");
    member.setEmail("newtest@example.com");

    memberService.addMember(member);

    MemberDto fetchedMember = memberDao.selectMemberById("newTestId");
    assertNotNull(fetchedMember);
    assertEquals("newTestId", fetchedMember.getId());
  }

  @Test(expected = RuntimeException.class)
  public void testAddMemberWithDuplicateId() {
    MemberDto member1 = new MemberDto();
    member1.setId("duplicateId");
    member1.setPswd("password1");

    MemberDto member2 = new MemberDto();
    member2.setId("duplicateId");
    member2.setPswd("password2");

    memberService.addMember(member1);
    memberService.addMember(member2); // Should throw exception due to duplicate ID
  }

  @Test
  public void testUpdateMember() {
    MemberDto member = new MemberDto();
    member.setId("testId");
    member.setPswd("updatedPassword");

    memberService.updateMember(member);

    MemberDto updatedMember = memberDao.selectMemberById("testId");
    assertNotNull(updatedMember);
    assertEquals("updatedPassword", updatedMember.getPswd());
  }

  @Test
  public void testRemoveMember() {
    MemberDto member = new MemberDto();
    member.setId("removeId");
    member.setPswd("password");
    memberDao.insertMember(member);

    memberService.removeMember("removeId");

    MemberDto deletedMember = memberDao.selectMemberById("removeId");
    assertNull(deletedMember);
  }

  @Test
  public void testLoginSuccess() {
    MemberDto member = new MemberDto();
    member.setId("loginId");
    member.setPswd("correctPassword");
    memberDao.insertMember(member);

    HttpServletRequest request = new MockHttpServletRequest(); // Use a mock HTTP request
    HttpSession session = new MockHttpSession();
    ((MockHttpServletRequest) request).setSession(session);
    session.setAttribute("userId", "loginId");

    boolean result = memberService.login("loginId", "correctPassword", request);
    assertTrue(result);
  }

  @Test
  public void testUpdateUserProfile() {
    MemberDto member = new MemberDto();
    member.setId("profileId");
    member.setPswd("oldPassword");
    memberDao.insertMember(member);

    HttpServletRequest request = new MockHttpServletRequest();
    HttpSession session = new MockHttpSession();
    ((MockHttpServletRequest) request).setSession(session);
    session.setAttribute("userId", "profileId");

    MemberDto updatedMember = new MemberDto();
    updatedMember.setId("profileId");
    updatedMember.setPswd("newPassword");
    updatedMember.setPhnNumb("0987654321");
    updatedMember.setEmail("new@example.com");

    boolean result = memberService.updateUserProfile(request, updatedMember);
    assertTrue(result);

    MemberDto fetchedMember = memberDao.selectMemberById("profileId");
    assertEquals("newPassword", fetchedMember.getPswd());
    assertEquals("0987654321", fetchedMember.getPhnNumb());
    assertEquals("new@example.com", fetchedMember.getEmail());
  }
}
