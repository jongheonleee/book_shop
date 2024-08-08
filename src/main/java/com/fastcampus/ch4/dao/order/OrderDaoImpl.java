package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.OrderMapper.";

    /**
     * @param orderDto
     * @return ord_seq : 생성한 주문 pk
     */
    @Override
    public Integer createOrderAndReturnId(OrderDto orderDto) throws Exception {
        if (orderDto.getId() == null) {
            throw new IllegalArgumentException("주문을 생성하기 위해서는 회원 ID 가 필요합니다.");
        }

        if (orderDto.getDelivery_fee() != 0 || orderDto.getTotal_prod_pric() != 0 || orderDto.getTotal_bene_pric() != 0 || orderDto.getTotal_ord_pric() != 0) {
            throw new IllegalArgumentException("주문을 생성할 때 주문의 가격정보가 초기화된 상태이어야 합니다..");
        }

        // 시스템 컬럼 셋팅
        orderDto.setReg_id(orderDto.getId());
        orderDto.setUp_id(orderDto.getId());

        sqlSession.insert(namespace + "createOrder", orderDto);
        return orderDto.getOrd_seq();
    }

    /**
     * @param ordSeq
     * @return 조회한 orderDto
     */
    @Override
    public OrderDto findOrderById(Integer ordSeq) throws Exception {
        return sqlSession.selectOne(namespace + "findOrderById", ordSeq);
    }

    @Override
    public int deleteOrderById(Integer ordSeq) throws Exception {
        return sqlSession.delete(namespace + "deleteOrderById", ordSeq);
    }

    @Override
    public List<OrderDto> searchOrder() throws Exception {
        return List.of();
    }
}
