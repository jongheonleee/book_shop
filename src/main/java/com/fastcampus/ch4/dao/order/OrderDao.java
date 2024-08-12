package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;

import java.util.List;

public interface OrderDao {
    /**
     * 요구 기능
     * 1. 주문 생성하기
     * 2. 주문 조회하기
     * 3. 주문 삭제하기
     * 4. 주문 변경하기
     */

    public Integer insertAndReturnSeq(OrderDto orderDto) throws Exception;
    public OrderDto selectBySeq(Integer ordSeq) throws Exception;
    public List<OrderDto> selectAll(String columnName, boolean isDesc) throws Exception;
    public List<OrderDto> selectListByCondition(String userId) throws Exception;
    public int deleteBySeq(Integer ordSeq) throws Exception;
    public int deleteAll() throws Exception;
    public int update(OrderDto orderDto, String upId) throws Exception;
    public int countAll() throws Exception;
}
