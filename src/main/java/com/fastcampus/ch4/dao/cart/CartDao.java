package com.fastcampus.ch4.dao.cart;

import com.fastcampus.ch4.dto.cart.CartDto;

import java.util.List;
import java.util.Map;

public interface CartDao {
    public Integer insertAndReturnSeq(CartDto cartDto);
    public CartDto selectByMap (Map map);
    public List<CartDto> selectListByCondition(Integer cartSeq, String userId);
    public int deleteByMap(Map map);
    public int deleteAll();
}
