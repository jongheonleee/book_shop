package com.fastcampus.ch4.dto.order;

import java.sql.Timestamp;
import java.util.Objects;

public class OrderHistoryDto {
    // pk (auto increment)
    private Integer ord_hist_seq; // 주문 변경 이력 번호
    private Integer ord_seq; // 주문 번호

    // NOTNULL
    private String chg_start_date; // 변경 시작 일자
    private String chg_end_date; // 변경 종료 일자
    private String chg_reason; // 변경 사유

    // 속성
    private Integer ord_stat; // 주문 상태
    private Integer deli_stat;
    private Integer pay_stat;
    private Integer delivery_fee = 0; // 배송비
    private Integer total_prod_pric = 0; // 총 상품 금액
    private Integer total_disc_pric = 0; // 총 할인 금액
    private Integer total_ord_pric = 0; // 총 주문 금액
    private String created_at; // 주문 생성 일자
    private String updated_at; // 주문 변경 일자

    // 시스템 컬럼
    private Timestamp reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Timestamp up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    public static OrderHistoryDto from (String startTime, String endTime, OrderDto orderDto, String changeReason) {
        OrderHistoryDto historyDto = new OrderHistoryDto();
        historyDto.setOrd_seq(orderDto.getOrd_seq());
        historyDto.setChg_start_date(startTime);
        historyDto.setChg_end_date(endTime);
        historyDto.setChg_reason(changeReason);
        historyDto.setDelivery_fee(orderDto.getDelivery_fee());
        historyDto.setTotal_prod_pric(orderDto.getTotal_prod_pric());
        historyDto.setTotal_disc_pric(orderDto.getTotal_disc_pric());
        historyDto.setTotal_ord_pric(orderDto.getTotal_ord_pric());
        historyDto.setReg_id(orderDto.getCust_id());
        historyDto.setUp_id(orderDto.getCust_id());
        return historyDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHistoryDto)) return false;
        OrderHistoryDto that = (OrderHistoryDto) o;
        return Objects.equals(ord_hist_seq, that.ord_hist_seq) && Objects.equals(ord_seq, that.ord_seq) && Objects.equals(chg_start_date, that.chg_start_date) && Objects.equals(chg_end_date, that.chg_end_date) && Objects.equals(chg_reason, that.chg_reason) && Objects.equals(ord_stat, that.ord_stat) && Objects.equals(deli_stat, that.deli_stat) && Objects.equals(pay_stat, that.pay_stat) && Objects.equals(delivery_fee, that.delivery_fee) && Objects.equals(total_prod_pric, that.total_prod_pric) && Objects.equals(total_disc_pric, that.total_disc_pric) && Objects.equals(total_ord_pric, that.total_ord_pric);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord_hist_seq, ord_seq, chg_start_date, chg_end_date, chg_reason, ord_stat, deli_stat, pay_stat, delivery_fee, total_prod_pric, total_disc_pric, total_ord_pric);
    }

    @Override
    public String toString() {
        return "OrderHistoryDto{" +
                "ord_hist_seq=" + ord_hist_seq +
                ", ord_seq=" + ord_seq +
                ", chg_start_date='" + chg_start_date + '\'' +
                ", chg_end_date='" + chg_end_date + '\'' +
                ", chg_reason='" + chg_reason + '\'' +
                ", ord_stat=" + ord_stat +
                ", deli_stat=" + deli_stat +
                ", pay_stat=" + pay_stat +
                ", delivery_fee=" + delivery_fee +
                ", total_prod_pric=" + total_prod_pric +
                ", total_disc_pric=" + total_disc_pric +
                ", total_ord_pric=" + total_ord_pric +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }

    public Integer getOrd_hist_seq() {
        return ord_hist_seq;
    }

    public void setOrd_hist_seq(Integer ord_hist_seq) {
        this.ord_hist_seq = ord_hist_seq;
    }

    public Integer getOrd_seq() {
        return ord_seq;
    }

    public void setOrd_seq(Integer ord_seq) {
        this.ord_seq = ord_seq;
    }

    public String getChg_start_date() {
        return chg_start_date;
    }

    public void setChg_start_date(String chg_start_date) {
        this.chg_start_date = chg_start_date;
    }

    public String getChg_end_date() {
        return chg_end_date;
    }

    public void setChg_end_date(String chg_end_date) {
        this.chg_end_date = chg_end_date;
    }

    public String getChg_reason() {
        return chg_reason;
    }

    public void setChg_reason(String chg_reason) {
        this.chg_reason = chg_reason;
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

    public Timestamp getReg_date() {
        return reg_date;
    }

    public void setReg_date(Timestamp reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public Timestamp getUp_date() {
        return up_date;
    }

    public void setUp_date(Timestamp up_date) {
        this.up_date = up_date;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }
}
