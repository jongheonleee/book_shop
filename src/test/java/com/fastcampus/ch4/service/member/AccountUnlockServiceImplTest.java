package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountUnlockServiceImplTest {

  @InjectMocks
  private AccountUnlockServiceImpl accountUnlockService;

  @Mock
  private MemberDao memberDao;

  @Mock
  private JavaMailSender javaMailSender;

  private MemberDto member;

  @Before
  public void setUp() {
    member = new MemberDto();
    member.setEmail("testuser@example.com");
    member.setMailTokn(null);
  }

  @Test
  public void testGeneratePasswordResetToken_Success() {
    // Arrange
    String token = "test-token";
    Mockito.when(memberDao.findUsernameByEmail("testuser@example.com")).thenReturn(member);
    Mockito.doNothing().when(javaMailSender).send(Mockito.any(SimpleMailMessage.class)); // 올바르게 모킹

    // Act
    accountUnlockService.generatePasswordResetToken("testuser@example.com");

    // Assert
    assertNotNull(member.getMailTokn());
    assertTrue(member.getMailTokn().length() > 0);
    Mockito.verify(memberDao, Mockito.times(1)).updateMember(member);
    Mockito.verify(javaMailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
  }

  @Test
  public void testValidatePasswordResetToken_ValidToken() {
    // Arrange
    String token = "valid-token";
    member.setMailTokn(token);
    Mockito.when(memberDao.selectMemberByToken(token)).thenReturn(member);

    // Act
    boolean isValid = accountUnlockService.validatePasswordResetToken(token);

    // Assert
    assertTrue(isValid);
  }

  @Test
  public void testValidatePasswordResetToken_InvalidToken() {
    // Arrange
    String token = "invalid-token";
    Mockito.when(memberDao.selectMemberByToken(token)).thenReturn(null);

    // Act
    boolean isValid = accountUnlockService.validatePasswordResetToken(token);

    // Assert
    assertFalse(isValid);
  }

  @Test
  public void testResetPassword_Success() {
    // Arrange
    String token = "reset-token";
    String newPassword = "new-password";
    member.setMailTokn(token);
    Mockito.when(memberDao.selectMemberByToken(token)).thenReturn(member);
    Mockito.doNothing().when(memberDao).updateMember(Mockito.any(MemberDto.class)); // void 메서드일 경우

    // Act
    accountUnlockService.resetPassword(token, newPassword);

    // Assert
    assertNull(member.getMailTokn());
    Mockito.verify(memberDao, Mockito.times(1)).updateMember(member);
    assertEquals(newPassword, member.getPswd());
  }

  @Test(expected = RuntimeException.class)
  public void testResetPassword_InvalidToken() {
    // Arrange
    String token = "invalid-token";
    String newPassword = "new-password";
    Mockito.when(memberDao.selectMemberByToken(token)).thenReturn(null);

    // Act
    accountUnlockService.resetPassword(token, newPassword);
  }
}
