package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.LoginHistoryDao;
import com.fastcampus.ch4.dto.member.LoginHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

  @Autowired
  private LoginHistoryDao loginHistoryDao;

  @Override
  public void saveLoginHistory(LoginHistoryDto loginHistory) {
    loginHistoryDao.insertLoginHistory(loginHistory);
  }

  @Override
  public List<LoginHistoryDto> getAllLoginHistories() {
    return loginHistoryDao.getAllLoginHistories();
  }

  @Override
  public void deleteAllLoginHistories() {
    loginHistoryDao.deleteAll();
  }
}
