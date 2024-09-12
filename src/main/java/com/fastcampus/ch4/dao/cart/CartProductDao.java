package com.fastcampus.ch4.dao.cart;

import com.fastcampus.ch4.dto.cart.CartProductDetailDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;

import java.util.List;

public interface CartProductDao {
    public int insert(CartProductDto cartProductDto);
    public CartProductDto selectOne(Integer cartSeq, String isbn, String prod_type_code);
    public List<CartProductDto> selectOneList(Integer cartSeq, String isbn, String prod_type_code, String userId);
    public List<CartProductDto> selectListByCartSeq(Integer cartSeq);
    public CartProductDetailDto selectOneDetail(Integer cartSeq, String isbn, String prod_type_code);
    public List<CartProductDetailDto> selectListDetailByCartSeq (Integer cartSeq);
    public int deleteOne(Integer cartSeq, String isbn, String prod_type_code);
    public int deleteByCartSeq(Integer cartSeq);
    public int updateItemQuantity(Integer cartSeq, String isbn, String prod_type_code, Integer itemQuantity, String userId);
    public int plusItemQuantity(Integer cart_seq, String isbn, String prod_type_code, String userId);
    public int minusItemQuantity(Integer cart_seq, String isbn, String prod_type_code, String userId);
}
