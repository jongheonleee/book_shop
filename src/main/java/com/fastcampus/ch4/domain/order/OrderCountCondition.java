package com.fastcampus.ch4.domain.order;

public class OrderCountCondition {
    private Integer ord_stat;
    private Integer deli_stat;
    private Integer pay_stat;
    private String cust_id;

    public static OrderCountCondition from(Integer ord_stat, Integer deli_stat, Integer pay_stat, String cust_id) {
        OrderCountCondition dto = new OrderCountCondition();
        dto.setOrd_stat(ord_stat);
        dto.setDeli_stat(deli_stat);
        dto.setPay_stat(pay_stat);
        dto.setCust_id(cust_id);
        return dto;
    }

    @Override
    public String toString() {
        return "OrderCountCondition{" +
                "ord_stat=" + ord_stat +
                ", deli_stat=" + deli_stat +
                ", pay_stat=" + pay_stat +
                ", cust_id='" + cust_id + '\'' +
                '}';
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
