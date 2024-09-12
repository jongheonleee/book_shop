package com.fastcampus.ch4.dto.order;

import java.util.Date;

public class OrderDto {
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
    private String created_at; // 주문 생성 일자
    private String updated_at; // 주문 변경 일자

    // fk
    private String cust_id; // 유저 id

    // 시스템 컬럼
    private Date reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Date up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    public static OrderDto from(String userId, int totalProductPrice, int totalDiscountPrice, int totalOrderPrice, int deliveryFee) {
        OrderDto orderDto = new OrderDto();

        orderDto.setTotal_prod_pric(totalProductPrice);
        orderDto.setTotal_disc_pric(totalDiscountPrice);
        orderDto.setTotal_ord_pric(totalOrderPrice);
        orderDto.setDelivery_fee(deliveryFee);

        orderDto.setCust_id(userId);
        orderDto.setReg_id(userId);
        orderDto.setUp_id(userId);

        return orderDto;
    }

    // overload
    public static OrderDto from(String userId, int totalProductPrice, int totalDiscountPrice, int totalOrderPrice, int deliveryFee, Integer ord_stat_id, Integer deli_stat_id, Integer pay_stat_id) {
        OrderDto orderDto = OrderDto.from(userId, totalProductPrice, totalDiscountPrice, totalOrderPrice, deliveryFee);
        orderDto.setOrd_stat(ord_stat_id);
        orderDto.setDeli_stat(deli_stat_id);
        orderDto.setPay_stat(pay_stat_id);
        return orderDto;
    }


    @Override
    public String toString() {
        return "OrderDto{" +
                "ord_seq=" + ord_seq +
                ", ord_stat=" + ord_stat +
                ", deli_stat=" + deli_stat +
                ", pay_stat=" + pay_stat +
                ", delivery_fee=" + delivery_fee +
                ", total_prod_pric=" + total_prod_pric +
                ", total_disc_pric=" + total_disc_pric +
                ", total_ord_pric=" + total_ord_pric +
                ", crated_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", cust_id='" + cust_id + '\'' +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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
