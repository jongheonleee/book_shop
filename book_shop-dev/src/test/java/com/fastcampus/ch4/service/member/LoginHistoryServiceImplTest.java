package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.LoginHistoryDao;
import com.fastcampus.ch4.dto.member.LoginHistoryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginHistoryServiceImplTest {

  @Mock
  private LoginHistoryDao loginHistoryDao;

  @InjectMocks
  private LoginHistoryServiceImpl loginHistoryService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testSaveLoginHistory() {
    LoginHistoryDto loginHistory = new LoginHistoryDto();

    // `doNothing()`은 사용하지 않음 - `insertLoginHistory`는 `void` 메서드이므로
    // 단순히 호출 확인만 하면 됨

    loginHistoryService.saveLoginHistory(loginHistory);

    // `insertLoginHistory` 메서드가 정확히 한 번 호출되었는지 검증
    verify(loginHistoryDao, times(1)).insertLoginHistory(loginHistory);
  }

  @Test
  public void testGetAllLoginHistories() {
    List<LoginHistoryDto> mockLoginHistories = new ArrayList<>();
    mockLoginHistories.add(new LoginHistoryDto());
    when(loginHistoryDao.getAllLoginHistories()).thenReturn(mockLoginHistories);

    List<LoginHistoryDto> result = loginHistoryService.getAllLoginHistories();

    // `getAllLoginHistories` 메서드가 정확히 한 번 호출되었는지 검증
    verify(loginHistoryDao, times(1)).getAllLoginHistories();
    // 결과가 예상 값과 일치하는지 검증
    assertEquals(mockLoginHistories, result);
  }

  @Test
  public void testDeleteAllLoginHistories() {
    // `deleteAll` 메서드는 `void`이므로 `doNothing()`은 필요 없음

    loginHistoryService.deleteAllLoginHistories();

    // `deleteAll` 메서드가 정확히 한 번 호출되었는지 검증
    verify(loginHistoryDao, times(1)).deleteAll();
  }
}
