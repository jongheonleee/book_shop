package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderProductDto;

import java.util.List;
import java.util.Map;

public interface OrderProductDao {
    public Integer insertAndReturnSeq(OrderProductDto orderProductDto);
    public int count ();
    public OrderProductDto selectBySeq(Integer orderProductSeq);
    public List<OrderProductDto> selectAll();
    public List<OrderProductDto> selectListByCondition(Map<String, Object> map);
    public int update(OrderProductDto orderProductDto);
    public int deleteBySeq(Integer orderProductSeq);
    public int deleteAll();
}
