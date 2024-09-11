package com.fastcampus.ch4.dto.order;

import java.util.Date;
import java.util.Objects;

public class OrderProductStatusHistoryDto {
    // PK
    private Integer ord_prod_stat_hist_seq; // 주문 상품 상태 번호
    private Integer ord_prod_seq; // 주문 상품 번호

    // NOT NULL
    private String chg_start_date; // 변경 시작 일자
    private String chg_end_date; // 변경 종료 일자

    // NULL
    private Integer ord_stat; // 주문 상태
    private Integer deli_stat; // 배송 상태
    private Integer pay_stat; // 결제 상태

    // 시스템 컬럼  - equals 제외
    private Date reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Date up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    public static OrderProductStatusHistoryDto from(String startTime, String endTime, OrderProductDto orderProductDto) {
        OrderProductStatusHistoryDto orderProductStatusHistoryDto = new OrderProductStatusHistoryDto();
        orderProductStatusHistoryDto.setOrd_prod_seq(orderProductDto.getOrd_prod_seq());
        orderProductStatusHistoryDto.setOrd_prod_seq(orderProductDto.getOrd_prod_seq());
        orderProductStatusHistoryDto.setChg_start_date(startTime);
        orderProductStatusHistoryDto.setChg_end_date(endTime);
        orderProductStatusHistoryDto.setOrd_stat(orderProductDto.getOrd_stat());
        orderProductStatusHistoryDto.setDeli_stat(orderProductDto.getDeli_stat());
        orderProductStatusHistoryDto.setPay_stat(orderProductDto.getPay_stat());
        orderProductStatusHistoryDto.setReg_id(orderProductDto.getReg_id());
        orderProductStatusHistoryDto.setUp_id(orderProductDto.getUp_id());
        return orderProductStatusHistoryDto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductStatusHistoryDto)) return false;
        OrderProductStatusHistoryDto that = (OrderProductStatusHistoryDto) o;
        return Objects.equals(ord_prod_stat_hist_seq, that.ord_prod_stat_hist_seq) && Objects.equals(ord_prod_seq, that.ord_prod_seq) && Objects.equals(chg_start_date, that.chg_start_date) && Objects.equals(chg_end_date, that.chg_end_date) && Objects.equals(ord_stat, that.ord_stat) && Objects.equals(deli_stat, that.deli_stat) && Objects.equals(pay_stat, that.pay_stat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord_prod_stat_hist_seq, ord_prod_seq, chg_start_date, chg_end_date, ord_stat, deli_stat, pay_stat);
    }

    @Override
    public String toString() {
        return "OrderProductStatusHistoryDto{" +
                "ord_prod_stat_hist_seq=" + ord_prod_stat_hist_seq +
                ", ord_prod_seq=" + ord_prod_seq +
                ", chg_start_date='" + chg_start_date + '\'' +
                ", chg_end_date='" + chg_end_date + '\'' +
                ", ord_stat=" + ord_stat +
                ", deli_stat=" + deli_stat +
                ", pay_stat=" + pay_stat +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }

    public Integer getOrd_prod_stat_hist_seq() {
        return ord_prod_stat_hist_seq;
    }

    public void setOrd_prod_stat_hist_seq(Integer ord_prod_stat_hist_seq) {
        this.ord_prod_stat_hist_seq = ord_prod_stat_hist_seq;
    }

    public Integer getOrd_prod_seq() {
        return ord_prod_seq;
    }

    public void setOrd_prod_seq(Integer ord_prod_seq) {
        this.ord_prod_seq = ord_prod_seq;
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
