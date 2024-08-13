package com.fastcampus.ch4.dto.cart;

import com.fastcampus.ch4.model.cart.UserType;

import java.util.Date;
import java.util.Objects;

public class CartProductDto {
    // fk
    private Integer cart_seq;
    private String isbn;
    private String prod_type_code;

    // NOT NULL
    private Integer item_quan;
    private String created_at;
    private String updated_at;

    // 시스템 컬럼
    private Date reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Date up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    public static CartProductDto create(Integer cart_seq, String isbn, String prod_type_code, Integer item_quan, String userId) {
        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setCart_seq(cart_seq);
        cartProductDto.setIsbn(isbn);
        cartProductDto.setProd_type_code(prod_type_code);
        cartProductDto.setItem_quan(item_quan);
        cartProductDto.setReg_id(userId);
        cartProductDto.setUp_id(userId);
        return cartProductDto;
    }

    public static CartProductDto create(Integer cart_seq, String isbn, String prod_type_code, Integer item_quan, String userId, UserType userType) {
        CartProductDto cartProductDto = create(cart_seq, isbn, prod_type_code, item_quan, userId);
        if (UserType.NON_MEMBERSHIP.equals(userType)) {
            cartProductDto.setReg_id(UserType.NON_MEMBERSHIP.getCode());
            cartProductDto.setUp_id(UserType.NON_MEMBERSHIP.getCode());
        }
        return cartProductDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartProductDto)) return false;
        CartProductDto that = (CartProductDto) o;
        return Objects.equals(cart_seq, that.cart_seq) && Objects.equals(isbn, that.isbn) && Objects.equals(prod_type_code, that.prod_type_code) && Objects.equals(item_quan, that.item_quan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart_seq, isbn, prod_type_code, item_quan);
    }

    public Integer getCart_seq() {
        return cart_seq;
    }

    public void setCart_seq(Integer cart_seq) {
        this.cart_seq = cart_seq;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getProd_type_code() {
        return prod_type_code;
    }

    public void setProd_type_code(String prod_type_code) {
        this.prod_type_code = prod_type_code;
    }

    public Integer getItem_quan() {
        return item_quan;
    }

    public void setItem_quan(Integer item_quan) {
        this.item_quan = item_quan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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
