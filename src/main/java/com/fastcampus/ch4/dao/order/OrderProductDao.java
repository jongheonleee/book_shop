package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductDto;

import java.util.List;
import java.util.Map;

public interface OrderProductDao {
    int insertOrderProduct(OrderProductDto orderProductDto);

    List<OrderProductDto> selectOrderProductByCondition(Map map);

    int updateOrderProduct(OrderProductDto orderProductDto);

    int deleteOrderProductByCondition(Map map);
}
