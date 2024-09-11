package com.fastcampus.ch4.dto.order.response;

import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;

import java.util.List;

public class OrderViewDto {
    OrderDto orderDto;
    List<OrderProductDto> orderProductDtoList;

    public static OrderViewDto from(OrderDto orderDto, List<OrderProductDto> orderProductDtoList) {
        OrderViewDto orderViewDto = new OrderViewDto();
        orderViewDto.setOrderDto(orderDto);
        orderViewDto.setOrderProductDtoList(orderProductDtoList);
        return orderViewDto;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<OrderProductDto> getOrderProductDtoList() {
        return orderProductDtoList;
    }

    public void setOrderProductDtoList(List<OrderProductDto> orderProductDtoList) {
        this.orderProductDtoList = orderProductDtoList;
    }
}
