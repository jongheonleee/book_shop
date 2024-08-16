package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.InfoChangeHistoryDto;

import java.util.List;

public interface InfoChangeHistoryDao {
  void insertChangeHistory(InfoChangeHistoryDto infoChangeHistory);
  List<InfoChangeHistoryDto> getChangeHistoryById(String id);
  void saveChangeHistory(InfoChangeHistoryDto changeLog);
  void deleteAllChangeHistories();
}
