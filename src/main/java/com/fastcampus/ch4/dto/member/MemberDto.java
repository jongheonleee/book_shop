package com.fastcampus.ch4.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberDto {
  private String id;
  private String pswd;
  private String email;
  private String phnNumb; // 전화번호는 문자열로 처리
  private LocalDate joinDate; // 날짜를 LocalDate로 처리
  private int failLognAtmt;
  private String acntLock;
  private String isMailVrfi;
  private String mailTokn;
  private String name;
  private LocalDate brthDate; // 날짜를 LocalDate로 처리
  private String isAcntActv;
  private String adsReceMthd;
  private String grad;
  private String isCanc;
  private LocalDate regDate; // 날짜를 LocalDate로 처리
  private String regId;
  private LocalDateTime upDate; // 날짜를 LocalDateTime으로 처리
  private String upId;

  public MemberDto() {}

  // Getters and Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPswd() {
    return pswd;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhnNumb() {
    return phnNumb;
  }

  public void setPhnNumb(String phnNumb) {
    this.phnNumb = phnNumb;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
  }

  public int getFailLognAtmt() {
    return failLognAtmt;
  }

  public void setFailLognAtmt(int failLognAtmt) {
    this.failLognAtmt = failLognAtmt;
  }

  public String getAcntLock() {
    return acntLock;
  }

  public void setAcntLock(String acntLock) {
    this.acntLock = acntLock;
  }

  public String getIsMailVrfi() {
    return isMailVrfi;
  }

  public void setIsMailVrfi(String isMailVrfi) {
    this.isMailVrfi = isMailVrfi;
  }

  public String getMailTokn() {
    return mailTokn;
  }

  public void setMailTokn(String mailTokn) {
    this.mailTokn = mailTokn;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBrthDate() {
    return brthDate;
  }

  public void setBrthDate(LocalDate brthDate) {
    this.brthDate = brthDate;
  }

  public String getIsAcntActv() {
    return isAcntActv;
  }

  public void setIsAcntActv(String isAcntActv) {
    this.isAcntActv = isAcntActv;
  }

  public String getAdsReceMthd() {
    return adsReceMthd;
  }

  public void setAdsReceMthd(String adsReceMthd) {
    this.adsReceMthd = adsReceMthd;
  }

  public String getGrad() {
    return grad;
  }

  public void setGrad(String grad) {
    this.grad = grad;
  }

  public String getIsCanc() {
    return isCanc;
  }

  public void setIsCanc(String isCanc) {
    this.isCanc = isCanc;
  }

  public LocalDate getRegDate() {
    return regDate;
  }

  public void setRegDate(LocalDate regDate) {
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

  @Override
  public String toString() {
    return "MemberDto{" +
            "id='" + id + '\'' +
            ", pswd='" + pswd + '\'' +
            ", email='" + email + '\'' +
            ", phnNumb='" + phnNumb + '\'' +
            ", joinDate=" + joinDate +
            ", failLognAtmt=" + failLognAtmt +
            ", acntLock='" + acntLock + '\'' +
            ", isMailVrfi='" + isMailVrfi + '\'' +
            ", mailTokn='" + mailTokn + '\'' +
            ", name='" + name + '\'' +
            ", brthDate=" + brthDate +
            ", isAcntActv='" + isAcntActv + '\'' +
            ", adsReceMthd='" + adsReceMthd + '\'' +
            ", grad='" + grad + '\'' +
            ", isCanc='" + isCanc + '\'' +
            ", regDate=" + regDate +
            ", regId='" + regId + '\'' +
            ", upDate=" + upDate +
            ", upId='" + upId + '\'' +
            '}';
  }
}
