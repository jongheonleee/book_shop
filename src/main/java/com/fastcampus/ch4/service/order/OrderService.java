package com.fastcampus.ch4.service.order;

import com.fastcampus.ch4.domain.order.OrderSearchCondition;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.request.OrderCreateDto;
import com.fastcampus.ch4.dto.order.response.OrderCountDto;
import com.fastcampus.ch4.dto.order.response.OrderViewDto;
import com.fastcampus.ch4.dto.order.response.ResponseOrderDto;

import java.util.List;

public interface OrderService {
    OrderViewDto create(OrderCreateDto orderCreateDto);
    OrderViewDto findOrder(Integer ord_seq);
    List<OrderViewDto> searchOrderPage(OrderSearchCondition orderSearchCondition);
    OrderViewDto changeOrderStatus(Integer ord_seq, Integer ord_stat_id, String user_id);
    OrderCountDto getOrderCountByStatus(String user_id);
    OrderViewDto getOrderData(OrderCreateDto orderCreateDto);
}
