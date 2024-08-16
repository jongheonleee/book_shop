package com.fastcampus.ch4.dto.member;

import java.time.LocalDateTime;

public class ShippingAddressDto {
   // 주소의 고유 식별자
  private String userId;
  private String address;// 사용자 ID
  private String regiAddress1; // 등록 주소지 1
  private String regiAddress2; // 등록 주소지 2
  private String rcntAddress1; // 최근 주소지 1
  private String rcntAddress2; // 최근 주소지 2
  private LocalDateTime regDate; // 생성 날짜
  private String regId; // 등록자 ID
  private LocalDateTime upDate; // 수정 날짜
  private String upId;// 수정자 ID

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRegiAddress1() {
    return regiAddress1;
  }

  public void setRegiAddress1(String regiAddress1) {
    this.regiAddress1 = regiAddress1;
  }

  public String getRegiAddress2() {
    return regiAddress2;
  }

  public void setRegiAddress2(String regiAddress2) {
    this.regiAddress2 = regiAddress2;
  }

  public String getRcntAddress1() {
    return rcntAddress1;
  }

  public void setRcntAddress1(String rcntAddress1) {
    this.rcntAddress1 = rcntAddress1;
  }

  public String getRcntAddress2() {
    return rcntAddress2;
  }

  public void setRcntAddress2(String rcntAddress2) {
    this.rcntAddress2 = rcntAddress2;
  }

  public LocalDateTime getRegDate() {
    return regDate;
  }

  public void setRegDate(LocalDateTime regDate) {
    this.regDate = regDate;
  }

  public String getRegId() {
    return regId;
  }

  public void setRegId(String regId) {
    this.regId = regId;
  }

  public LocalDateTime getUpDate() {
    return upDate;
  }

  public void setUpDate(LocalDateTime upDate) {
    this.upDate = upDate;
  }

  public String getUpId() {
    return upId;
  }

  public void setUpId(String upId) {
    this.upId = upId;
  }
// Getters and Setters
}
