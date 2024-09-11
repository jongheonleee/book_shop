package com.fastcampus.ch4.domain.order;

import java.util.Arrays;
import java.util.Objects;

public enum DeliveryStatus {
    INIT("deli-stat-01", "배송 대기"),
    DELIVERY_WAIT("deli-stat-01", "배송 대기"),
    DELIVERT_READY("deli-stat-02", "배송 준비중"),
    DELIVERY_DOING("deli-stat-03", "배송 중"),
    DELIVERY_DONE("deli-stat-04", "배송 완료");

    private final String code;
    private final String codeName;

    DeliveryStatus(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static DeliveryStatus of (String code) {
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
