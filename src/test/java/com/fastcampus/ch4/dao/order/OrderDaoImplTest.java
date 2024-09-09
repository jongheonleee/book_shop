package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dao.global.CodeDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/*
주문 생성 테스트

동작 과정
1. 주문 Dto 생성
2. 주문 insert
3. 주문이 제대로 생성되어있는지 검증하기
- 고객의 id 를 null 로 넣었을 떄 에러 발생
- 주문 상태 가 null 로 들어갔을 때 에러 발생


- 최초 주문 생성 시 금액 정보가 0으로 들어오지 않았을 때 => Service 단에서 체크한다.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderDaoImplTest {
    @Autowired
    OrderDao orderDao;
    @Autowired
    CodeDao codeDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int FAIL_CODE = 0; // Query Execute Fail

    final String USER_ID_NULL = null; // user id null
    final String CREATE_USER_ID = "createOrder"; // 주문 생성 시 사용할 userId
    final String SELECT_USER_ID = "selectOrder"; // 주문 조회 시 사용할 userId
    final String UPDATE_USER_ID = "updateOrder"; // 주문 변경 시 사용할 userId
    final String DELETE_USER_ID = "deleteOrder"; // 주문 삭제 시 사용할 userId

    final String ORD_CODE = "ord-stat-01";
    final String DELI_CODE = "deli-stat-01";
    final String PAY_CODE = "pay-stat-01";

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

    // 코드를 받아서 코드 id 를 반환하는 메서드
    public Integer getStatus(String code) {
        return codeDao.selectByCode(code).getCode_id();
    }

    /**
     * 주문 생성 테스트
     * === 성공해야하는 케이스
     * 1. 고객 id 가 포함되어 있는 orderDto 가 insert 되는 경우
     * === 실패해야하는 케이스
     * 1. 고객 id 가 null 인 orderDto 가 insert 되는 경우
     * 2. 최초 주문 생성 시 금액(배송비, 총 상품금액, 총 할인금액, 총 주문금액)이 0이 아닌 orderDto 가 insert 요청될 때
     *
     * 3. SQLIntegrityConstraintViolationException : 데이터베이스 무결성 위반
     * 4. DuplicateKeyException : 기본 키 중복 등으로 인한 insert 실패
     */
    @Test
    public void 단일주문생성하기 () throws Exception {
        // 1. 주문 삭제 (CREATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", CREATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 1. dto 생성하기
        OrderDto orderDto = createOrderDto(CREATE_USER_ID);

        // 2. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 3. 주문이 제대로 생성되어있는지 검증하기
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("ord_seq", orderDto.getOrd_seq());
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertNotNull(selectedList);

        OrderDto selectedOrderDto = selectedList.get(0);

        // 고객 아이디 동일 확인
        String selectedUserId = selectedOrderDto.getCust_id();
        assertEquals(CREATE_USER_ID, selectedUserId);

        // 주문 상태 동일 확인
        Integer selectedOrderStatus = selectedOrderDto.getOrd_stat();
        assertEquals(getStatus(ORD_CODE), selectedOrderStatus);

        // 배송 상태 동일 확인
        Integer selectedDeliveryStatus = selectedOrderDto.getDeli_stat();
        assertEquals(getStatus(DELI_CODE), selectedDeliveryStatus);

        // 결제 상태 동일 확인
        Integer selectedPaymentStatus = selectedOrderDto.getPay_stat();
        assertEquals(getStatus(PAY_CODE), selectedPaymentStatus);
    }

    /*
    테스트 이름 : 다중주문생성하기
    역할 : 여러 개의 주문을 생성하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
    2. 주문 Dto 생성
    3. 주문 insert
    4. 주문이 제대로 생성되어있는지 검증하기
    5. 10회 반복 (2-4)
    6. 생성 개수 비교
     */
    @Test
    public void 다중주문생성하기() {
        // 반복횟수
        int repeatCount = 10;

        // 1. 주문 삭제 (CREATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", CREATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);
        // 1-2. 삭제 후 개수 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 1-3. 전체 개수 확인
        List<OrderDto> allList = orderDao.selectOrderListByCondition(new HashMap<>());
        int beforeCount = allList.size();

        for (int i = 0; i < repeatCount; i++) {
            String userId = CREATE_USER_ID + i;
            // 2. 주문 Dto 생성
            OrderDto orderDto = createOrderDto(userId);

            // 3. 주문 insert
            int result = orderDao.insertOrder(orderDto);
            assertEquals(SUCCESS_CODE, result);

            // 4. 주문이 제대로 생성되어있는지 검증하기
            Map<String, Object> selectCondtion = new HashMap<>();
            selectCondtion.put("ord_seq", orderDto.getOrd_seq());
            List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondtion);
            assertNotNull(selectedList);

            OrderDto selectedOrderDto = selectedList.get(0);
            // 고객 아이디 동일 확인
            String selectedUserId = selectedOrderDto.getCust_id();
            assertEquals(userId, selectedUserId);

            // 주문 상태 동일 확인
            Integer selectedOrderStatus = selectedOrderDto.getOrd_stat();
            assertEquals(getStatus(ORD_CODE), selectedOrderStatus);

            // 배송 상태 동일 확인
            Integer selectedDeliveryStatus = selectedOrderDto.getDeli_stat();
            assertEquals(getStatus(DELI_CODE), selectedDeliveryStatus);

            // 결제 상태 동일 확인
            Integer selectedPaymentStatus = selectedOrderDto.getPay_stat();
            assertEquals(getStatus(PAY_CODE), selectedPaymentStatus);
        }

        // 6. 생성 개수 비교
        List<OrderDto> allListAfter = orderDao.selectOrderListByCondition(new HashMap<>());
        assertEquals(beforeCount + repeatCount, allListAfter.size());
    }

    /*
    테스트 이름 : 주문생성실패_고객아이디Null
    역할 : 고객 아이디가 null 인 주문을 생성하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
    2. 주문 Dto 생성
    3. 고객 아이디를 null 로 변경
    4. 주문 insert
    5. 주문 생성 실패 확인
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void 주문생성실패_고객아이디Null() {
        // 1. 주문 삭제 (CREATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", CREATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(CREATE_USER_ID);

        // 3. 고객 아이디를 null 로 변경
        orderDto.setCust_id(USER_ID_NULL);

        // 4. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(0, result);

        // 5. 주문 생성 실패 확인
        fail();
    }

    /*
    테스트 이름 : 주문단건조회
    역할 : 주문을 단건 조회하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (SELECT_USER_ID)
        - SELECT_USER_ID 로 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 단건 조회 (생성한 Dto의 ord_seq 로 조회)
        - 주문 단건 조회
    5. 주문 단건 조회 확인
        - cust_id 확인
     */
    @Test
    public void 주문단건조회() {
        // 1. 주문 삭제 (SELECT_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", SELECT_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        // 1-2. 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(SELECT_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 단건 조회
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("ord_seq", orderDto.getOrd_seq());
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertNotNull(selectedList);

        // 5. 주문 단건 조회 확인
        OrderDto selectedOrderDto = selectedList.get(0);
        String selectedUserId = selectedOrderDto.getCust_id();
        assertEquals(SELECT_USER_ID, selectedUserId);
    }

    /*
    테스트 이름 : 주문다중조회
    역할 : 주문을 다중 조회하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (SELECT_USER_ID)
        - SELECT_USER_ID 로 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
    3. 주문 insert (10회)
        - 주문 insert
        - 주문 insert 확인
    4. 주문 다중 조회
        - cust_id 로 주문 다중 조회
    5. 주문 다중 조회 확인
        - 생성한 주문의 cust_id 비교
     */
    @Test
    public void 주문다중조회() {
        // 반복횟수
        int repeatCount = 10;

        // 1. 주문 삭제 (SELECT_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", SELECT_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        // 1-2. 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        for (int i = 0; i < repeatCount; i++) {
            // 2. 주문 Dto 생성
            OrderDto orderDto = createOrderDto(SELECT_USER_ID);

            // 3. 주문 insert
            int result = orderDao.insertOrder(orderDto);
            assertEquals(SUCCESS_CODE, result);
        }

        // 4. 주문 다중 조회
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("cust_id", SELECT_USER_ID);
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertFalse(selectedList.isEmpty());

        // 5. 주문 다중 조회 확인
        for (OrderDto selectedOrderDto : selectedList) {
            String selectedUserId = selectedOrderDto.getCust_id();
            assertTrue(selectedUserId.contains(SELECT_USER_ID));
        }
    }

    /*
    테스트 이름 : 없는주문번호로조회
    역할 : 없는 주문 번호로 조회하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (SELECT_USER_ID)
        - SELECT_USER_ID 로 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 단건 조회 (생성한 Dto의 ord_seq + 1 로 조회)
        - 주문 단건 조회
    5. 주문 단건 조회 확인
        - 조회 결과가 없는지 확인
     */
    @Test
    public void 없는주문번호로조회() {
        // 1. 주문 삭제 (SELECT_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", SELECT_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        // 1-2. 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(SELECT_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 단건 조회
        Map<String, Object> selectCondition = new HashMap<>();
        Integer NON_EXIST_SEQ = orderDto.getOrd_seq() + 1;
        selectCondition.put("ord_seq", NON_EXIST_SEQ);
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertTrue(selectedList.isEmpty());
    }

    /*
    테스트 이름 : 주문상태변경
    역할 : 주문의 상태를 변경하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 상태 변경
        - 주문 상태 변경
        - 주문 상태 변경 확인
     */
    @Test
    public void 주문_주문상태변경() {
        String updatedOrdCode = "ord-stat-02";

        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 상태 변경
        Map<String, Object> updateCondition = new HashMap<>();
        updateCondition.put("ord_seq", orderDto.getOrd_seq());
        updateCondition.put("ord_stat", getStatus(updatedOrdCode));
        updateCondition.put("deli_stat", orderDto.getDeli_stat());
        updateCondition.put("pay_stat", orderDto.getPay_stat());
        updateCondition.put("up_id", UPDATE_USER_ID);
        int updateResult = orderDao.updateOrderStatus(updateCondition);
        assertEquals(SUCCESS_CODE, updateResult);

        // 4-2. 주문 상태 변경 확인
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(updateCondition);
        assertNotNull(selectedList);

        OrderDto selectedOrderDto = selectedList.get(0);
        Integer selectedOrderStatus = selectedOrderDto.getOrd_stat();
        assertEquals(getStatus(updatedOrdCode), selectedOrderStatus);
    }

    /*
    테스트 이름 : 주문_주문상태변경실패_주문상태Null
    역할 : 주문의 상태를 null 로 변경하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 상태 변경
        - 주문 상태를 null 로 변경
        - 주문 상태 변경 실패 확인
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void 주문_주문상태변경싱패_주문상태Null() {
        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 상태 변경
        Map<String, Object> updateCondition = new HashMap<>();
        updateCondition.put("ord_seq", orderDto.getOrd_seq());
        updateCondition.put("ord_stat", null);
        updateCondition.put("deli_stat", orderDto.getDeli_stat());
        updateCondition.put("pay_stat", orderDto.getPay_stat());
        updateCondition.put("up_id", UPDATE_USER_ID);
        int updateResult = orderDao.updateOrderStatus(updateCondition);
        assertEquals(0, updateResult);

        // 4-2. 주문 상태 변경 확인
        fail();
    }

    /*
    테스트 이름 : 주문_배송상태변경
    역할 : 주문의 배송 상태를 변경하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 배송 상태 변경
        - 주문 배송 상태 변경
        - 주문 배송 상태 변경 확인
     */
    @Test
    public void 주문_배송상태변경() {
        String updatedDeliCode = "deli-stat-02";

        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 배송 상태 변경
        Map<String, Object> updateCondition = new HashMap<>();
        updateCondition.put("ord_seq", orderDto.getOrd_seq());
        updateCondition.put("ord_stat", orderDto.getOrd_stat());
        updateCondition.put("deli_stat", getStatus(updatedDeliCode));
        updateCondition.put("pay_stat", orderDto.getPay_stat());
        updateCondition.put("up_id", UPDATE_USER_ID);
        int updateResult = orderDao.updateOrderStatus(updateCondition);
        assertEquals(SUCCESS_CODE, updateResult);

        // 4-2. 주문 배송 상태 변경 확인
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(updateCondition);
        assertNotNull(selectedList);

        OrderDto selectedOrderDto = selectedList.get(0);
        Integer selectedDeliveryStatus = selectedOrderDto.getDeli_stat();
        assertEquals(getStatus(updatedDeliCode), selectedDeliveryStatus);
    }


    /*
    테스트 이름 : 주문_배송상태변경실패_배송상태Null
    역할 : 주문의 배송 상태를 null 로 변경하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 배송 상태 변경
        - 주문 배송 상태를 null 로 변경
        - 주문 배송 상태 변경 실패 확인
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void 주문_배송상태변경실패_배송상태Null() {
        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 배송 상태 변경
        Map<String, Object> updateCondition = new HashMap<>();
        updateCondition.put("ord_seq", orderDto.getOrd_seq());
        updateCondition.put("ord_stat", orderDto.getOrd_stat());
        updateCondition.put("deli_stat", null);
        updateCondition.put("pay_stat", orderDto.getPay_stat());
        updateCondition.put("up_id", UPDATE_USER_ID);
        int updateResult = orderDao.updateOrderStatus(updateCondition);
        assertEquals(0, updateResult);

        // 4-2. 주문 배송 상태 변경 확인
        fail();
    }

    /*
    테스트 이름 : 주문_결제상태변경
    역할 : 주문의 결제 상태를 변경하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 결제 상태 변경
        - 주문 결제 상태 변경
        - 주문 결제 상태 변경 확인
     */
    @Test
    public void 주문_결제상태변경() {
        String updatedPayCode = "pay-stat-02";

        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 결제 상태 변경
        Map<String, Object> updateCondition = new HashMap<>();
        updateCondition.put("ord_seq", orderDto.getOrd_seq());
        updateCondition.put("ord_stat", orderDto.getOrd_stat());
        updateCondition.put("deli_stat", orderDto.getDeli_stat());
        updateCondition.put("pay_stat", getStatus(updatedPayCode));
        updateCondition.put("up_id", UPDATE_USER_ID);
        int updateResult = orderDao.updateOrderStatus(updateCondition);
        assertEquals(SUCCESS_CODE, updateResult);

        // 4-2. 주문 결제 상태 변경 확인
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(updateCondition);
        assertNotNull(selectedList);

        OrderDto selectedOrderDto = selectedList.get(0);
        Integer selectedPaymentStatus = selectedOrderDto.getPay_stat();
        assertEquals(getStatus(updatedPayCode), selectedPaymentStatus);
    }

    /*
    테스트 이름 : 주문_결제상태변경실패_결제상태Null
    역할 : 주문의 결제 상태를 null 로 변경하는 테스트
    매개변수 : 없음
    진행과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 결제 상태 변경
        - 주문 결제 상태를 null 로 변경
        - 주문 결제 상태 변경 실패 확인
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void 주문_결제상태변경실패_결제상태Null() {
        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 결제 상태 변경
        Map<String, Object> updateCondition = new HashMap<>();
        updateCondition.put("ord_seq", orderDto.getOrd_seq());
        updateCondition.put("ord_stat", orderDto.getOrd_stat());
        updateCondition.put("deli_stat", orderDto.getDeli_stat());
        updateCondition.put("pay_stat", null);
        updateCondition.put("up_id", UPDATE_USER_ID);
        int updateResult = orderDao.updateOrderStatus(updateCondition);
        assertEquals(0, updateResult);

        // 4-2. 주문 결제 상태 변경 확인
        fail();
    }

    /*
    테스트 이름 : 주문_금액변경
    역할 : 주문의 금액을 변경하는 테스트 (total_prod_pric, total_disc_pric, total_ord_pric)
    매개변수 : 없음
    진행 과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
    4. 주문 금액 변경
        - total_prod_pric, total_disc_pric, total_ord_pric 변경
        - 주문 update
    5. 변경 사항 확인
        - total_prod_pric, total_disc_pric, total_ord_pric 확인
     */
    @Test
    public void 주문_금액변경() {
        Integer updated_delivery_fee = 3000;
        Integer updated_total_disc_pric = 5000;
        Integer updated_total_ord_pric = 10000;
        Integer updated_total_prod_pric = 16000;

        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        // 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int result = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, result);

        // 4. 주문 금액 변경
        Map<String, Object> priceInfo = new HashMap<>();
        priceInfo.put("ord_seq", orderDto.getOrd_seq());
        priceInfo.put("delivery_fee", updated_delivery_fee);
        priceInfo.put("total_disc_pric", updated_total_disc_pric);
        priceInfo.put("total_ord_pric", updated_total_ord_pric);
        priceInfo.put("total_prod_pric", updated_total_prod_pric);
        priceInfo.put("up_id", UPDATE_USER_ID);

        // 주문 update
        int updateResult = orderDao.updateOrderPriceInfo(priceInfo);
        assertEquals(SUCCESS_CODE, updateResult);

        // update 확인
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("ord_seq", orderDto.getOrd_seq());
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertNotNull(selectedList);

        // 5. 변경 사항 확인
        OrderDto updatedOrderDto =  selectedList.get(0);
        assertEquals(updated_delivery_fee, updatedOrderDto.getDelivery_fee());
        assertEquals(updated_total_disc_pric, updatedOrderDto.getTotal_disc_pric());
        assertEquals(updated_total_ord_pric, updatedOrderDto.getTotal_ord_pric());
        assertEquals(updated_total_prod_pric, updatedOrderDto.getTotal_prod_pric());
    }

    /*
    테스트 이름 : 주문_이미삭제한주문변경
    역할 : 이미 삭제한 주문을 변경하는 테스트
    매개변수 : 없음
    진행 과정
    1. 주문 삭제 (CREATE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
        - 주문 Dto 정보 저장
    4. 주문 삭제
    5. 주문 금액 변경
        - total_prod_pric, total_disc_pric, total_ord_pric 변경
        - 주문 update
    6. 변경 사항 확인
        - total_prod_pric, total_disc_pric, total_ord_pric 확인
     */
    @Test
    public void 주문_이미삭제한주문변경() {
        Integer updated_delivery_fee = 3000;
        Integer updated_total_disc_pric = 5000;
        Integer updated_total_ord_pric = 10000;
        Integer updated_total_prod_pric = 16000;

        // 1. 주문 삭제 (UPDATE_USER_ID)
        Map<String, Object> initDeleteCondition = new HashMap<>();
        initDeleteCondition.put("cust_id", UPDATE_USER_ID);
        orderDao.deleteOrderByCondition(initDeleteCondition);

        // 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(initDeleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 3. 주문 insert
        int insertResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertResult);

        // 4. 주문 삭제
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("ord_seq", orderDto.getOrd_seq());
        int deleteResult = orderDao.deleteOrderByCondition(deleteCondition);
        assertEquals(SUCCESS_CODE, deleteResult);

        // 5. 주문 금액 변경
        Map<String, Object> priceInfo = new HashMap<>();
        priceInfo.put("ord_seq", orderDto.getOrd_seq());
        priceInfo.put("delivery_fee", updated_delivery_fee);
        priceInfo.put("total_disc_pric", updated_total_disc_pric);
        priceInfo.put("total_ord_pric", updated_total_ord_pric);
        priceInfo.put("total_prod_pric", updated_total_prod_pric);
        priceInfo.put("up_id", UPDATE_USER_ID);

        // 주문 update
        int updateResult = orderDao.updateOrderPriceInfo(priceInfo);
        assertEquals(FAIL_CODE, updateResult);

        // update 확인
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("ord_seq", orderDto.getOrd_seq());
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertTrue(selectedList.isEmpty());
    }

    /*
    메서드 이름 : 주문_단건삭제
    역할 : 주문 단건 삭제
    매개변수 : 없음
    진행 과정
    1. 주문 삭제(DELETE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
        - 주문 Dto 정보 저장
        - 주문 개수 저장
    4. 주문 삭제
    5. 삭제 결과 확인
        - 주문 조회
        - 주문 개수 확인
     */
    @Test
    public void 주문_단건삭제() {
        // 1. 주문 삭제 (DELETE_USER_ID)
        Map<String, Object> initDeleteCondition = new HashMap<>();
        initDeleteCondition.put("cust_id", DELETE_USER_ID);
        orderDao.deleteOrderByCondition(initDeleteCondition);

        // 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(initDeleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(DELETE_USER_ID);

        // 3. 주문 insert
        int insertResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertResult);

        // 3-2. 주문 insert 확인
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("ord_seq", orderDto.getOrd_seq());
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertNotNull(selectedList);

        // 3-3. 주문 Dto 정보 저장
        OrderDto selectedOrderDto = selectedList.get(0);

        // 3-4. 주문 개수 저장
        List<OrderDto> allList = orderDao.selectOrderListByCondition(new HashMap<>());
        int beforeCount = allList.size();

        // 4. 주문 삭제
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("ord_seq", orderDto.getOrd_seq());
        int deleteResult = orderDao.deleteOrderByCondition(deleteCondition);
        assertEquals(SUCCESS_CODE, deleteResult);

        // 5. 삭제 결과 확인
        List<OrderDto> afterList = orderDao.selectOrderListByCondition(new HashMap<>());
        assertEquals(beforeCount - 1, afterList.size());
    }

    /*
    메서드 이름 : 주문_다중삭제
    역할 : 주문 다중 삭제
    매개변수 : 없음
    진행 과정
    1. 주문 삭제 (DELETE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert (10회)
        - 주문 insert
        - 주문 insert 확인
        - 주문 개수 저장
    4. 주문 삭제
    5. 삭제 결과 확인
        - 주문 조회
        - 주문 개수 확인
     */
    @Test
    public void 주문_다중삭제() {
        // 1. 주문 삭제 (DELETE_USER_ID)
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", DELETE_USER_ID);
        orderDao.deleteOrderByCondition(deleteCondition);

        // 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(deleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(DELETE_USER_ID);

        // 3. 주문 insert
        int repeatCount = 10;
        for (int i = 0; i < repeatCount; i++) {
            // 3-1. 주문 insert
            int insertResult = orderDao.insertOrder(orderDto);
            assertEquals(SUCCESS_CODE, insertResult);
        }

        // 3-2. 주문 insert 확인
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("cust_id", DELETE_USER_ID);
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertEquals(selectedList.size(), repeatCount);

        // 3-3. 주문 개수 저장
        List<OrderDto> allList = orderDao.selectOrderListByCondition(new HashMap<>());
        int beforeCount = allList.size();

        // 4. 주문 삭제
        int deleteResult = orderDao.deleteOrderByCondition(deleteCondition);
        assertEquals(repeatCount, deleteResult);

        // 5. 삭제 결과 확인
        List<OrderDto> afterList = orderDao.selectOrderListByCondition(new HashMap<>());
        assertEquals(beforeCount - repeatCount, afterList.size());
    }

    /*
    메서드 이름 : 주문_삭제실패_삭제조건없는경우
    역할 : 삭제 조건이 없는 경우 삭제 실패
    매개변수 : 없음
    진행 과정
    1. 주문 삭제 (DELETE_USER_ID)
        - 주문 삭제
        - 주문 삭제 확인
    2. 주문 Dto 생성
        - 주문 Dto 생성
    3. 주문 insert
        - 주문 insert
        - 주문 insert 확인
        - 주문 개수 저장
    4. 주문 삭제
    5. 삭제 결과 확인
        - 주문 조회
        - 주문 개수 확인
     */
    @Test
    public void 주문_삭제실패_삭제조건없는경우 () {
        // 1. 주문 삭제 (DELETE_USER_ID)
        Map<String, Object> initDeleteCondition = new HashMap<>();
        initDeleteCondition.put("cust_id", DELETE_USER_ID);
        orderDao.deleteOrderByCondition(initDeleteCondition);

        // 삭제 확인
        List<OrderDto> deletedList = orderDao.selectOrderListByCondition(initDeleteCondition);
        assertEquals(0, deletedList.size());

        // 2. 주문 Dto 생성
        OrderDto orderDto = createOrderDto(DELETE_USER_ID);

        // 3. 주문 insert
        int insertResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, insertResult);

        // 3-2. 주문 insert 확인
        Map<String, Object> selectCondition = new HashMap<>();
        selectCondition.put("ord_seq", orderDto.getOrd_seq());
        List<OrderDto> selectedList = orderDao.selectOrderListByCondition(selectCondition);
        assertFalse(selectedList.isEmpty());

        // 3-3. 주문 개수 저장
        List<OrderDto> allList = orderDao.selectOrderListByCondition(new HashMap<>());
        int beforeCount = allList.size();

        // 4. 주문 삭제 (조건없음)
        int deleteResult = orderDao.deleteOrderByCondition(new HashMap<>());
        assertEquals(0, deleteResult);

        // 5. 삭제 결과 확인
        List<OrderDto> afterList = orderDao.selectOrderListByCondition(new HashMap<>());
        assertEquals(beforeCount, afterList.size());
    }
}
