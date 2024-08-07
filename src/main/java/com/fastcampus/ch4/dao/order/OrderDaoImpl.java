package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dto.order.OrderDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    SqlSession sqlSession;

    String namespace = "com.fastcampus.ch4.dao.OrderMapper.";

    @Override
    public Integer createOrderAndReturnId(OrderDto orderDto) {
        /**
         * order 생성 시 필수 데이터
         * 1. id : 사용자 id
         */

        if (orderDto.getId() == null) {
            throw new IllegalArgumentException("주문을 생성하기 위해서는 회원 ID 가 필요합니다.");
        }

        if (orderDto.getDeliveryFee() != 0 || orderDto.getTotalProdPric() != 0 || orderDto.getTotalBenePric() != 0 || orderDto.getTotalOrdPric() != 0) {
            throw new IllegalArgumentException("주문을 생성할 때 주문의 가격정보가 초기화된 상태이어야 합니다..");
        }

        // 시스템 컬럼 셋팅
        orderDto.setRegId(orderDto.getId());
        orderDto.setUpId(orderDto.getId());

        try {
            sqlSession.insert(namespace + "createOrder", orderDto);
            return orderDto.getOrdSeq();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OrderDto> searchOrder() {
        return List.of();
    }
}
