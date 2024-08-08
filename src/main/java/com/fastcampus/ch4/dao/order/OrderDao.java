package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;

import java.util.List;

public interface OrderDao {
    /**
     * 요구 기능
     * 1. 주문 생성하기
     * 2. 주문 조회하기
     * 3. 주문 삭제하기
     * 4. 주문 변경하기
     */

    public Integer createOrderAndReturnId(OrderDto orderDto) throws Exception;
    public OrderDto findOrderById(Integer ordSeq) throws Exception;
    public int deleteOrderById(Integer ordSeq) throws Exception;
    public List<OrderDto> searchOrder() throws Exception ;
}
