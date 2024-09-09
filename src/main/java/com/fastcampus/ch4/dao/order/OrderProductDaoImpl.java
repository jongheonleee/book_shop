package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderProductDaoImpl implements OrderProductDao {
    @Autowired
    SqlSession sqlSession;

    private static final String namespace = "com.fastcampus.ch4.dao.order.OrderProductMapper.";

    @Override
    public int insertOrderProduct(OrderProductDto orderProductDto) {
        return sqlSession.insert(namespace + "insertOrderProduct", orderProductDto);
    }

    @Override
    public List<OrderProductDto> selectOrderProductByCondition(Map map) {
        return sqlSession.selectList(namespace + "selectListOrderProductByCondition", map);
    }

    @Override
    public int updateOrderProduct(OrderProductDto orderProductDto) {
        return sqlSession.update(namespace + "updateOrderProduct", orderProductDto);
    }

    @Override
    public int deleteOrderProductByCondition(Map map) {
        return sqlSession.delete(namespace + "deleteOrderProductByCondition", map);
    }


}
