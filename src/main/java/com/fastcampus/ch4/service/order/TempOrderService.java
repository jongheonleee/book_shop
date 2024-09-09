package com.fastcampus.ch4.service.order;

import java.util.List;
import java.util.Map;

public interface TempOrderService {
    public TempOrderDto createOrder (String userId, List<Map<String, Object>> ordProdList) throws Exception;
}
