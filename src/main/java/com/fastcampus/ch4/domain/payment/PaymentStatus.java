package com.fastcampus.ch4.domain.payment;

import com.fastcampus.ch4.domain.order.OrderStatus;

import java.util.Arrays;
import java.util.Objects;

public enum PaymentStatus {
    INIT("pay-stat-01", "결제 대기"),
    PAYMENT_WAIT("pay-stat-01", "결제 대기"),
    PAYMENT_FAIL("pay-stat-02", "결제 실패"),
    PAYMENT_DONE("pay-stat-03", "결제 완료"),
    PAYMENT_CANCLE("pay-stat-04", "결제 취소");

    private final String code;
    private final String codeName;

    PaymentStatus(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static PaymentStatus of (String code) {
        return Arrays.stream(values())
                .filter(val -> Objects.equals(code, val.code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
