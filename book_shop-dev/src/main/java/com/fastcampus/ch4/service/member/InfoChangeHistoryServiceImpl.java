package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 사용자 정보를 업데이트
    boolean isUpdated = memberDao.updateMember(updatedUser);

    if (isUpdated) {
      // 비밀번호 변경 이력 기록
      if (updatedUser.getPswd() != null && !updatedUser.getPswd().equals(currentUser.getPswd())) {
        InfoChangeHistoryDto pswdChangeLog = createChangeHistory("비밀번호 변경", currentUser.getPswd(), updatedUser.getPswd(), currentUser);
        infoChangeHistoryDao.saveChangeHistory(pswdChangeLog);
      }

      // 전화번호 변경 이력 기록
      if (updatedUser.getPhnNumb() != null && !updatedUser.getPhnNumb().equals(currentUser.getPhnNumb())) {
        InfoChangeHistoryDto phnNumbChangeLog = createChangeHistory("전화번호 변경", currentUser.getPhnNumb(), updatedUser.getPhnNumb(), currentUser);
        infoChangeHistoryDao.saveChangeHistory(phnNumbChangeLog);
      }

      // 이메일 변경 이력 기록
      if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(currentUser.getEmail())) {
        InfoChangeHistoryDto emailChangeLog = createChangeHistory("이메일 변경", currentUser.getEmail(), updatedUser.getEmail(), currentUser);
        infoChangeHistoryDao.saveChangeHistory(emailChangeLog);
      }
    }

    return isUpdated;
  }


  private InfoChangeHistoryDto createChangeHistory(String changeType, String beforeChange, String afterChange, MemberDto currentUser) {
    InfoChangeHistoryDto changeLog = new InfoChangeHistoryDto();
    changeLog.setId(currentUser.getId());
    changeLog.setChgTime(new Date());
    changeLog.setChgInfo(changeType + " updated");
    changeLog.setBforChg(beforeChange);
    changeLog.setAftrChg(afterChange);
    changeLog.setRegDate(new Date());
    changeLog.setRegId(currentUser.getId());
    changeLog.setUpDate(new Date());
    changeLog.setUpId(currentUser.getId());

    return changeLog;
  }

  public void deleteAllData() {
    infoChangeHistoryDao.deleteAllChangeHistories();
  }

}
