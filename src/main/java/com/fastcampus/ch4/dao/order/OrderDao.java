package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.domain.order.OrderCountCondition;
import com.fastcampus.ch4.domain.order.OrderSearchCondition;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.request.OrderStatusUpdateDto;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    int insertOrder(OrderDto orderDto);
    List<OrderDto> selectOrderByCondition(Map map);
    List<OrderDto> selectOrderPage(OrderSearchCondition orderSearchCondition);
    int selectOrderCount(OrderCountCondition condition);
    int updateStatus(OrderStatusUpdateDto orderStatusUpdateDto);
    int updateOrderPriceInfo(Map map);
    int deleteOrderByCondition(Map map);
}
