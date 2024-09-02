package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class InfoChangeHistoryServiceImplTest {

    @Autowired
    private InfoChangeHistoryServiceImpl infoChangeHistoryService;

    @Autowired
    private InfoChangeHistoryDao infoChangeHistoryDao;

    @Autowired
    private MemberDao memberDao;

    @Before
    public void setUp() {
        // 데이터베이스 초기화
        // 기존 데이터 삭제
        memberDao.deleteAll();
        infoChangeHistoryDao.deleteAllChangeHistories();

        // 테스트 데이터 준비
        MemberDto existingUser = new MemberDto();
        existingUser.setId("user123");
        existingUser.setPswd("oldPassword");
        existingUser.setPhnNumb("123456789");
        existingUser.setEmail("oldemail@example.com");

        memberDao.insertMember(existingUser); // 실제 구현에 따라 저장 메서드 사용
    }

    @Test
    public void testSaveChangeHistory() {
        // Arrange
        String changeType = "비밀번호 변경";
        String beforeChange = "oldPassword";
        String afterChange = "newPassword";
        MemberDto currentUser = new MemberDto();
        currentUser.setId("user123");

        // Act
        infoChangeHistoryService.saveChangeHistory(changeType, beforeChange, afterChange, currentUser);

        // Assert
        List<InfoChangeHistoryDto> historyList = infoChangeHistoryDao.getChangeHistoryById("user123");
        assertNotNull(historyList);
        assertFalse(historyList.isEmpty());

        InfoChangeHistoryDto changeLog = historyList.get(0);
        assertEquals("user123", changeLog.getId());
        assertEquals(changeType + " updated", changeLog.getChgInfo());
        assertEquals(beforeChange, changeLog.getBforChg());
        assertEquals(afterChange, changeLog.getAftrChg());
    }

    @Test
    public void testUpdateUserProfile() {
        // Arrange
        MemberDto updatedUser = new MemberDto();
        updatedUser.setId("user123");
        updatedUser.setPswd("newPassword");
        updatedUser.setPhnNumb("987654321");
        updatedUser.setEmail("newemail@example.com");

        // Act
        boolean isUpdated = infoChangeHistoryService.updateUserProfile("user123", updatedUser);

        // Assert
        assertTrue(isUpdated);

        // Validate that change history records are correctly updated
        List<InfoChangeHistoryDto> historyList = infoChangeHistoryDao.getChangeHistoryById("user123");
        assertNotNull(historyList);
        assertTrue(historyList.size() >= 3);

        InfoChangeHistoryDto passwordChangeLog = historyList.stream()
                .filter(log -> log.getChgInfo().contains("비밀번호 변경"))
                .findFirst().orElse(null);
        assertNotNull(passwordChangeLog);
        assertEquals("oldPassword", passwordChangeLog.getBforChg());
        assertEquals("newPassword", passwordChangeLog.getAftrChg());

        InfoChangeHistoryDto phoneChangeLog = historyList.stream()
                .filter(log -> log.getChgInfo().contains("전화번호 변경"))
                .findFirst().orElse(null);
        assertNotNull(phoneChangeLog);
        assertEquals("123456789", phoneChangeLog.getBforChg());
        assertEquals("987654321", phoneChangeLog.getAftrChg());

        InfoChangeHistoryDto emailChangeLog = historyList.stream()
                .filter(log -> log.getChgInfo().contains("이메일 변경"))
                .findFirst().orElse(null);
        assertNotNull(emailChangeLog);
        assertEquals("oldemail@example.com", emailChangeLog.getBforChg());
        assertEquals("newemail@example.com", emailChangeLog.getAftrChg());
    }
}
