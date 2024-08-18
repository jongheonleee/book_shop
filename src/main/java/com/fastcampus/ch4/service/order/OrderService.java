package com.fastcampus.ch4.service.order;

import com.fastcampus.ch4.dto.order.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderService {
    public OrderDto createOrder (String userId, List<Map<String, Object>> ordProdList) throws Exception;
}
