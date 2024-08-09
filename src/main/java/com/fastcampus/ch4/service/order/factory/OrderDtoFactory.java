package com.fastcampus.ch4.service.order.factory;

import com.fastcampus.ch4.dto.order.OrderDto;

import static com.fastcampus.ch4.dto.order.OrderStatus.ORDER_DONE;

public class OrderDtoFactory {
    private static final int BASIC_PRICE = 0;
    private static final String BASIC_ORDER_STATUS = ORDER_DONE.getOrd_stat();

    /*
    1. 상태를 가지지 않기 때문에 static 으로 생성한다.

    instance method 로 사용할 때 좋은 점
    1. Spring Bean 으로 등록해서 관리할 수 있다.
    2. @Override 가능
     */
    public static OrderDto getInstance(String userId) {
        OrderDto orderDto = new OrderDto();

        // NOT NULL
        orderDto.setUserId(userId);
        orderDto.setOrd_stat(BASIC_ORDER_STATUS);

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
}
