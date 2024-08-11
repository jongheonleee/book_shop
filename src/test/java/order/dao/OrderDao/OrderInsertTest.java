package order.dao.OrderDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.model.order.OrderConstants;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

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
public class OrderInsertTest {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderDtoFactory orderDtoFactory;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final String USER_ID_NULL = null; // user id null
    final String CREATE_USER_ID = "createUser"; // 주문 생성 시 사용할 userId

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
    public void 주문생성_insert_single() throws Exception {
        // 1. dto 생성하기
        OrderDto orderDto = orderDtoFactory.create(CREATE_USER_ID);

        // 2. insert
        Integer createdOrderSeq = orderDao.insertAndReturnSeq(orderDto);
        assertNotNull(createdOrderSeq);

        // 3. 생성한 order 조회하기
        OrderDto selectedOrderDto = orderDao.selectBySeq(createdOrderSeq);
        assertNotNull(selectedOrderDto);

        // 4. 생성된 order 값 비교
        String selectedOrderRegId = selectedOrderDto.getReg_id();
        assertEquals(selectedOrderRegId, CREATE_USER_ID);

        String selectedOrderStatus = selectedOrderDto.getOrd_stat();
        assertTrue(OrderConstants.BASIC_ORDER_STATUS.isSameStatus(selectedOrderStatus));

        String  selectedDeliveryStatus = selectedOrderDto.getDeli_stat();
        assertTrue(OrderConstants.BASIC_DELIVERY_STATUS.isSameStatus(selectedDeliveryStatus));

        String  selectedPaymentStatus = selectedOrderDto.getPay_stat();
        assertTrue(OrderConstants.BASIC_PAYMENT_STATUS.isSameStatus(selectedPaymentStatus));

        // 5. 테스트한 order 삭제
        assertTrue(orderDao.deleteBySeq(createdOrderSeq) == SUCCESS_CODE);
    }

    @Test
    public void 주문생성_insert_multiple() throws Exception {
        // give
        final int INSERT_COUNT = 5;
        // 1. 주문 개수 카운트
        int beforeCount = orderDao.countAll();

        // do
        // 2. 주문 생성
        OrderDto orderDto = null;
        Integer createOrderSeq = null;
        Set<Integer> orderDtoSeqSet = new HashSet<>();

        for (int i = 0; i < INSERT_COUNT; i++) {
            orderDto = orderDtoFactory.create(CREATE_USER_ID);
            createOrderSeq = orderDao.insertAndReturnSeq(orderDto);
            assertNotNull(createOrderSeq);

            // ADD SET
            orderDtoSeqSet.add(createOrderSeq);
        }

        // 3. 주문 개수 카운트
        int afterCount = orderDao.countAll();
        int orderDtoSeqSetSize = orderDtoSeqSet.size();

        // assert
        // 4. 개수 비교
        assertEquals(beforeCount + INSERT_COUNT, afterCount);
        assertEquals(INSERT_COUNT, orderDtoSeqSetSize);
    }

    //1. 고객 id 가 없는 orderDto 가 insert 되는 경우
    @Test(expected = DataIntegrityViolationException.class)
    public void 주문생성_실패테스트_고객id없음() throws Exception {
        /**
         * === give ===
         * 1. 고객 id가 null 인 orderDto 를 생성한다.
         *
         * === do ===
         * 2. orderDto 를 insert 한다.
         *
         * === assert ===
         * 3. DataIntegrityViolationException 가 발생하면 성공
         */

        OrderDto orderDto = orderDtoFactory.create(USER_ID_NULL);
        Integer createdOrderSeq = orderDao.insertAndReturnSeq(orderDto);
        assertNotNull(createdOrderSeq);
        fail();
    }

    // 2. 최초 주문 생성 시 금액(배송비, 총 상품금액, 총 할인금액, 총 주문금액)이 0이 아닌 orderDto 가 insert 요청될 때

    /**
     * === give ===
     * 1. orderDto 를 생성하고 금액을 설정한다.
     * === do ===
     * 2. orderDto 를 insert 한다.
     * === assert ===
     * 3. IllegalArgumentException 가 발생하면 성공
     */
//    @Test(expected = IllegalArgumentException.class)
//    public void 주문생성_실패테스트_배송비() throws Exception {
//        OrderDto orderDtoDeliveryFeeTest = new OrderDto("userId");
//        orderDtoDeliveryFeeTest.setTotal_bene_pric(3000);
//        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDtoDeliveryFeeTest);
//        assertNotNull(createdOrderSeq);
//        fail();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void 주문생성_실패테스트_총상품금액() throws Exception {
//        OrderDto orderDto = new OrderDto("userId");
//        orderDto.setTotal_prod_pric(3000);
//        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDto);
//        assertNotNull(createdOrderSeq);
//        fail();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void 주문생성_실패테스트_총할인금액() throws Exception {
//        OrderDto orderDto = new OrderDto("userId");
//        orderDto.setTotal_bene_pric(3000);
//        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDto);
//        assertNotNull(createdOrderSeq);
//        fail();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void 주문생성_실패테스트_총주문금액() throws Exception {
//        OrderDto orderDto = new OrderDto("userId");
//        orderDto.setTotal_ord_pric(3000);
//        Integer createdOrderSeq = orderDao.insertAndReturnId(orderDto);
//        assertNotNull(createdOrderSeq);
//        fail();
//    }
}
