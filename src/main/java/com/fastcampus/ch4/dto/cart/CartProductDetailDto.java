package com.fastcampus.ch4.dto.cart;

public class CartProductDetailDto implements PriceManipulatable {
//public class CartProductDetailDto {
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

    // 적립율
    Double papr_point;
    Double e_point;

    double point_perc; // prod_type_code 에 따라서 선택
    int point_pric; // 계산 필요

    // 할인 - 임시로 넣어준 값
    Double papr_disc;
    Double e_disc;
    Integer paper_bene_pric;
    Integer e_bene_pric;

    int basicPrice; // 상품정가
    double bene_perc; // prod_type_code 에 따라서 선택
    int bene_pric; // 할인액 (정가 * 할인율) 계산 필요
    int salePrice; // 상품 판매가 (상품정가 - 할인액)

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

    public double getPoint_perc() {
        return point_perc;
    }

    public void setPoint_perc(double point_perc) {
        this.point_perc = point_perc;
    }

    public int getPoint_pric() {
        return point_pric;
    }

    public void setPoint_pric(int point_pric) {
        this.point_pric = point_pric;
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

    public int getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(int basicPrice) {
        this.basicPrice = basicPrice;
    }

    public double getBene_perc() {
        return bene_perc;
    }

    public void setBene_perc(double bene_perc) {
        this.bene_perc = bene_perc;
    }

    public int getBene_pric() {
        return bene_pric;
    }

    public void setBene_pric(int bene_pric) {
        this.bene_pric = bene_pric;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
}
