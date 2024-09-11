package com.fastcampus.ch4.domain.order;

import java.util.Arrays;
import java.util.Objects;

public enum OrderStatus {
    INIT("ord-stat-01", "주문 대기"),
    ORDER_WAIT("ord-stat-01", "주문 대기"),
    ORDER_DONE("ord-stat-02", "주문 완료"),
    ORDER_CANCLE("ord-stat-03", "주문 취소");

    private final String code;
    private final String codeName;

    OrderStatus(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static OrderStatus of (String code) {
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
