//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dao.member.MemberDao;
//import com.fastcampus.ch4.dto.member.LoginHistoryDto;
//import com.fastcampus.ch4.dto.member.MemberDto;
//import com.fastcampus.ch4.service.member.AccountUnlockService;
//import com.fastcampus.ch4.service.member.LoginHistoryService;
//import com.fastcampus.ch4.service.member.MemberAuthenticationServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class MemberAuthenticationServiceImplTest {
//
//    @Mock
//    private MemberDao memberDao;
//
//    @Mock
//    private LoginHistoryService loginHistoryService;
//
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @Mock
//    private AccountUnlockService accountUnlockService;
//
//    @InjectMocks
//    private MemberAuthenticationServiceImpl memberAuthenticationService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testLogin_Success() {
//        // Given
//        String id = "testUser";
//        String password = "testPass";
//        String ipAddr = "127.0.0.1";
//        MemberDto member = new MemberDto();
//        member.setId(id);
//        member.setAcntLock("N");
//        member.setFailLognAtmt(0);
//
//        when(memberDao.selectMemberById(id)).thenReturn(member);
//        when(memberDao.validatePassword(id, password)).thenReturn(true);
//        when(request.getRemoteAddr()).thenReturn(ipAddr);
//
//        // When
//        boolean isAuthenticated = memberAuthenticationService.login(id, password, request);
//
//        // Then
//        assertTrue(isAuthenticated);
//        verify(loginHistoryService).saveLoginHistory(any(LoginHistoryDto.class));
//        verify(memberDao).updateMember(eq(member));
//
//        // Capture the LoginHistoryDto to check the details
//        ArgumentCaptor<LoginHistoryDto> loginHistoryCaptor = ArgumentCaptor.forClass(LoginHistoryDto.class);
//        verify(loginHistoryService).saveLoginHistory(loginHistoryCaptor.capture());
//        LoginHistoryDto capturedLoginHistory = loginHistoryCaptor.getValue();
//
//        assertEquals(id, capturedLoginHistory.getId());
//        assertEquals(ipAddr, capturedLoginHistory.getIpAddr());
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testLogin_NonExistingUser() {
//        // Given
//        String id = "nonExistentUser";
//        when(memberDao.selectMemberById(id)).thenReturn(null);
//
//        // When
//        memberAuthenticationService.login(id, "password", request);
//
//        // Then: Expect RuntimeException
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testLogin_LockedAccount() {
//        // Given
//        String id = "lockedUser";
//        MemberDto member = new MemberDto();
//        member.setId(id);
//        member.setAcntLock("Y");
//
//        when(memberDao.selectMemberById(id)).thenReturn(member);
//
//        // When
//        memberAuthenticationService.login(id, "password", request);
//
//        // Then: Expect RuntimeException
//    }
//
//    @Test
//    public void testHandleLoginFailure_AccountLockedAfterThreeFailures() {
//        // Given
//        String id = "testUser";
//        MemberDto member = new MemberDto();
//        member.setId(id);
//        member.setEmail("test@example.com"); // 이메일 설정
//        member.setFailLognAtmt(2); // Already 2 failed attempts
//
//        doNothing().when(accountUnlockService).generatePasswordResetToken(anyString());
//
//        // When
//        try {
//            memberAuthenticationService.handleLoginFailure(member);
//            fail("Expected RuntimeException to be thrown");
//        } catch (RuntimeException e) {
//            // Then
//            assertEquals("비밀번호 3회 틀렸습니다. 계정이 잠겼습니다.", e.getMessage());
//            assertEquals("Y", member.getAcntLock());
//            verify(accountUnlockService).generatePasswordResetToken(eq(member.getEmail()));
//            verify(memberDao).updateMember(eq(member));
//        }
//    }
//
//    @Test
//    public void testHandleLoginFailure_LoginAttemptsAndLocking() {
//        // Given
//        String id = "testUser";
//        MemberDto member = new MemberDto();
//        member.setId(id);
//        member.setEmail("test@example.com"); // 이메일 설정
//
//        // Set initial fail count to 0
//        member.setFailLognAtmt(0);
//
//        // 1st failure
//        try {
//            memberAuthenticationService.handleLoginFailure(member);
//        } catch (RuntimeException e) {
//            assertEquals("로그인 실패! 남은 기회: 2", e.getMessage());
//            assertEquals(1, member.getFailLognAtmt()); // Fail attempt count should be 1
//        }
//        verify(memberDao).updateMember(member);
//
//        // 2nd failure
//        member.setFailLognAtmt(1); // Manually setting the fail count to simulate second attempt
//        try {
//            memberAuthenticationService.handleLoginFailure(member);
//        } catch (RuntimeException e) {
//            assertEquals("로그인 실패! 남은 기회: 1", e.getMessage());
//            assertEquals(2, member.getFailLognAtmt()); // Fail attempt count should be 2
//        }
//        verify(memberDao, times(2)).updateMember(member);
//
//        // 3rd failure - account should be locked
//        member.setFailLognAtmt(2); // Manually setting the fail count to simulate third attempt
//        try {
//            memberAuthenticationService.handleLoginFailure(member);
//            fail("Expected RuntimeException to be thrown");
//        } catch (RuntimeException e) {
//            assertEquals("비밀번호 3회 틀렸습니다. 계정이 잠겼습니다.", e.getMessage());
//            assertEquals("Y", member.getAcntLock()); // Account should be locked
//            verify(accountUnlockService).generatePasswordResetToken(eq(member.getEmail()));
//        }
//        verify(memberDao, times(3)).updateMember(member);
//    }
//
//}
