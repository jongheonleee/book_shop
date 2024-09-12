//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
//import com.fastcampus.ch4.dao.member.MemberDao;
//import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;
//import com.fastcampus.ch4.dto.member.MemberDto;
//import com.fastcampus.ch4.service.member.InfoChangeHistoryServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class InfoChangeHistoryServiceImplTest {
//
//    @Mock
//    private InfoChangeHistoryDao infoChangeHistoryDao;
//
//    @Mock
//    private MemberDao memberDao;
//
//    @InjectMocks
//    private InfoChangeHistoryServiceImpl infoChangeHistoryService;
//
//    private MemberDto existingUser;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//
//        // 기존 사용자 정보 초기화
//        existingUser = new MemberDto();
//        existingUser.setId("user123");
//        existingUser.setPswd("oldPassword");
//        existingUser.setPhnNumb("010-1234-5678");
//        existingUser.setEmail("old.email@example.com");
//
//        when(memberDao.selectMemberById("user123")).thenReturn(existingUser);
//    }
//
//    @Test
//    public void testUpdateUserProfile_PasswordChanged() {
//        MemberDto updatedUser = new MemberDto();
//        updatedUser.setPswd("newPassword");
//
//        boolean isUpdated = infoChangeHistoryService.updateUserProfile("user123", updatedUser);
//
//        assertTrue(isUpdated);
//        verify(memberDao).updateMember(any(MemberDto.class));
//        verify(infoChangeHistoryDao).saveChangeHistory(any(InfoChangeHistoryDto.class));
//    }
//
//    @Test
//    public void testUpdateUserProfile_EmailChanged() {
//        MemberDto updatedUser = new MemberDto();
//        updatedUser.setEmail("new.email@example.com");
//
//        boolean isUpdated = infoChangeHistoryService.updateUserProfile("user123", updatedUser);
//
//        assertTrue(isUpdated);
//        verify(memberDao).updateMember(any(MemberDto.class));
//        verify(infoChangeHistoryDao).saveChangeHistory(any(InfoChangeHistoryDto.class));
//    }
//
//    @Test
//    public void testUpdateUserProfile_NoChanges() {
//        MemberDto updatedUser = new MemberDto();
//
//        boolean isUpdated = infoChangeHistoryService.updateUserProfile("user123", updatedUser);
//
//        assertTrue(!isUpdated);
//        verify(memberDao, never()).updateMember(any(MemberDto.class));
//        verify(infoChangeHistoryDao, never()).saveChangeHistory(any(InfoChangeHistoryDto.class));
//    }
//
//    @Test
//    public void testUpdateUserProfile_MultipleChanges() {
//        MemberDto updatedUser = new MemberDto();
//        updatedUser.setPswd("newPassword");
//        updatedUser.setPhnNumb("010-8765-4321");
//        updatedUser.setEmail("new.email@example.com");
//
//        boolean isUpdated = infoChangeHistoryService.updateUserProfile("user123", updatedUser);
//
//        assertTrue(isUpdated);
//        verify(memberDao).updateMember(any(MemberDto.class));
//        verify(infoChangeHistoryDao, times(3)).saveChangeHistory(any(InfoChangeHistoryDto.class));
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void testUpdateUserProfile_UserNotFound() {
//        when(memberDao.selectMemberById("nonExistentUser")).thenReturn(null);
//
//        MemberDto updatedUser = new MemberDto();
//        updatedUser.setPswd("newPassword");
//
//        infoChangeHistoryService.updateUserProfile("nonExistentUser", updatedUser);
//    }
//}
