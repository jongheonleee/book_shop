package com.fastcampus.ch4.domain.cart;

public class ItemInfoDto {
    Integer cartSeq;
    String isbn;
    String prodTypeCode;
    Integer itemQuantity;

    public Integer getCartSeq() {
        return cartSeq;
    }

    public void setCartSeq(Integer cartSeq) {
        this.cartSeq = cartSeq;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getProdTypeCode() {
        return prodTypeCode;
    }

    public void setProdTypeCode(String prodTypeCode) {
        this.prodTypeCode = prodTypeCode;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
