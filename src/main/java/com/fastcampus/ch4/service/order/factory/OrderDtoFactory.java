package com.fastcampus.ch4.service.order.factory;

import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.model.order.DeliveryStatus;
import com.fastcampus.ch4.model.order.OrderStatus;
import com.fastcampus.ch4.model.order.PaymentStatus;
import org.springframework.stereotype.Component;

import static com.fastcampus.ch4.dto.order.OrderDto.*;
import static com.fastcampus.ch4.model.order.OrderStatus.*;

@Component
public class OrderDtoFactory {
    final int BASIC_PRICE = 0;

    /*
    1. 상태를 가지지 않기 때문에 static 으로 생성한다.

    instance method 로 사용할 때 좋은 점
    1. Spring Bean 으로 등록해서 관리할 수 있다.
    2. @Override 가능
     */

    public OrderDto create(String userId) {
        return create(userId, BASIC_ORDER_STATUS, BASIC_DELIVERY_STATUS, BASIC_PAYMENT_STATUS);
    }

    public OrderDto create(String userId, OrderStatus orderStatus, DeliveryStatus deliveryStatus, PaymentStatus paymentStatus) {
        OrderDto orderDto = new OrderDto();

        // NOT NULL
        orderDto.setUserId(userId);
        orderDto.setOrd_stat(orderStatus.getCode());
        orderDto.setDeli_stat(deliveryStatus.getCode());
        orderDto.setPay_stat(paymentStatus.getCode());

        // NULL
        orderDto.setTotal_prod_pric(BASIC_PRICE);
        orderDto.setTotal_bene_pric(BASIC_PRICE);
        orderDto.setDelivery_fee(BASIC_PRICE);
        orderDto.setTotal_ord_pric(BASIC_PRICE);

        // SYSTEM COLUMN
        orderDto.setReg_id(userId);
        orderDto.setUp_id(userId);

        return orderDto;
    }

    public int getBASIC_PRICE() {
        return BASIC_PRICE;
    }

    public OrderStatus getBASIC_ORDER_STATUS() {
        return BASIC_ORDER_STATUS;
    }

    public DeliveryStatus getBASIC_DELIVERY_STATUS() {
        return BASIC_DELIVERY_STATUS;
    }

    public PaymentStatus getBASIC_PAYMENT_STATUS() {
        return BASIC_PAYMENT_STATUS;
    }
}
