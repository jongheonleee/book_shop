package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductStatusHistoryDto;

import java.util.List;
import java.util.Map;

public interface OrderProductStatusHistoryDao {
    int insertHistory(OrderProductStatusHistoryDto orderProductStatusHistoryDto);

    List<OrderProductStatusHistoryDto> selectHistoryByCondition(Map map);

    int updateHistory(OrderProductStatusHistoryDto orderProductStatusHistoryDto);

    int deleteHistoryByCondition(Map map);
}
