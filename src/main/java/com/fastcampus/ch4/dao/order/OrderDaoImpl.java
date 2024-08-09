package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.OrderMapper.";

    @Override
    public Integer insertAndReturnSeq(OrderDto orderDto) throws Exception {
        sqlSession.insert(namespace + "insertAndReturnId", orderDto);
        return orderDto.getOrd_seq();
    }

    @Override
    public OrderDto selectBySeq(Integer ordSeq) throws Exception {
        return sqlSession.selectOne(namespace + "selectById", ordSeq);
    }

    @Override
    public List<OrderDto> selectAll(String columnName, boolean isDesc) throws Exception {
        Map<String, String> map = new HashMap<>();

        String orderCriterion = isDesc ? "DESC" : "ASC";
        String orderCondition = columnName + "_" + orderCriterion;
        map.put("orderCondition", orderCondition);
        return sqlSession.selectList(namespace + "selectAll", map);
    }

    @Override
    public int deleteBySeq(Integer ordSeq) throws Exception {
        return sqlSession.delete(namespace + "deleteById", ordSeq);
    }

    @Override
    public int deleteAll() throws Exception {
        return sqlSession.delete(namespace + "deleteAll");
    }

    @Override
    public int update(OrderDto orderDto, String upId) throws Exception {
        orderDto.setUp_id(upId);
        return sqlSession.update(namespace + "update", orderDto);
    }

    @Override
    public int countAll() throws Exception {
        return sqlSession.selectOne(namespace + "countAll");
    }
}
