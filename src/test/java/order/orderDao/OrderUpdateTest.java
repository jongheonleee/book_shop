package order.orderDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static com.fastcampus.ch4.dto.order.OrderStatus.PAYMENT_DONE;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

/*
order update test

실행 과정
1. 주문을 생성한다.
2. 주문을 변경한다.
3. 변경한 주문의 값이 제대로 변경 되어 있는지 확인한다.
(속성, 시스템 컬럼)

실패하는 케이스
1. 없는 주문을 변경하려고 시도하는 경우
2. not null 컬럼에 null 을 넣어줄 경우

경우의 수
1. 생성한 주문을 변경한다.
2. 이미 생성되어 있는 주문을 변경한다.

주의사항
수정 시 up_date 는 service 단에서 수정했을 때 검증한다.

 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderUpdateTest {
    @Autowired
    OrderDao orderDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final String USER_ID_NULL = null; // user id null
    final String CREATE_USER_ID = "createUser"; // 주문 생성 시 사용할 userId
    final String UPDATE_USER_ID = "updateTestUser"; // update 시 사용할 userId

    @Test
    public void 주문변경_update_order() throws Exception {
        // give
        // 1. 주문을 생성한다.
        OrderDto orderDto = OrderDtoFactory.getInstance(CREATE_USER_ID);
        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDto);
        assertNotNull(createdOrderSeq);

        // 2. 생성한 주문의 값을 저장한다.
        OrderDto createdOrderDto = orderDao.selectById(createdOrderSeq);
        assertNotNull(createdOrderDto);

//        String createdUserId = createdOrderDto.getUserId();
//        int createdTotalProdPric = createdOrderDto.getTotal_prod_pric();
//        int createdTotalBenePric = createdOrderDto.getTotal_bene_pric();
//        int createdTotalOrdPric = createdOrderDto.getTotal_ord_pric();
//        String createdOrdStat = createdOrderDto.getOrd_stat();
//        String createdUpId = createdOrderDto.getUp_id();
        Date createdUpDate = createdOrderDto.getUp_date();

        // do
        // 3. 주문을 변경한다.
        int changeTotalProdPric = 16800;
        int changeTotalBenePric = 1680;
        int changeDeliveryFee = 3000;
        int changeTotalOrdPric = changeTotalProdPric - changeTotalBenePric + changeDeliveryFee;
        String changeOrdStat = PAYMENT_DONE.getOrd_stat();

        createdOrderDto.setTotal_prod_pric(changeTotalProdPric);
        createdOrderDto.setTotal_bene_pric(changeTotalBenePric);
        createdOrderDto.setDelivery_fee(changeDeliveryFee);
        createdOrderDto.setTotal_ord_pric(changeTotalOrdPric);
        createdOrderDto.setOrd_stat(changeOrdStat);

        int updateResult = orderDao.update(createdOrderDto, UPDATE_USER_ID);
        assertEquals(SUCCESS_CODE, updateResult);

        // assert
        // 4. 변경한 주문의 값이 제대로 변경 되어 있는지 확인한다.
        OrderDto updatedOrderDto = orderDao.selectById(createdOrderSeq);
        Integer updatedOrdSeq = updatedOrderDto.getOrd_seq();
        assertNotNull(updatedOrderDto);
        assertTrue(createdOrderSeq.equals(updatedOrdSeq)); // seq 일치

        // 총 상품 금액
        int updatedTotalProdPric = updatedOrderDto.getTotal_prod_pric();
        assertEquals(changeTotalProdPric, updatedTotalProdPric);

        // 총 할인 금액
        int updatedTotalBenePric = updatedOrderDto.getTotal_bene_pric();
        assertEquals(changeTotalBenePric, updatedTotalBenePric);

        // 배송비
        int updatedDeliveryFee = updatedOrderDto.getDelivery_fee();
        assertEquals(changeDeliveryFee, updatedDeliveryFee);

        // 총 주문 금액
        int updatedTotalOrdPric = updatedOrderDto.getTotal_ord_pric();
        assertEquals(changeTotalOrdPric, updatedTotalOrdPric);

        // 주문 상태
        String updatedOrdStat = updatedOrderDto.getOrd_stat();
        assertEquals(changeOrdStat, updatedOrdStat);

        // 최근 수정자 id
        String updatedUpId = updatedOrderDto.getUp_id();
        assertEquals(updatedUpId, UPDATE_USER_ID);

        // 최근 수정 일자
        Date updatedUpDate = updatedOrderDto.getUp_date();
        assertNotEquals(createdUpDate, updatedUpDate);

        // 5. 주문 삭제
        int deleteResult = orderDao.deleteById(updatedOrdSeq);
        assertEquals(SUCCESS_CODE, deleteResult);
    }

//    @Test
//    public void 주문변경_update_시스템컬럼 () throws Exception {
//        // 1. 이미 생성되어 있는 주문 리스트를 조회한다.
//        List<OrderDto> orderDtoList = orderDao.selectAll("ord_seq", false);
//
//        // 이미 생성되어 있는 주문이 없다면 테스트를 실행하지 않는다.
//        assumeTrue(orderDtoList.size() > 0);
//
//        // 무작위 선택
//        int randomIndex = (int) (Math.random() * orderDtoList.size());
//
//        OrderDto firstOrderDto  = orderDtoList.get(randomIndex);
//        Integer firstOrderDtoSeq = firstOrderDto.getOrd_seq();
//
//        // 2. up_date 저장
//        Date firstOrderDtoUpDate = firstOrderDto.getUp_date();
//
//        // 3. order update
//        int updateResult = orderDao.updateOrder(firstOrderDto, UPDATE_USER_ID);
//        assertEquals(SUCCESS_CODE, updateResult);
//
//        OrderDto updatedOrderDto = orderDao.selectOrderById(firstOrderDtoSeq);
//        Integer updatedOrdSeq = updatedOrderDto.getOrd_seq();
//        assertNotNull(updatedOrderDto);
//        assertEquals(firstOrderDtoSeq, updatedOrdSeq);
//        Date updatedOrdUpDate = updatedOrderDto.getUp_date();
//
//        // 4. 검증
//        assertNotEquals(firstOrderDtoUpDate, updatedOrdUpDate);
//    }

    @Test
    public void 주문변경_update_noOrder() throws Exception {
        // give
        // 1. 주문을 생성한다.
        OrderDto orderDto = OrderDtoFactory.getInstance(CREATE_USER_ID);
        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDto);
        OrderDto createdOrderDto = orderDao.selectById(createdOrderSeq);

        // do
        // 2. 주문을 삭제한다.
        int deleteResult = orderDao.deleteById(createdOrderSeq);
        assertEquals(SUCCESS_CODE, deleteResult);

        // 3. 주문 update
        int changeTotalProdPric = 16800;
        int changeTotalBenePric = 1680;
        int changeDeliveryFee = 3000;
        int changeTotalOrdPric = changeTotalProdPric - changeTotalBenePric + changeDeliveryFee;
        String changeOrdStat = PAYMENT_DONE.getOrd_stat();

        createdOrderDto.setTotal_prod_pric(changeTotalProdPric);
        createdOrderDto.setTotal_bene_pric(changeTotalBenePric);
        createdOrderDto.setDelivery_fee(changeDeliveryFee);
        createdOrderDto.setTotal_ord_pric(changeTotalOrdPric);
        createdOrderDto.setOrd_stat(changeOrdStat);

        int updateResult = orderDao.update(createdOrderDto, UPDATE_USER_ID);

        // assert
        // 4. update 결과 == 0
        assertTrue(updateResult != SUCCESS_CODE);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 주문변경_update_UpId_null() throws Exception {
        // give
        // 1. 주문을 생성한다.
        OrderDto orderDto = OrderDtoFactory.getInstance(CREATE_USER_ID);
        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDto);
        assertNotNull(createdOrderSeq);

        OrderDto createdOrderDto = orderDao.selectById(createdOrderSeq);
        assertNotNull(createdOrderDto);

        // do
        // 3. 주문 update
        int updateResult = orderDao.update(createdOrderDto, USER_ID_NULL);
        assertEquals(SUCCESS_CODE, updateResult);

        // assert
        // 4. fail
        fail();
    }
}
