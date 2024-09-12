package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductStatusHistoryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderProductStatusHistoryDaoImpl implements OrderProductStatusHistoryDao {
    @Autowired
    SqlSession sqlSession;

    private static final String namespace = "com.fastcampus.ch4.dao.order.OrderProductStatusHistoryMapper.";

    @Override
    public int insertHistory(OrderProductStatusHistoryDto orderProductStatusHistoryDto) {
        return sqlSession.insert(namespace + "insertOrderProductStatusHistory", orderProductStatusHistoryDto);
    }

    @Override
    public List<OrderProductStatusHistoryDto> selectHistoryByCondition(Map map) {
        return sqlSession.selectList(namespace + "selectListOrderProductStatusHistoryByCondition", map);
    }

    @Override
    public int updateHistory(OrderProductStatusHistoryDto orderProductStatusHistoryDto) {
        return sqlSession.update(namespace + "updateOrderProductStatusHistory", orderProductStatusHistoryDto);
    }

    @Override
    public int deleteHistoryByCondition(Map map) {
        return sqlSession.delete(namespace + "deleteOrderProductStatusHistoryByCondition", map);
    }

}
