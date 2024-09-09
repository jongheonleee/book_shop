package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderHistoryDto;

import java.util.List;
import java.util.Map;

public interface OrderHistoryDao {
    int insertOrderHistory(OrderHistoryDto dto);

    List<OrderHistoryDto> selectOrderHistoryByCondition(Map map);

    int updateOrderHistory(OrderHistoryDto dto);

    int deleteOrderHistoryByCondition(Map map);
}
