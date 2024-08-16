package com.fastcampus.ch4.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoginHistoryDto {

  private int lognId;
  private LocalDateTime lognTime;  // 날짜 및 시간
  private String ipAddr;
  private LocalDateTime regDate;  // 날짜만
  private String regId;
  private LocalDateTime upDate;  // 날짜 및 시간
  private String upId;
  private String id;

  // Getter 및 Setter 메서드
  public int getLognId() {
    return lognId;
  }

  public void setLognId(int lognId) {
    this.lognId = lognId;
  }

  public LocalDateTime getLognTime() {
    return lognTime;
  }

  public void setLognTime(LocalDateTime lognTime) {
    this.lognTime = lognTime;
  }

  public String getIpAddr() {
    return ipAddr;
  }

  public void setIpAddr(String ipAddr) {
    this.ipAddr = ipAddr;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  // DTO 객체의 문자열 표현을 반환하는 메서드
  @Override
  public String toString() {
    return "LoginHistoryDto{" +
            "lognId=" + lognId +
            ", lognTime=" + lognTime +
            ", ipAddr='" + ipAddr + '\'' +
            ", regDate=" + regDate +
            ", regId='" + regId + '\'' +
            ", upDate=" + upDate +
            ", upId='" + upId + '\'' +
            ", id='" + id + '\'' +
            '}';
  }
}
