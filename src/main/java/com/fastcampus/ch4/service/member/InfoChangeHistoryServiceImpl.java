package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class InfoChangeHistoryServiceImpl implements InfoChangeHistoryService {

  @Autowired
  private InfoChangeHistoryDao infoChangeHistoryDao;

  @Autowired
  private MemberDao memberDao;

  @Override
  public void saveChangeHistory(String changeType, String beforeChange, String afterChange, MemberDto currentUser) {
    InfoChangeHistoryDto changeLog = createChangeHistory(changeType, beforeChange, afterChange, currentUser);
    infoChangeHistoryDao.saveChangeHistory(changeLog);
  }

  @Override
  public List<InfoChangeHistoryDto> getChangeHistoryById(String id) {
    return infoChangeHistoryDao.getChangeHistoryById(id);
  }

  @Override
  public boolean updateUserProfile(String userId, MemberDto updatedUser) {
    // 현재 사용자 정보를 가져옴
    MemberDto currentUser = memberDao.selectMemberById(userId);
    if (currentUser == null) {
      throw new IllegalStateException("현재 사용자를 찾을 수 없습니다.");
    }

    // 필요한 필드만 업데이트
    boolean isUpdated = false;

    if (updatedUser.getPswd() != null && !updatedUser.getPswd().equals(currentUser.getPswd())) {
      currentUser.setPswd(updatedUser.getPswd());
      InfoChangeHistoryDto pswdChangeLog = createChangeHistory("비밀번호 변경", currentUser.getPswd(), updatedUser.getPswd(), currentUser);
      infoChangeHistoryDao.saveChangeHistory(pswdChangeLog);
      isUpdated = true;
    }

    if (updatedUser.getPhnNumb() != null && !updatedUser.getPhnNumb().equals(currentUser.getPhnNumb())) {
      currentUser.setPhnNumb(updatedUser.getPhnNumb());
      InfoChangeHistoryDto phnNumbChangeLog = createChangeHistory("전화번호 변경", currentUser.getPhnNumb(), updatedUser.getPhnNumb(), currentUser);
      infoChangeHistoryDao.saveChangeHistory(phnNumbChangeLog);
      isUpdated = true;
    }

    if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(currentUser.getEmail())) {
      currentUser.setEmail(updatedUser.getEmail());
      InfoChangeHistoryDto emailChangeLog = createChangeHistory("이메일 변경", currentUser.getEmail(), updatedUser.getEmail(), currentUser);
      infoChangeHistoryDao.saveChangeHistory(emailChangeLog);
      isUpdated = true;
    }

    // 업데이트가 필요한 경우에만 DB에 반영
    if (isUpdated) {
      memberDao.updateMember(currentUser);
    }

    return isUpdated;
  }

  private InfoChangeHistoryDto createChangeHistory(String changeType, String beforeChange, String afterChange, MemberDto currentUser) {
    InfoChangeHistoryDto changeLog = new InfoChangeHistoryDto();
    changeLog.setId(currentUser.getId());
    changeLog.setChgTime(LocalDate.now());
    changeLog.setChgInfo(changeType + " updated");
    changeLog.setBforChg(beforeChange);
    changeLog.setAftrChg(afterChange);
    changeLog.setRegDate(LocalDate.now());
    changeLog.setRegId(currentUser.getId());
    changeLog.setUpDate(LocalDate.now());
    changeLog.setUpId(currentUser.getId());

    return changeLog;
  }

  public void deleteAllData() {
    infoChangeHistoryDao.deleteAllChangeHistories();
  }
}

