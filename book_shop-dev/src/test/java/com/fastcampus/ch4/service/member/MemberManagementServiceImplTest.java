package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dao.member.TermAgreeDao;
import com.fastcampus.ch4.dao.member.TermDao;
import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
import com.fastcampus.ch4.dto.member.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MemberManagementServiceImplTest {

  @InjectMocks
  private MemberManagementServiceImpl memberManagementService;

  @Mock
  private MemberDao memberDao;

  @Mock
  private TermAgreeDao termAgreeDao;

  @Mock
  private TermDao termDao;

  @Mock
  private TermService termService;

  @Mock
  private InfoChangeHistoryDao infoChangeHistoryDao;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpSession session;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAddMember_Success() {
    MemberDto member = new MemberDto();
    member.setId("testId");

    when(memberDao.isIdAvailable(member.getId())).thenReturn(0);

    memberManagementService.addMember(member);

    verify(memberDao, times(1)).insertMember(member);
  }

  @Test
  public void testAddMember_IdDuplicate() {
    MemberDto member = new MemberDto();
    member.setId("testId");

    when(memberDao.isIdAvailable(member.getId())).thenReturn(1);

    try {
      memberManagementService.addMember(member);
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertEquals("회원 ID가 이미 존재합니다.", e.getMessage());
    }

    verify(memberDao, never()).insertMember(member);
  }

  @Test
  public void testGetMemberById() {
    String id = "testId";
    MemberDto member = new MemberDto();
    when(memberDao.selectMemberById(id)).thenReturn(member);

    MemberDto result = memberManagementService.getMemberById(id);

    assertEquals(member, result);
  }

  @Test
  public void testGetAllMembers() {
    List<MemberDto> members = Arrays.asList(new MemberDto(), new MemberDto());
    when(memberDao.getAllMembers()).thenReturn(members);

    List<MemberDto> result = memberManagementService.getAllMembers();

    assertEquals(members, result);
  }

  @Test
  public void testUpdateMember() {
    MemberDto member = new MemberDto();

    memberManagementService.updateMember(member);

    verify(memberDao, times(1)).updateMember(member);
  }

  @Test
  public void testRemoveMember() {
    String id = "testId";

    memberManagementService.removeMember(id);

    verify(memberDao, times(1)).deleteMember(id);
  }

  @Test
  public void testRemoveAllMembers() {
    memberManagementService.removeAllMembers();

    verify(memberDao, times(1)).deleteAll();
  }

  @Test
  public void testIsIdAvailable() {
    String id = "testId";
    when(memberDao.isIdAvailable(id)).thenReturn(0);

    boolean result = memberManagementService.isIdAvailable(id);

    assertTrue(result);
  }

  @Test
  public void testIsIdDuplicate() {
    String id = "testId";
    when(memberDao.isIdAvailable(id)).thenReturn(1);

    boolean result = memberManagementService.isIdDuplicate(id);

    assertTrue(result);
  }

  @Test
  public void testSaveTermAgreements() {
    // Given
    TermAgreeDto termAgreeDto1 = new TermAgreeDto();
    termAgreeDto1.setId("user1");
    termAgreeDto1.setTermId(1);
    termAgreeDto1.setTermAgree("Y");
    termAgreeDto1.setRegId("user1");
    termAgreeDto1.setUpId("user1");

    TermAgreeDto termAgreeDto2 = new TermAgreeDto();
    termAgreeDto2.setId("user2");
    termAgreeDto2.setTermId(2);
    termAgreeDto2.setTermAgree("N");
    termAgreeDto2.setRegId("user2");
    termAgreeDto2.setUpId("user2");

    List<TermAgreeDto> termAgreeDtos = Arrays.asList(termAgreeDto1, termAgreeDto2);

    // When
    termService.saveTermAgreements(termAgreeDtos);

    // Then
    verify(termAgreeDao, times(1)).insertTermAgreements(termAgreeDtos);
  }

  @Test
  public void testGetAllTerms() {
    List<TermDto> terms = Arrays.asList(new TermDto(), new TermDto());
    when(termDao.getAllTerms()).thenReturn(terms);

    List<TermDto> result = memberManagementService.getAllTerms();

    assertEquals(terms, result);
  }

  @Test
  public void testGetCurrentUser_Success() {
    String userId = "testId";
    MemberDto member = new MemberDto();

    when(request.getSession(false)).thenReturn(session);
    when(session.getAttribute("id")).thenReturn(userId);
    when(memberDao.selectMemberById(userId)).thenReturn(member);

    MemberDto result = memberManagementService.getCurrentUser(request);

    assertEquals(member, result);
  }

  @Test
  public void testGetCurrentUser_SessionNull() {
    when(request.getSession(false)).thenReturn(null);

    try {
      memberManagementService.getCurrentUser(request);
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertEquals("세션이 존재하지 않습니다.", e.getMessage());
    }
  }

  @Test
  public void testGetCurrentUser_UserIdNull() {
    when(request.getSession(false)).thenReturn(session);
    when(session.getAttribute("id")).thenReturn(null);

    try {
      memberManagementService.getCurrentUser(request);
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertEquals("세션에서 사용자를 찾을 수 없습니다.", e.getMessage());
    }
  }

  @Test
  public void testUpdateUserProfile_Success() {
    // Given
    when(request.getSession(false)).thenReturn(session);
    when(session.getAttribute("id")).thenReturn("testId");

    MemberDto currentUser = new MemberDto();
    currentUser.setId("testId");
    MemberDto updatedUser = new MemberDto();
    updatedUser.setId("testId");

    when(memberDao.selectMemberById("testId")).thenReturn(currentUser);
    when(memberDao.updateMember(updatedUser)).thenReturn(true);

    doNothing().when(infoChangeHistoryDao).saveChangeHistory(any(InfoChangeHistoryDto.class));

    // When
    boolean result = memberManagementService.updateUserProfile(request, updatedUser);

    // Then
    assertTrue(result);
    verify(memberDao, times(1)).updateMember(updatedUser);
    verify(infoChangeHistoryDao, times(1)).saveChangeHistory(any(InfoChangeHistoryDto.class));
  }

  @Test
  public void testUpdateUserProfile_Failure() {
    // Given
    when(request.getSession(false)).thenReturn(session);
    when(session.getAttribute("id")).thenReturn("testId");

    MemberDto currentUser = new MemberDto();
    currentUser.setId("testId");
    MemberDto updatedUser = new MemberDto();
    updatedUser.setId("testId");

    when(memberDao.selectMemberById("testId")).thenReturn(currentUser);
    when(memberDao.updateMember(updatedUser)).thenReturn(false);

    doNothing().when(infoChangeHistoryDao).saveChangeHistory(any(InfoChangeHistoryDto.class));

    // When
    boolean result = memberManagementService.updateUserProfile(request, updatedUser);

    // Then
    assertFalse(result);
    verify(memberDao, times(1)).updateMember(updatedUser);
    verify(infoChangeHistoryDao, never()).saveChangeHistory(any(InfoChangeHistoryDto.class));
  }
}
