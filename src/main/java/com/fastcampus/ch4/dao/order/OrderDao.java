package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    int insertOrder(OrderDto orderDto);
    List<OrderDto> selectOrderListByCondition(Map map);
    int updateOrderStatus (Map map);
    int updateOrderPriceInfo(Map map);
    int deleteOrderByCondition(Map map);
}
