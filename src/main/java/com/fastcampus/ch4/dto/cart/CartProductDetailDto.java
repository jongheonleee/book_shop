package com.fastcampus.ch4.dto.cart;

import java.util.Date;

public class CartProductDetailDto {
    // CartProductDto
    // fk
    Integer cart_seq;
    String isbn;
    String prod_type_code;

    // NOT NULL
    Integer item_quan;
    String created_at;
    String updated_at;


    // BookDto
    String book_title;

    // 정가
    Integer papr_pric;
    Integer e_pric;
    // 적립
    Double papr_point; // 적립
    Double e_point;

    // 할인 - 임시로 넣어준 값
    Double papr_disc;
    Double e_disc;
    Integer paper_bene_pric;
    Integer e_bene_pric;

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

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public Integer getPapr_pric() {
        return papr_pric;
    }

    public void setPapr_pric(Integer papr_pric) {
        this.papr_pric = papr_pric;
    }

    public Integer getE_pric() {
        return e_pric;
    }

    public void setE_pric(Integer e_pric) {
        this.e_pric = e_pric;
    }

    public Double getPapr_point() {
        return papr_point;
    }

    public void setPapr_point(Double papr_point) {
        this.papr_point = papr_point;
    }

    public Double getE_point() {
        return e_point;
    }

    public void setE_point(Double e_point) {
        this.e_point = e_point;
    }

    public Double getPapr_disc() {
        return papr_disc;
    }

    public void setPapr_disc(Double papr_disc) {
        this.papr_disc = papr_disc;
    }

    public Double getE_disc() {
        return e_disc;
    }

    public void setE_disc(Double e_disc) {
        this.e_disc = e_disc;
    }

    public Integer getPaper_bene_pric() {
        return paper_bene_pric;
    }

    public void setPaper_bene_pric(Integer paper_bene_pric) {
        this.paper_bene_pric = paper_bene_pric;
    }

    public Integer getE_bene_pric() {
        return e_bene_pric;
    }

    public void setE_bene_pric(Integer e_bene_pric) {
        this.e_bene_pric = e_bene_pric;
    }
}
