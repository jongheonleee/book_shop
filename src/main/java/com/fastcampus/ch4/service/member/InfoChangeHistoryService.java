package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;
import com.fastcampus.ch4.dto.member.MemberDto;

import java.util.List;

public interface InfoChangeHistoryService {
  public void saveChangeHistory(String changeType, String beforeChange, String afterChange, MemberDto currentUser);
  List<InfoChangeHistoryDto> getChangeHistoryById(String id);
  public boolean updateUserProfile(String userId, MemberDto updatedUser);
    void deleteAllData();
}
