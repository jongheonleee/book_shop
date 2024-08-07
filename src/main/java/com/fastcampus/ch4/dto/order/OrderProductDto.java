package com.fastcampus.ch4.dto.order;

import java.util.Date;

public class OrderProductDto {
    // PK
    String ordProdNum; // 주문 상품 번호

    // FK
    String ordNum; // 주문 번호
    String isbn; // 도서 pk
    String prodTypeCode; // 상품 유형 코드

    // 속성
    String ordStat; // 주문 상태
    int itemQuan; //주문 상품 수량
    int basicPric; // 상품 가격
    double benePerc; // 상품 할인율
    double benePric; // 상품 할인 가격
    int salePric; // 상품 판매 가격
    int ordPric; // 주문 금액

    // 시스템 컬럼
    Date regDate; // 최초 등록 일시
    String regId; // 최초 등록 id
    Date  upDate; // 최근 수정 일시
    String upId; // 최근 수정 id

    public OrderProductDto(String ordNum, String isbn, String prodTypeCode) {
        this.ordNum = ordNum;
        this.isbn = isbn;
        this.prodTypeCode = prodTypeCode;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "ordProdNum='" + ordProdNum + '\'' +
                ", ordNum='" + ordNum + '\'' +
                ", isbn='" + isbn + '\'' +
                ", prodTypeCode='" + prodTypeCode + '\'' +
                ", ordStat='" + ordStat + '\'' +
                ", itemQuan=" + itemQuan +
                ", basicPric=" + basicPric +
                ", benePerc=" + benePerc +
                ", benePric=" + benePric +
                ", salePric=" + salePric +
                ", ordPric=" + ordPric +
                ", regDate=" + regDate +
                ", regId='" + regId + '\'' +
                ", upDate=" + upDate +
                ", upId='" + upId + '\'' +
                '}';
    }

    public String getOrdProdNum() {
        return ordProdNum;
    }

    public void setOrdProdNum(String ordProdNum) {
        this.ordProdNum = ordProdNum;
    }

    public String getOrdNum() {
        return ordNum;
    }

    public void setOrdNum(String ordNum) {
        this.ordNum = ordNum;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getProdTypeCode() {
        return prodTypeCode;
    }

    public void setProdTypeCode(String prodTypeCode) {
        this.prodTypeCode = prodTypeCode;
    }

    public String getOrdStat() {
        return ordStat;
    }

    public void setOrdStat(String ordStat) {
        this.ordStat = ordStat;
    }

    public int getItemQuan() {
        return itemQuan;
    }

    public void setItemQuan(int itemQuan) {
        this.itemQuan = itemQuan;
    }

    public int getBasicPric() {
        return basicPric;
    }

    public void setBasicPric(int basicPric) {
        this.basicPric = basicPric;
    }

    public double getBenePerc() {
        return benePerc;
    }

    public void setBenePerc(double benePerc) {
        this.benePerc = benePerc;
    }

    public double getBenePric() {
        return benePric;
    }

    public void setBenePric(double benePric) {
        this.benePric = benePric;
    }

    public int getSalePric() {
        return salePric;
    }

    public void setSalePric(int salePric) {
        this.salePric = salePric;
    }

    public int getOrdPric() {
        return ordPric;
    }

    public void setOrdPric(int ordPric) {
        this.ordPric = ordPric;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }
}
