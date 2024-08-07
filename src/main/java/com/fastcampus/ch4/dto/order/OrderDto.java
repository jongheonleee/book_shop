package com.fastcampus.ch4.dto.order;

import java.util.Date;

public class OrderDto {
    // pk
    // auto increment
    private Integer ordSeq; // 주문 번호

    // fk
    private String id; // 유저 id

    // 속성
    private Date createdAt; // 주문 생성 일자
    private Integer deliveryFee = 0; // 배송비
    private Integer totalProdPric = 0; // 총 상품 금액
    private Integer totalBenePric = 0; // 총 할인 금액
    private Integer totalOrdPric = 0; // 총 주문 금액

    // 시스템 컬럼
    private Date  regDate; // 최초 등록 일시
    private String regId; // 최초 등록 id
    private Date  upDate; // 최근 수정 일시
    private String upId; // 최근 수정 id

    public OrderDto(String id) {
        this.id = id;
    }

    public Integer getOrdSeq() {
        return ordSeq;
    }

    public void setOrdSeq(Integer ordSeq) {
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

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getTotalProdPric() {
        return totalProdPric;
    }

    public void setTotalProdPric(Integer totalProdPric) {
        this.totalProdPric = totalProdPric;
    }

    public Integer getTotalBenePric() {
        return totalBenePric;
    }

    public void setTotalBenePric(Integer totalBenePric) {
        this.totalBenePric = totalBenePric;
    }

    public Integer getTotalOrdPric() {
        return totalOrdPric;
    }

    public void setTotalOrdPric(Integer totalOrdPric) {
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
