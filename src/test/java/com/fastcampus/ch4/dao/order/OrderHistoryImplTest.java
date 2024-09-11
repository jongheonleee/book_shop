package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dao.global.CodeDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderHistoryDto;
import com.fastcampus.ch4.utils.GenerateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderHistoryImplTest {
    @Autowired
    private OrderHistoryDao orderHistoryDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CodeDao codeDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int FAIL_CODE = 0; // Query Execute Fail

    final String USER_ID_NULL = null; // user id null
    final String TEST_USER_ID = "OrderHistory"; // 주문 생성 시 사용할 userId
    final String TEST_USER_ID2 = "OrderHistory2"; // 주문 생성 시 사용할 userId

    final String CHG_REASON = "주문 변경 이력 테스트";

    final String ORD_CODE = "ord-stat-01";
    final String DELI_CODE = "deli-stat-01";
    final String PAY_CODE = "pay-stat-01";

    // 코드를 받아서 코드 id 를 반환하는 메서드
    public Integer getStatus(String code) {
        return codeDao.selectByCode(code).getCode_id();
    }

    /*
이름 : orderDtoCreate
역할 : OrderDto 클래스의 인스턴스를 생성하여 초기화해 주는 메서드
매개변수
    String userId : 주문을 생성할 사용자의 아이디
반환값 : OrderDto 클래스의 인스턴스
 */
    public OrderDto createOrderDto(String custId) {
        OrderDto orderDto = new OrderDto();
        orderDto.setCust_id(custId);
        orderDto.setOrd_stat(getStatus(ORD_CODE));
        orderDto.setDeli_stat(getStatus(DELI_CODE));
        orderDto.setPay_stat(getStatus(PAY_CODE));
        orderDto.setReg_id(custId);
        orderDto.setUp_id(custId);
        return orderDto;
    }

    /*
    메서드 이름 : createOrderHistoryDto
    기능 : OrderDto를 받아서 OrderHistoryDto로 변환한다.
    매개변수
        OrderDto orderDto : OrderDto 인스턴스
    반환값 : OrderHistoryDto 인스턴스
     */
    public OrderHistoryDto createOrderHistoryDto(OrderDto orderDto) {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrd_seq(orderDto.getOrd_seq());
        orderHistoryDto.setChg_start_date(GenerateTime.getCurrentTime());
        orderHistoryDto.setChg_end_date(GenerateTime.getMaxTime());
        orderHistoryDto.setChg_reason(CHG_REASON);

        orderHistoryDto.setOrd_stat(orderDto.getOrd_stat());
        orderHistoryDto.setDeli_stat(orderDto.getDeli_stat());
        orderHistoryDto.setPay_stat(orderDto.getPay_stat());
        orderHistoryDto.setDelivery_fee(orderDto.getDelivery_fee());
        orderHistoryDto.setTotal_prod_pric(orderDto.getTotal_prod_pric());
        orderHistoryDto.setTotal_disc_pric(orderDto.getTotal_disc_pric());
        orderHistoryDto.setTotal_ord_pric(orderDto.getTotal_ord_pric());
        orderHistoryDto.setReg_id(orderDto.getCust_id());
        orderHistoryDto.setUp_id(orderDto.getCust_id());
        return orderHistoryDto;
    }

    /*
    @Before
    진행 과정
    1. Order 조회 (TEST_USER_ID)
    2. 조회한 Order 를 순회하면서 OrderHistory 삭제
    3. Order 삭제
     */
    @Before
    public void initOrderHistory() {
        // 1. Order 조회 (TEST_USER_ID)
        Map<String, Object> initialCondition = new HashMap<>();
        initialCondition.put("cust_id", TEST_USER_ID);
        List<OrderDto> orderDtoList = orderDao.selectOrderByCondition(initialCondition);

        for (OrderDto orderDto : orderDtoList) {
            // 2. 조회한 Order 에 따른 OrderHistory 조회
            Map<String, Object> orderHistoryCondition = new HashMap<>();
            orderHistoryCondition.put("ord_seq", orderDto.getOrd_seq());
            orderHistoryDao.deleteOrderHistoryByCondition(orderHistoryCondition);

            // 3. Order 삭제
            Map<String, Object> orderCondition = new HashMap<>();
            orderCondition.put("ord_seq", orderDto.getOrd_seq());
            int deleteOrderResult = orderDao.deleteOrderByCondition(orderCondition);
            assertTrue(SUCCESS_CODE <= deleteOrderResult);
        }
    }

    /*
    메서드 이름 : 주문이력_등록
    기능 : 주문이력을 등록한다.
    매개변수 : 없음
    반환값 : 없음
    진행 과정
    1. Order 등록
        - OrderDto 생성
        - OrderDao.insertOrder
    2. OrderHistory 등록
        - OrderHistoryDto 생성
        - OrderHistoryDao.insertOrderHistory
    3. OrderHistory 조회
        - OrderHistoryDao.selectOrderHistoryByCondition
    4. OrderHistory 조회 결과 확인
     */
    @Test
    public void 주문이력_등록() {
        // 1. Order 등록
        OrderDto orderDto = createOrderDto(TEST_USER_ID);

        // 1-1. OrderDao.insertOrder
        int insertOrderResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertOrderResult);

        // 2. OrderHistory 등록
        OrderHistoryDto orderHistoryDto = createOrderHistoryDto(orderDto);

        // 2-1. OrderHistoryDao.insertOrderHistory
        int insertOrderHistoryResult = orderHistoryDao.insertOrderHistory(orderHistoryDto);
        assertEquals(SUCCESS_CODE, insertOrderHistoryResult);

        // 3. OrderHistory 조회
        Map<String, Object> orderHistoryCondition = new HashMap<>();
        orderHistoryCondition.put("ord_hist_seq", orderHistoryDto.getOrd_hist_seq());
        List<OrderHistoryDto> orderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(orderHistoryCondition);
        assertFalse(orderHistoryDtoList.isEmpty());

        // 4. OrderHistory 조회 결과 확인
        OrderHistoryDto selectedOrderHistoryDto = orderHistoryDtoList.get(0);
        assertEquals(orderHistoryDto, selectedOrderHistoryDto);
    }

    /*
    메서드 이름 : 주문이력_조회
    기능 : 주문이력을 조회한다.
    매개변수 : 없음
    반환값 : 없음
    진행 과정
    1. Order 등록
        - OrderDto 생성
        - OrderDao.insertOrder
    2. OrderHistory 등록
        - OrderHistoryDto 생성
        - OrderHistoryDao.insertOrderHistory
    3. OrderHistory 조회
        - OrderHistoryDao.selectOrderHistoryByCondition
    4. OrderHistory 조회 결과 확인
     */
    @Test
    public void 주문이력_조회() {
        // 1. Order 등록
        OrderDto orderDto = createOrderDto(TEST_USER_ID);

        // 1-1. OrderDao.insertOrder
        int insertOrderResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertOrderResult);

        // 2. OrderHistory 등록
        OrderHistoryDto orderHistoryDto = createOrderHistoryDto(orderDto);

        // 2-1. OrderHistoryDao.insertOrderHistory
        int insertOrderHistoryResult = orderHistoryDao.insertOrderHistory(orderHistoryDto);
        assertEquals(SUCCESS_CODE, insertOrderHistoryResult);

        // 3. OrderHistory 조회
        Map<String, Object> orderHistoryCondition = new HashMap<>();
        orderHistoryCondition.put("ord_hist_seq", orderHistoryDto.getOrd_hist_seq());
        List<OrderHistoryDto> orderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(orderHistoryCondition);
        assertFalse(orderHistoryDtoList.isEmpty());

        // 4. OrderHistory 조회 결과 확인
        OrderHistoryDto selectedOrderHistoryDto = orderHistoryDtoList.get(0);
        assertEquals(orderHistoryDto, selectedOrderHistoryDto);
    }
    
    /*
    메서드 이름 : 주문이력_변경
    기능 : 주문이력을 변경한다.
    매개변수 : 없음
    반환값 : 없음
    진행 과정
    1. Order 등록
        - OrderDto 생성
        - OrderDao.insertOrder
    2. OrderHistory 등록
        - OrderHistoryDto 생성
        - OrderHistoryDao.insertOrderHistory
    3. OrderHistory 조회
        - OrderHistoryDao.selectOrderHistoryByCondition
    4. OrderHisotry 변경
        - OrderHistoryDto 변경
        - OrderHistoryDao.updateOrderHistory
    5. OrderHisotry 변경 결과 확인
        - chg_start_date 확인
        - chg_end_date 확인
        - chg_reason 확인
     */
    @Test
    public void 주문이력_변경() {
        // 1. Order 등록
        OrderDto orderDto = createOrderDto(TEST_USER_ID);

        // 1-1. OrderDao.insertOrder
        int insertOrderResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertOrderResult);

        // 2. OrderHistory 등록
        OrderHistoryDto orderHistoryDto = createOrderHistoryDto(orderDto);

        // 2-1. OrderHistoryDao.insertOrderHistory
        int insertOrderHistoryResult = orderHistoryDao.insertOrderHistory(orderHistoryDto);
        assertEquals(SUCCESS_CODE, insertOrderHistoryResult);

        // 3. OrderHistory 조회
        Map<String, Object> orderHistoryCondition = new HashMap<>();
        orderHistoryCondition.put("ord_hist_seq", orderHistoryDto.getOrd_hist_seq());
        List<OrderHistoryDto> orderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(orderHistoryCondition);
        assertFalse(orderHistoryDtoList.isEmpty());

        // 4. OrderHisotry 변경
        String chgReason = "주문 변경 이력 테스트";
        String chgStartDate = GenerateTime.getCurrentTime();
        String chgEndDate = GenerateTime.getMaxTime();
        OrderHistoryDto selectedOrderHistoryDto = orderHistoryDtoList.get(0);
        selectedOrderHistoryDto.setChg_reason(chgReason);
        selectedOrderHistoryDto.setChg_start_date(chgStartDate);
        selectedOrderHistoryDto.setChg_end_date(chgEndDate);
        selectedOrderHistoryDto.setUp_id(TEST_USER_ID2);

        // 4-1. OrderHistoryDao.updateOrderHistory
        int updateOrderHistoryResult = orderHistoryDao.updateOrderHistory(selectedOrderHistoryDto);
        assertEquals(SUCCESS_CODE, updateOrderHistoryResult);

        // 5. OrderHisotry 변경 결과 확인
        Map<String, Object> updatedOrderHistoryCondition = new HashMap<>();
        updatedOrderHistoryCondition.put("ord_hist_seq", selectedOrderHistoryDto.getOrd_hist_seq());
        List<OrderHistoryDto> updatedOrderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(updatedOrderHistoryCondition);
        assertFalse(updatedOrderHistoryDtoList.isEmpty());

        OrderHistoryDto updatedOrderHistoryDto = updatedOrderHistoryDtoList.get(0);
        assertEquals(selectedOrderHistoryDto, updatedOrderHistoryDto);
    }

    /*
    메서드 이름 : 주문이력_삭제
    기능 : 주문이력을 삭제한다.
    매개변수 : 없음
    반환값 : 없음
    진행 과정
    1. Order 등록
        - OrderDto 생성
        - OrderDao.insertOrder
    2. OrderHistory 등록
        - OrderHistoryDto 생성
        - OrderHistoryDao.insertOrderHistory
    3. OrderHistory 조회
        - OrderHistoryDao.selectOrderHistoryByCondition
    4. OrderHistory 삭제
        - OrderHistoryDao.deleteOrderHistoryByCondition
    5. OrderHistory 삭제 결과 확인
     */
    @Test
    public void 주문이력_삭제() {
        // 1. Order 등록
        OrderDto orderDto = createOrderDto(TEST_USER_ID);

        // 1-1. OrderDao.insertOrder
        int insertOrderResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertOrderResult);

        // 2. OrderHistory 등록
        OrderHistoryDto orderHistoryDto = createOrderHistoryDto(orderDto);

        // 2-1. OrderHistoryDao.insertOrderHistory
        int insertOrderHistoryResult = orderHistoryDao.insertOrderHistory(orderHistoryDto);
        assertEquals(SUCCESS_CODE, insertOrderHistoryResult);

        // 3. OrderHistory 조회
        Map<String, Object> orderHistoryCondition = new HashMap<>();
        orderHistoryCondition.put("ord_hist_seq", orderHistoryDto.getOrd_hist_seq());
        List<OrderHistoryDto> orderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(orderHistoryCondition);
        assertFalse(orderHistoryDtoList.isEmpty());

        // 4. OrderHistory 삭제
        int deleteOrderHistoryResult = orderHistoryDao.deleteOrderHistoryByCondition(orderHistoryCondition);
        assertEquals(SUCCESS_CODE, deleteOrderHistoryResult);

        // 5. OrderHistory 삭제 결과 확인
        List<OrderHistoryDto> deletedOrderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(orderHistoryCondition);
        assertTrue(deletedOrderHistoryDtoList.isEmpty());
    }
}
