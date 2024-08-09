package com.fastcampus.ch4.model.order;

public enum OrderStatus {
    ORDER_START("order_start", "주문 시작"),
    ORDER_DONE("order_done", "주문 완료"),
    PAYMENT_DONE("payment_done", "결제 완료"),
    DELIVERY_WAIT("delivery_wait", "배송 준비중"),
    DELIVERY_DO("delivery_do", "배송 중"),
    DELIVERY_DONE("delivery_done", "배송 완료"),
    CANCEL_REQUEST("cancel_request", "취소 신청"),
    CANCEL_DONE("cancel_done", "취소 완료"),
    EXCHANGE_REQUEST("exchange_request", "교환 신청"),
    EXCHANGE_APPROVAL("exchange_approval", "교환 승인"),
    EXCHANGE_DONE("exchange_done", "교환 완료"),
    RETURN_REQUEST("return_request", "반품 신청"),
    RETURN_APPROVAL("return_approval", "반품 승인"),
    RETURN_DONE("return_done", "반품 완료");

    private final String code;
    private final String statusName;

    OrderStatus(String code, String statusName) {
        this.code = code;
        this.statusName = statusName;
    }

    public boolean isSameStatus(String orderStatus) {
        return orderStatus.equals(this.code);
    }

    public String getCode() {
        return code;
    }

    public String getStatusName() {
        return statusName;
    }


}
