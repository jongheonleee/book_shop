package com.fastcampus.ch4.dto.order.response;

public class OrderCountDto {
    private int totalCount; // 전체 개수
    private int orderWaitCount; // 주문 중
    private int deliveryDoingCount; // 배송 중
    private int deliveryDoneCount; // 배송 완료

    public static OrderCountDto from (int totalCount, int orderWaitCount, int deliveryDoingCount, int deliveryDoneCount) {
        OrderCountDto dto = new OrderCountDto();
        dto.setTotalCount(totalCount);
        dto.setOrderWaitCount(orderWaitCount);
        dto.setDeliveryDoingCount(deliveryDoingCount);
        dto.setDeliveryDoneCount(deliveryDoneCount);
        return dto;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOrderWaitCount() {
        return orderWaitCount;
    }

    public void setOrderWaitCount(int orderWaitCount) {
        this.orderWaitCount = orderWaitCount;
    }

    public int getDeliveryDoingCount() {
        return deliveryDoingCount;
    }

    public void setDeliveryDoingCount(int deliveryDoingCount) {
        this.deliveryDoingCount = deliveryDoingCount;
    }

    public int getDeliveryDoneCount() {
        return deliveryDoneCount;
    }

    public void setDeliveryDoneCount(int deliveryDoneCount) {
        this.deliveryDoneCount = deliveryDoneCount;
    }
}
