package com.fastcampus.ch4.dto.member;

import java.time.LocalDateTime;

public class TermAgreeDto {

  private String id;           // 회원 ID
  private int termId;          // 약관 아이디
  private String termAgree;    // 약관 동의 여부 (Y/N)
  private LocalDateTime regDate; // 등록일
  private String regId;        // 등록자 ID
  private LocalDateTime upDate; // 수정일
  private String upId;         // 수정자 ID


  public  TermAgreeDto(){
  }

  // 필수 정보만 받는 생성자
  public TermAgreeDto(String id, int termId, String termAgree) {
    this.id = id;
    this.termId = termId;
    this.termAgree = termAgree;
  }

  public TermAgreeDto(String id, int termId, String termAgree, String regId) {
    this(id, termId, termAgree);
    this.regId = regId;
    // regDate, upDate, upId는 null로 설정됨
  }
  // 선택적인 정보는 setter를 통해 설정
  public TermAgreeDto(String id, int termId, String termAgree, LocalDateTime regDate, String regId, LocalDateTime upDate, String upId) {
    this.id = id;
    this.termId = termId;
    this.termAgree = termAgree;
    this.regDate = regDate;
    this.regId = regId;
    this.upDate = upDate;
    this.upId = upId;
  }
    // Getter 및 Setter 메서드
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getTermId() {
    return termId;
  }

  public void setTermId(int termId) {
    this.termId = termId;
  }

  public String getTermAgree() {
    return termAgree;
  }

  public void setTermAgree(String termAgree) {
    this.termAgree = termAgree;
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

  // DTO 객체의 문자열 표현을 반환하는 메서드
  @Override
  public String toString() {
    return "TermAgreeDto{" +
            "id='" + id + '\'' +
            ", termId=" + termId +
            ", termAgree='" + termAgree + '\'' +
            ", regDate=" + regDate +
            ", regId='" + regId + '\'' +
            ", upDate=" + upDate +
            ", upId='" + upId + '\'' +
            '}';
  }
}
