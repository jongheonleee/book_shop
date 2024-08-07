package com.fastcampus.ch4.dto.order;

import java.util.Date;

public class OrderDto {
    // pk
    int ordSeq; // 주문 번호

    // fk
    String id; // 유저 id

    // 속성
    Date createdAt; // 주문 생성 일자
    int deliveryFee = 0; // 배송비
    int totalProdPric = 0; // 총 상품 금액
    int totalBenePric = 0; // 총 할인 금액
    int totalOrdPric = 0; // 총 주문 금액

    // 시스템 컬럼
    Date  regDate; // 최초 등록 일시
    String regId; // 최초 등록 id
    Date  upDate; // 최근 수정 일시
    String upId; // 최근 수정 id

    public OrderDto(int ordSeq, String id) {
        this.ordSeq = ordSeq;
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "ordNum='" + ordSeq + '\'' +
                ", id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", deliveryFee=" + deliveryFee +
                ", totalProductPrice=" + totalProdPric +
                ", totalBenefitPrice=" + totalBenePric +
                ", totalOrderPrice=" + totalOrdPric +
                ", regDate=" + regDate +
                ", regId='" + regId + '\'' +
                ", upDate=" + upDate +
                ", upId='" + upId + '\'' +
                '}';
    }

    public int getOrdSeq() {
        return ordSeq;
    }

    public void setOrdSeq(int ordSeq) {
        this.ordSeq = ordSeq;
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

    public int getTotalProdPric() {
        return totalProdPric;
    }

    public void setTotalProdPric(int totalProdPric) {
        this.totalProdPric = totalProdPric;
    }

    public int getTotalBenePric() {
        return totalBenePric;
    }

    public void setTotalBenePric(int totalBenePric) {
        this.totalBenePric = totalBenePric;
    }

    public int getTotalOrdPric() {
        return totalOrdPric;
    }

    public void setTotalOrdPric(int totalOrdPric) {
        this.totalOrdPric = totalOrdPric;
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
