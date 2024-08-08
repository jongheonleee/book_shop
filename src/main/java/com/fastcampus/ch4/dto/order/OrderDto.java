package com.fastcampus.ch4.dto.order;

import java.util.Date;

public class OrderDto {
    // pk
    // auto increment
    private Integer ord_seq; // 주문 번호

    // fk
    private String userId; // 유저 id

    // 속성
    private String ord_stat; // 주문 상태
    private Integer delivery_fee = 0; // 배송비
    private Integer total_prod_pric = 0; // 총 상품 금액
    private Integer total_bene_pric = 0; // 총 할인 금액
    private Integer total_ord_pric = 0; // 총 주문 금액

    // 시스템 컬럼
    private Date reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Date up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    public OrderDto(String id) {
        this.userId = id;
    }

    public Integer getOrd_seq() {
        return ord_seq;
    }

    public void setOrd_seq(Integer ord_seq) {
        this.ord_seq = ord_seq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrd_stat() {
        return ord_stat;
    }

    public void setOrd_stat(String ord_stat) {
        this.ord_stat = ord_stat;
    }

    public Integer getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Integer delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public Integer getTotal_prod_pric() {
        return total_prod_pric;
    }

    public void setTotal_prod_pric(Integer total_prod_pric) {
        this.total_prod_pric = total_prod_pric;
    }

    public Integer getTotal_bene_pric() {
        return total_bene_pric;
    }

    public void setTotal_bene_pric(Integer total_bene_pric) {
        this.total_bene_pric = total_bene_pric;
    }

    public Integer getTotal_ord_pric() {
        return total_ord_pric;
    }

    public void setTotal_ord_pric(Integer total_ord_pric) {
        this.total_ord_pric = total_ord_pric;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }
}
