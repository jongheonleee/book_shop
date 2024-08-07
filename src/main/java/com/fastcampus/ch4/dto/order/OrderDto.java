package com.fastcampus.ch4.dto.order;

import java.util.Date;

public class OrderDto {
    // pk
    String ordNum; // 주문 번호

    // fk
    String id; // 유저 id

    // 속성
    Date createdAt; // 주문 생성 일자
    int deliveryFee; // 배송비
    int totalProductPrice; // 총 상품 금액
    int totalBenefitPrice; // 총 할인 금액
    int totalOrderPrice; // 총 주문 금액

    // 시스템 컬럼
    Date  regDate; // 최초 등록 일시
    String regId; // 최초 등록 id
    Date  upDate; // 최근 수정 일시
    String upId; // 최근 수정 id

    public OrderDto(String ordNum, String id) {
        this.ordNum = ordNum;
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "ordNum='" + ordNum + '\'' +
                ", id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", deliveryFee=" + deliveryFee +
                ", totalProductPrice=" + totalProductPrice +
                ", totalBenefitPrice=" + totalBenefitPrice +
                ", totalOrderPrice=" + totalOrderPrice +
                ", regDate=" + regDate +
                ", regId='" + regId + '\'' +
                ", upDate=" + upDate +
                ", upId='" + upId + '\'' +
                '}';
    }

    public String getOrdNum() {
        return ordNum;
    }

    public void setOrdNum(String ordNum) {
        this.ordNum = ordNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(int totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public int getTotalBenefitPrice() {
        return totalBenefitPrice;
    }

    public void setTotalBenefitPrice(int totalBenefitPrice) {
        this.totalBenefitPrice = totalBenefitPrice;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(int totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
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
