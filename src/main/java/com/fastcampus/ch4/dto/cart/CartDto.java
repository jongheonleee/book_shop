package com.fastcampus.ch4.dto.cart;

import com.fastcampus.ch4.model.cart.UserType;

import java.util.Date;
import java.util.Objects;

public class CartDto {
    // pk
    private Integer cart_seq;

    // fk
    private String userId;

    // 속성
    private String created_at;

    // 시스템 컬럼
    private Date reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Date up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    public static CartDto create() {
        return new CartDto();
    }

    public static CartDto create(String userId) {
        CartDto cartDto = new CartDto();
        cartDto.setUserId(userId);
        cartDto.setReg_id(userId);
        cartDto.setUp_id(userId);
        return cartDto;
    }

    // 비회원 장바구니 생성 정적 팩토리 메서드
    public static CartDto create(UserType userType) {
        if (!userType.equals(UserType.NON_MEMBERSHIP)) {
            throw new IllegalArgumentException("비회원 장바구니 생성에만 가능합니다.");
        }
        CartDto cartDto = new CartDto();
        cartDto.setReg_id(UserType.NON_MEMBERSHIP.getCode());
        cartDto.setUp_id(UserType.NON_MEMBERSHIP.getCode());
        return cartDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDto)) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(cart_seq, cartDto.cart_seq) && Objects.equals(userId, cartDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart_seq, userId);
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "cart_seq=" + cart_seq +
                ", userId='" + userId + '\'' +
                ", created_at='" + created_at + '\'' +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }

    public Integer getCart_seq() {
        return cart_seq;
    }

    public void setCart_seq(Integer cart_seq) {
        this.cart_seq = cart_seq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
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
