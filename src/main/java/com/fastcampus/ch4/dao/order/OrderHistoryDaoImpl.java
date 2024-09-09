package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderHistoryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderHistoryDaoImpl implements OrderHistoryDao {
    @Autowired
    SqlSession sqlSession;

    private static final String namespace = "com.fastcampus.ch4.dao.order.OrderHistoryMapper.";

    @Override
    public int insertOrderHistory(OrderHistoryDto dto) {
        return sqlSession.insert(namespace + "insertOrderHistory", dto);
    }

    @Override
    public List<OrderHistoryDto> selectOrderHistoryByCondition(Map map) {
        return sqlSession.selectList(namespace + "selectOrderHistoryByCondition", map);
    }

    @Override
    public int updateOrderHistory(OrderHistoryDto dto) {
        return sqlSession.update(namespace + "updateOrderHistory", dto);
    }

    @Override
    public int deleteOrderHistoryByCondition(Map map) {
        return sqlSession.delete(namespace + "deleteOrderHistoryByCondition", map);
    }

}
