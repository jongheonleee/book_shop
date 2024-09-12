package com.fastcampus.ch4.dto.order.response;

import com.fastcampus.ch4.dto.order.OrderProductDto;

import java.util.List;
import java.util.Objects;

public class ResponseOrderDto {
    // pk (auto increment)
    private Integer ord_seq; // 주문 번호

    // NOTNULL
    private Integer ord_stat; // 주문 상태
    private Integer deli_stat; // 배송 상태
    private Integer pay_stat; // 결제 상태

    // 공통 코드 속성
    private String ord_stat_code; // 주문 상태 코드
    private String ord_stat_name; // 주문 상태 코드 이름
    private String deli_stat_code; // 배송 상태 코드
    private String deli_stat_name; // 배송 상태 코드 이름
    private String pay_stat_code; // 결제 상태 코드
    private String pay_stat_name; // 결제 상태 코드 이름

    // 속성
    private Integer delivery_fee = 0; // 배송비
    private Integer total_prod_pric = 0; // 총 상품 금액
    private Integer total_disc_pric = 0; // 총 할인 금액
    private Integer total_ord_pric = 0; // 총 주문 금액
    private String crated_at; // 주문 생성 일자
    private String updated_at; // 주문 변경 일자

    // fk
    private String cust_id; // 유저 id

    // OrderProductDto List
    private List<OrderProductDto> orderProductDtoList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseOrderDto)) return false;
        ResponseOrderDto that = (ResponseOrderDto) o;
        return Objects.equals(ord_seq, that.ord_seq) && Objects.equals(ord_stat, that.ord_stat) && Objects.equals(deli_stat, that.deli_stat) && Objects.equals(pay_stat, that.pay_stat) && Objects.equals(ord_stat_code, that.ord_stat_code) && Objects.equals(ord_stat_name, that.ord_stat_name) && Objects.equals(deli_stat_code, that.deli_stat_code) && Objects.equals(deli_stat_name, that.deli_stat_name) && Objects.equals(pay_stat_code, that.pay_stat_code) && Objects.equals(pay_stat_name, that.pay_stat_name) && Objects.equals(delivery_fee, that.delivery_fee) && Objects.equals(total_prod_pric, that.total_prod_pric) && Objects.equals(total_disc_pric, that.total_disc_pric) && Objects.equals(total_ord_pric, that.total_ord_pric) && Objects.equals(orderProductDtoList, that.orderProductDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord_seq, ord_stat, deli_stat, pay_stat, ord_stat_code, ord_stat_name, deli_stat_code, deli_stat_name, pay_stat_code, pay_stat_name, delivery_fee, total_prod_pric, total_disc_pric, total_ord_pric, orderProductDtoList);
    }

    public Integer getOrd_seq() {
        return ord_seq;
    }

    public void setOrd_seq(Integer ord_seq) {
        this.ord_seq = ord_seq;
    }

    public Integer getOrd_stat() {
        return ord_stat;
    }

    public void setOrd_stat(Integer ord_stat) {
        this.ord_stat = ord_stat;
    }

    public Integer getDeli_stat() {
        return deli_stat;
    }

    public void setDeli_stat(Integer deli_stat) {
        this.deli_stat = deli_stat;
    }

    public Integer getPay_stat() {
        return pay_stat;
    }

    public void setPay_stat(Integer pay_stat) {
        this.pay_stat = pay_stat;
    }

    public String getOrd_stat_code() {
        return ord_stat_code;
    }

    public void setOrd_stat_code(String ord_stat_code) {
        this.ord_stat_code = ord_stat_code;
    }

    public String getOrd_stat_name() {
        return ord_stat_name;
    }

    public void setOrd_stat_name(String ord_stat_name) {
        this.ord_stat_name = ord_stat_name;
    }

    public String getDeli_stat_code() {
        return deli_stat_code;
    }

    public void setDeli_stat_code(String deli_stat_code) {
        this.deli_stat_code = deli_stat_code;
    }

    public String getDeli_stat_name() {
        return deli_stat_name;
    }

    public void setDeli_stat_name(String deli_stat_name) {
        this.deli_stat_name = deli_stat_name;
    }

    public String getPay_stat_code() {
        return pay_stat_code;
    }

    public void setPay_stat_code(String pay_stat_code) {
        this.pay_stat_code = pay_stat_code;
    }

    public String getPay_stat_name() {
        return pay_stat_name;
    }

    public void setPay_stat_name(String pay_stat_name) {
        this.pay_stat_name = pay_stat_name;
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

    public Integer getTotal_disc_pric() {
        return total_disc_pric;
    }

    public void setTotal_disc_pric(Integer total_disc_pric) {
        this.total_disc_pric = total_disc_pric;
    }

    public Integer getTotal_ord_pric() {
        return total_ord_pric;
    }

    public void setTotal_ord_pric(Integer total_ord_pric) {
        this.total_ord_pric = total_ord_pric;
    }

    public String getCrated_at() {
        return crated_at;
    }

    public void setCrated_at(String crated_at) {
        this.crated_at = crated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public List<OrderProductDto> getOrderProductDtoList() {
        return orderProductDtoList;
    }

    public void setOrderProductDtoList(List<OrderProductDto> orderProductDtoList) {
        this.orderProductDtoList = orderProductDtoList;
    }
}
