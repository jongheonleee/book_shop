package com.fastcampus.ch4.dao.cart;

import com.fastcampus.ch4.dto.cart.CartDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    SqlSession sqlSession;

    final static String namespace = "com.fastcampus.ch4.dao.CartMapper.";

    @Override
    public Integer insertAndReturnSeq (CartDto cartDto) {
        sqlSession.insert(namespace + "insertAndReturnSeq", cartDto);
        return cartDto.getCart_seq();
    }

    @Override
    public CartDto selectByMap (Map map) {
        return sqlSession.selectOne(namespace + "selectByCondition", map);
    }

    @Override
    public List<CartDto> selectListByCondition(Integer cartSeq, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("cart_seq", cartSeq);
        map.put("user_id", userId);
        return sqlSession.selectList(namespace + "selectByCondition", map);
    }

    @Override
    public int deleteByMap(Map map) {
        return sqlSession.delete(namespace + "deleteByMap", map);
    }

    @Override
    public int deleteAll() {
        return sqlSession.delete(namespace + "deleteAll");
    }
}
