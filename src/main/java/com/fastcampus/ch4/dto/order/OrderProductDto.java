package com.fastcampus.ch4.dto.order;

import java.util.Date;
import java.util.Objects;

public class OrderProductDto {
    // PK
    Integer ord_prod_num; // 주문 상품 번호

    // FK
    Integer ord_num; // 주문 번호
    String isbn; // 도서 pk
    String prod_type_code; // 상품 유형 코드

    // NOT NULL
    String ord_stat; // 주문 상태
    String deli_stat; // 배송 상태
    String pay_stat; // 결제 상태

    // NULL
    int item_quan = 0; //주문 상품 수량
    double point_perc = 0; // 적립율
    int point_pric = 0; // 적립금
    int basic_pric = 0; // 상품 정가
    double bene_perc = 0; // 상품 할인율
    double bene_pric = 0; // 상품 할인 가격
    int sale_pric = 0; // 상품 판매 가격
    int ord_pric = 0; // 주문 금액

    // 시스템 컬럼
    Date reg_date; // 최초 등록 일시
    String regId; // 최초 등록 id
    Date up_date; // 최근 수정 일시
    String up_id; // 최근 수정 id

    @Override
    public String toString() {
        return "OrderProductDto{" +
                "ord_prod_num=" + ord_prod_num +
                ", ord_num=" + ord_num +
                ", isbn='" + isbn + '\'' +
                ", prod_type_code='" + prod_type_code + '\'' +
                ", ord_stat='" + ord_stat + '\'' +
                ", deli_stat='" + deli_stat + '\'' +
                ", pay_stat='" + pay_stat + '\'' +
                ", item_quan=" + item_quan +
                ", point_perc=" + point_perc +
                ", point_pric=" + point_pric +
                ", basic_pric=" + basic_pric +
                ", bene_perc=" + bene_perc +
                ", bene_pric=" + bene_pric +
                ", sale_pric=" + sale_pric +
                ", ord_pric=" + ord_pric +
                ", reg_date=" + reg_date +
                ", regId='" + regId + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductDto)) return false;
        OrderProductDto that = (OrderProductDto) o;
        return item_quan == that.item_quan && Double.compare(point_perc, that.point_perc) == 0 && point_pric == that.point_pric && basic_pric == that.basic_pric && Double.compare(bene_perc, that.bene_perc) == 0 && Double.compare(bene_pric, that.bene_pric) == 0 && sale_pric == that.sale_pric && ord_pric == that.ord_pric && Objects.equals(ord_prod_num, that.ord_prod_num) && Objects.equals(ord_num, that.ord_num) && Objects.equals(isbn, that.isbn) && Objects.equals(prod_type_code, that.prod_type_code) && Objects.equals(ord_stat, that.ord_stat) && Objects.equals(deli_stat, that.deli_stat) && Objects.equals(pay_stat, that.pay_stat) && Objects.equals(reg_date, that.reg_date) && Objects.equals(regId, that.regId) && Objects.equals(up_date, that.up_date) && Objects.equals(up_id, that.up_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord_prod_num, ord_num, isbn, prod_type_code, ord_stat, deli_stat, pay_stat, item_quan, point_perc, point_pric, basic_pric, bene_perc, bene_pric, sale_pric, ord_pric, reg_date, regId, up_date, up_id);
    }

    public Integer getOrd_prod_num() {
        return ord_prod_num;
    }

    public void setOrd_prod_num(Integer ord_prod_num) {
        this.ord_prod_num = ord_prod_num;
    }

    public Integer getOrd_num() {
        return ord_num;
    }

    public void setOrd_num(Integer ord_num) {
        this.ord_num = ord_num;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getProd_type_code() {
        return prod_type_code;
    }

    public void setProd_type_code(String prod_type_code) {
        this.prod_type_code = prod_type_code;
    }

    public String getOrd_stat() {
        return ord_stat;
    }

    public void setOrd_stat(String ord_stat) {
        this.ord_stat = ord_stat;
    }

    public String getDeli_stat() {
        return deli_stat;
    }

    public void setDeli_stat(String deli_stat) {
        this.deli_stat = deli_stat;
    }

    public String getPay_stat() {
        return pay_stat;
    }

    public void setPay_stat(String pay_stat) {
        this.pay_stat = pay_stat;
    }

    public int getItem_quan() {
        return item_quan;
    }

    public void setItem_quan(int item_quan) {
        this.item_quan = item_quan;
    }

    public double getPoint_perc() {
        return point_perc;
    }

    public void setPoint_perc(double point_perc) {
        this.point_perc = point_perc;
    }

    public int getPoint_pric() {
        return point_pric;
    }

    public void setPoint_pric(int point_pric) {
        this.point_pric = point_pric;
    }

    public int getBasic_pric() {
        return basic_pric;
    }

    public void setBasic_pric(int basic_pric) {
        this.basic_pric = basic_pric;
    }

    public double getBene_perc() {
        return bene_perc;
    }

    public void setBene_perc(double bene_perc) {
        this.bene_perc = bene_perc;
    }

    public double getBene_pric() {
        return bene_pric;
    }

    public void setBene_pric(double bene_pric) {
        this.bene_pric = bene_pric;
    }

    public int getSale_pric() {
        return sale_pric;
    }

    public void setSale_pric(int sale_pric) {
        this.sale_pric = sale_pric;
    }

    public int getOrd_pric() {
        return ord_pric;
    }

    public void setOrd_pric(int ord_pric) {
        this.ord_pric = ord_pric;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
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
