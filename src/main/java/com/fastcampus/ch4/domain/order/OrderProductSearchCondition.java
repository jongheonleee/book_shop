package com.fastcampus.ch4.domain.order;

public class OrderProductSearchCondition {
    Integer ord_prod_seq;
    Integer ord_seq;
    Integer ord_stat;
    Integer deli_stat;
    Integer pay_stat;
    String cust_id;

    public static OrderProductSearchCondition from(Integer ord_prod_seq, Integer ord_seq, Integer ord_stat, Integer deli_stat, Integer pay_stat, String cust_id) {
        OrderProductSearchCondition dto = new OrderProductSearchCondition();
        dto.setOrd_prod_seq(ord_prod_seq);
        dto.setOrd_seq(ord_seq);
        dto.setOrd_stat(ord_stat);
        dto.setDeli_stat(deli_stat);
        dto.setPay_stat(pay_stat);
        dto.setCust_id(cust_id);
        return dto;
    }

    public Integer getOrd_prod_seq() {
        return ord_prod_seq;
    }

    public void setOrd_prod_seq(Integer ord_prod_seq) {
        this.ord_prod_seq = ord_prod_seq;
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

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }
}
