package com.fastcampus.ch4.service.cart;

public interface CartService {
    Integer add(Integer cart_seq, String isbn, String prod_type_code, String userId);
    Integer createOrGetCart(Integer cartSeq, String userId);
    int addCartProduct(Integer cartSeq, String isbn, String prod_type_code, String userId);
}
