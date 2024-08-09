package com.fastcampus.ch4.model.order;

public enum PaymentStatus {
    PAYMENT_WAIT("payment_wait", "결제대기"),
    PAYMENT_DONE("payment_done", "결제완료"),
    PAYMENT_CANCEL("payment_cancel", "결제 취소");

    private final String code;
    private final String statusName;

    PaymentStatus(String code, String statusName) {
        this.code = code;
        this.statusName = statusName;
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
