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

    String namespace = "com.fastcampus.ch4.dao.OrderProductMapper.";

    @Override
    public Integer insertAndReturnSeq(OrderProductDto orderProductDto) {
        sqlSession.insert(namespace + "insertAndReturnSeq", orderProductDto);
        return orderProductDto.getOrd_prod_seq();
    }

    @Override
    public int count() {
        return sqlSession.selectOne(namespace +"count");
    }

    @Override
    public OrderProductDto selectBySeq(Integer orderProductSeq) {
        return sqlSession.selectOne(namespace + "selectBySeq", orderProductSeq);
    }

    @Override
    public List<OrderProductDto> selectAll() {
        return sqlSession.selectList(namespace + "selectAll");
    }

    @Override
    public List<OrderProductDto> selectListByCondition(Map<String, Object> map) {
        return sqlSession.selectList(namespace + "selectListByCondition", map);
    }

    @Override
    public int update(OrderProductDto orderProductDto) {
        return sqlSession.update(namespace + "update", orderProductDto);
    }

    @Override
    public int deleteBySeq(Integer orderProductSeq) {
        return sqlSession.delete(namespace + "deleteBySeq", orderProductSeq);
    }

    @Override
    public int deleteAll() {
        return sqlSession.delete(namespace + "deleteAll");
    }
}
