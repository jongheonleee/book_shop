package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderProductDaoImpl implements OrderProductDao {
    @Autowired
    SqlSession sqlSession;

    @Override
    public Integer insertAndReturnSeq(OrderProductDto orderProductDto) {
        System.out.println(orderProductDto);
        return sqlSession.insert("insertAndReturnSeq", orderProductDto);
    }

    @Override
    public OrderProductDto selectById(String ord_seq, String isbn) {
        return null;
    }

    @Override
    public List<OrderProductDto> selectAll() {
        return List.of();
    }

    @Override
    public int update(OrderProductDto orderProductDto) {
        return 0;
    }

    @Override
    public int deleteById(String ord_seq, String isbn) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
