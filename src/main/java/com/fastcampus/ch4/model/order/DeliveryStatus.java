package com.fastcampus.ch4.model.order;

public enum DeliveryStatus {
    DELIVERY_WAIT("delivery_wait", "배송 준비중"),
    DELIVERY_DO("delivery_do", "배송중"),
    DELIVERY_DONE("delivery_done", "배송 완료"),
    DELIVERY_CANCLE("delivery_cancle", "배송 취소");

    private final String code;
    private final String statusName;

    DeliveryStatus(String code, String statusName) {
        this.statusName = statusName;
        this.code = code;
    }

    public boolean isSameStatus(String statusCode) {
        return statusCode.equals(this.code);
    }

    public String getCode() {
        return code;
    }

    public String getStatusName() {
        return statusName;
    }
}
