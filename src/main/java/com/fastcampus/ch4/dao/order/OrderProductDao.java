package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductDto;

import java.util.List;

public interface OrderProductDao {
    public Integer insertAndReturnSeq(OrderProductDto orderProductDto);
    public int count ();
    public OrderProductDto selectBySeq(Integer OrderProductSeq);
    public List<OrderProductDto> selectAll();
    public List<OrderProductDto> selectListByCondition(String userId);
    public int update(OrderProductDto orderProductDto);
    public int deleteById(String ord_seq, String isbn);
    public int deleteAll();
}
