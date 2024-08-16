package com.fastcampus.ch4.dto.cart;

public interface PriceManipulatable {
    // 상품 정가 getter
    public Integer getPapr_pric();
    public Integer getE_pric();

    // 포인트 적립율 getter
    public Double getPapr_point();
    public Double getE_point();

    // 할인율 getter
    public Double getPapr_disc();
    public Double getE_disc();

}
