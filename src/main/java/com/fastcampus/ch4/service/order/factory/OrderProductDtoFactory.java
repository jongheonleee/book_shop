package com.fastcampus.ch4.service.order.factory;

import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.model.order.DeliveryStatus;
import com.fastcampus.ch4.model.order.OrderConstants;
import com.fastcampus.ch4.model.order.OrderStatus;
import com.fastcampus.ch4.model.order.PaymentStatus;

public class OrderProductDtoFactory {
    public OrderProductDto create(Integer ord_num, String isbn, String prod_type_code, String userId) {
        OrderProductDto orderProductDto = new OrderProductDto();

        // NOT NULL
        orderProductDto.setOrd_num(ord_num);
        orderProductDto.setIsbn(isbn);
        orderProductDto.setProd_type_code(prod_type_code);
        orderProductDto.setOrd_stat(OrderConstants.BASIC_ORDER_STATUS.getCode());
        orderProductDto.setDeli_stat(OrderConstants.BASIC_DELIVERY_STATUS.getCode());
        orderProductDto.setPay_stat(OrderConstants.BASIC_PAYMENT_STATUS.getCode());

        // 시스템 컬럼
        orderProductDto.setRegId(userId);
        orderProductDto.setUp_id(userId);

        return orderProductDto;
    }
}
