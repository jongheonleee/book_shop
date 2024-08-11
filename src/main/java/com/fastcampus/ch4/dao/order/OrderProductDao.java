package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductDto;

import java.util.List;

public interface OrderProductDao {
    public Integer insertAndReturnSeq(OrderProductDto orderProductDto);
    public OrderProductDto selectById(String ord_seq, String isbn);
    public List<OrderProductDto> selectAll();
    public int update(OrderProductDto orderProductDto);
    public int deleteById(String ord_seq, String isbn);
    public int deleteAll();
}
