package com.fastcampus.ch4.dto.order.request;

import java.util.List;

public class OrderCreateDto {
    private Integer delivery_fee;
    private String cust_id;
    private List<OrderItemDto> orderItemDtoList;

    public static OrderCreateDto from(Integer delivery_fee, String cust_id, List<OrderItemDto> orderItemDtoList) {
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setDelivery_fee(delivery_fee);
        orderCreateDto.setCust_id(cust_id);
        orderCreateDto.setOrderItemDtoList(orderItemDtoList);
        return orderCreateDto;
    }

    public Integer getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Integer delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public List<OrderItemDto> getOrderItemDtoList() {
        return orderItemDtoList;
    }

    public void setOrderItemDtoList(List<OrderItemDto> orderItemDtoList) {
        this.orderItemDtoList = orderItemDtoList;
    }
}
