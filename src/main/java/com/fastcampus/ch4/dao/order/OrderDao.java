package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;

import java.util.List;

public interface OrderDao {
    /**
     * 요구 기능
     * 1. 주문 생성하기
     * 2. 주문 조회하기
     */

    public int createOrder(OrderDto orderDto);
    public List<OrderDto> searchOrder();
}
