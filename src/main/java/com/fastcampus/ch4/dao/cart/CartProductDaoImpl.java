package com.fastcampus.ch4.dao.cart;

import com.fastcampus.ch4.dto.cart.CartProductDetailDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartProductDaoImpl implements CartProductDao {
    @Autowired
    SqlSession sqlSession;

    private static final String namespace = "com.fastcampus.ch4.dao.CartProductMapper.";

    @Override
    public int insert(CartProductDto cartProductDto) {
        return sqlSession.insert(namespace + "insert", cartProductDto);
    }

    @Override
    public CartProductDto selectOne(Integer cartSeq, String isbn, String prod_type_code) {
        Map map = new HashMap();
        map.put("cart_seq", cartSeq);
        map.put("isbn", isbn);
        map.put("prod_type_code", prod_type_code);
        return sqlSession.selectOne(namespace + "selectOne", map);
    }

    @Override
    public List<CartProductDto> selectListByCartSeq(Integer cartSeq) {
        return sqlSession.selectList(namespace + "selectListByCartSeq", cartSeq);
    }

    @Override
    public CartProductDetailDto selectOneDetail(Integer cartSeq, String isbn, String prod_type_code) {
        Map map = new HashMap();
        map.put("cart_seq", cartSeq);
        map.put("isbn", isbn);
        map.put("prod_type_code", prod_type_code);
        return sqlSession.selectOne(namespace + "selectOneJoinBook", map);
    }

    @Override
    public List<CartProductDetailDto> selectListDetailByCartSeq(Integer cartSeq) {
        Map map = new HashMap();
        map.put("cart_seq", cartSeq);
        return sqlSession.selectList(namespace + "selectOneJoinBook", map);
    }

    @Override
    public int deleteOne(Integer cartSeq, String isbn, String prod_type_code) {
        Map map = new HashMap();
        map.put("cart_seq", cartSeq);
        map.put("isbn", isbn);
        map.put("prod_type_code", prod_type_code);
        return sqlSession.delete(namespace + "deleteByMap", map);
    }

    @Override
    public int deleteByCartSeq(Integer cartSeq) {
        Map map = new HashMap();
        map.put("cart_seq", cartSeq);
        return sqlSession.delete(namespace + "deleteByMap", map);
    }

    @Override
    public int updateItemQuantity(Integer cartSeq, String isbn, String prod_type_code, Boolean isPlus, String userId) {
        Map map = new HashMap();
        map.put("cart_seq", cartSeq);
        map.put("isbn", isbn);
        map.put("prod_type_code", prod_type_code);
        map.put("isPlus", isPlus);
        map.put("userId", userId);
        return sqlSession.update(namespace + "updateItemQuantity", map);
    }
}
