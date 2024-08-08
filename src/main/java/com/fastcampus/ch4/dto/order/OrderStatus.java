package com.fastcampus.ch4.dto.order;

public enum OrderStatus {
    ORDER_DONE("order_done", "주문 완료"),
    PAYMENT_DONE("payment_done", "결제 완료"),
    DELIVERY_READY("delivery_ready", "준비 중"),
    DELIVERY_END("delivery_end", "배송 완료"),
    CANCLE_REQUEST("cancle_request", "취소 신청"),
    CANCLE_END("cancle_end", "취소 완료"),
    EXCHANGE_REQUEST("exchange_request", "교환 신청"),
    EXCHNAGE_APPROVAL("exchange_approval", "교환 승인"),
    EXCHANGE_END("exchange_end", "교환 완료"),
    RETURN_REQUEST("return_request", "반품 신청"),
    RETURN_APPROVAL("return_approval", "반품 승인"),
    RETURN_END("return_end", "반품 완료");

    private final String ord_stat;
    private final String statusName;

    OrderStatus(String ord_stat, String statusName) {
        this.ord_stat = ord_stat;
        this.statusName = statusName;
    }

    public String getOrd_stat() {
        return ord_stat;
    }

    public boolean isSameStatus(String orderStatus) {
        return orderStatus.equals(this.ord_stat);
    }
}
