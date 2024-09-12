package com.fastcampus.ch4.dto.order.request;

// code.code_id upload
public class OrderStatusUpdateDto {
    private Integer ord_seq;
    private String up_id;
    private Integer ord_stat_id;
    private Integer deli_stat_id;
    private Integer pay_stat_id;

    public static OrderStatusUpdateDto from(Integer ord_seq, String up_id, Integer ord_stat_id, Integer deli_stat_id, Integer pay_stat_id) {
        OrderStatusUpdateDto dto = new OrderStatusUpdateDto();
        dto.setOrd_seq(ord_seq);
        dto.setUp_id(up_id);
        dto.setOrd_stat_id(ord_stat_id);
        dto.setDeli_stat_id(deli_stat_id);
        dto.setPay_stat_id(pay_stat_id);
        return dto;
    }

    public Integer getOrd_seq() {
        return ord_seq;
    }

    public void setOrd_seq(Integer ord_seq) {
        this.ord_seq = ord_seq;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }

    public Integer getOrd_stat_id() {
        return ord_stat_id;
    }

    public void setOrd_stat_id(Integer ord_stat_id) {
        this.ord_stat_id = ord_stat_id;
    }

    public Integer getDeli_stat_id() {
        return deli_stat_id;
    }

    public void setDeli_stat_id(Integer deli_stat_id) {
        this.deli_stat_id = deli_stat_id;
    }

    public Integer getPay_stat_id() {
        return pay_stat_id;
    }

    public void setPay_stat_id(Integer pay_stat_id) {
        this.pay_stat_id = pay_stat_id;
    }
}
