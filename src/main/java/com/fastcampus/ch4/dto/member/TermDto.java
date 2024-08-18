package com.fastcampus.ch4.dto.member;

import java.time.LocalDateTime;

public class TermDto {
  private int termId;          // term_id, 기본키 및 auto_increment
  private String termName;     // term_name
  private String termContent;  // term_cont
  private String required;    // required
  private LocalDateTime regDate; // reg_date
  private String regId;        // reg_id
  private LocalDateTime upDate; // up_date
  private String upId;         // up_id

  public TermDto(){

  }
  public TermDto(int termId, String termName, String termContent, String required, LocalDateTime regDate, String regId, LocalDateTime upDate, String upId) {
    this.termId = termId;
    this.termName = termName;
    this.termContent = termContent;
    this.required = required;
    this.regDate = regDate;
    this.regId = regId;
    this.upDate = upDate;
    this.upId = upId;
  }

  // Getters and Setters
  public int getTermId() {
    return termId;
  }

  public void setTermId(int termId) {
    this.termId = termId;
  }

  public String getTermName() {
    return termName;
  }

  public void setTermName(String termName) {
    this.termName = termName;
  }

  public String getTermContent() {
    return termContent;
  }

  public void setTermContent(String termContent) {
    this.termContent = termContent;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
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

  // toString() 메서드
  @Override
  public String toString() {
    return "TermDto{" +
            "termId=" + termId +
            ", termName='" + termName + '\'' +
            ", termContent='" + termContent + '\'' +
            ", required=" + required +
            ", regDate=" + regDate +
            ", regId='" + regId + '\'' +
            ", upDate=" + upDate +
            ", upId='" + upId + '\'' +
            '}';
  }
}