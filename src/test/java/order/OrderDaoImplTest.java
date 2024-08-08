package order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.fastcampus.ch4.dto.order.OrderStatus.ORDER_DONE;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderDaoImplTest {
    @Autowired
    OrderDao orderDao;


    final int SUCCESS_CODE = 1; // Query Execute Success
    final String USER_ID_NULL = null; // user id null

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
    public void 주문생성_성공테스트_단일생성() throws Exception {
        // 1. dto 생성하기
        String registerUserId = "createTestUser";
        OrderDto orderDto = new OrderDto(registerUserId);

        // 2. insert
        Integer createdOrderSeq = orderDao.createOrderAndReturnId(orderDto);
        assertNotNull(createdOrderSeq);

        // 3. 생성한 order 조회하기
        OrderDto selectedOrderDto = orderDao.findOrderById(createdOrderSeq);
        assertNotNull(selectedOrderDto);
        String selectedOrderRegId = selectedOrderDto.getReg_id();
        String selectedOrderStatus = selectedOrderDto.getOrd_stat();

        // 4. 생성된 order 값 비교
        assertEquals(selectedOrderRegId, registerUserId);
        assertTrue(ORDER_DONE.isSameStatus(selectedOrderStatus));

        // 5. 테스트한 order 삭제
        assertTrue(orderDao.deleteOrderById(createdOrderSeq) == SUCCESS_CODE);
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

        OrderDto orderDto = new OrderDto(USER_ID_NULL);
        Integer createdOrderSeq = orderDao.createOrderAndReturnId(orderDto);

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
    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_배송비() throws Exception {
        OrderDto orderDtoDeliveryFeeTest = new OrderDto("userId");
        orderDtoDeliveryFeeTest.setTotal_bene_pric(3000);
        orderDao.createOrderAndReturnId(orderDtoDeliveryFeeTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_총상품금액() throws Exception {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotal_prod_pric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_총할인금액() throws Exception {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotal_bene_pric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_총주문금액() throws Exception {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotal_ord_pric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }

    /**
     * 주문조회 테스트
     *
     * === 주문조회 성공 케이스 ===
     * 1. 주문을 하나 생성하여 그것을 기준으로 조회한다.
     *
     * === 주문조회 실패 케이스 ===
     * 1. 아직 생성되지 않은 주문일련번호를 조회한다.
     */

    @Test
    public void 주문조회_성공테스트() throws Exception {
        /**
         * === give ===
         * 1. 주문을 생성한다.
         * 2. 생성한 주문의 id 를 저장한다.
         * === do ===
         * 3. 생성한 주문 id 로 주문을 조회한다.
         * === assert ===
         * 4. 저장한 주문의 id 와 생성한 주문의 id 를 비교한다.
         *
         * 조회한 값이 같아야 한다. (key만 비교 x)
         */

        // 1, 2
        OrderDto createdOrderDto = new OrderDto("selectTestUser");
        Integer createdOrderSeq = orderDao.createOrderAndReturnId(createdOrderDto);
        assertNotNull(createdOrderSeq);

        // 3
        OrderDto selectedOrderDto = orderDao.findOrderById(createdOrderSeq);
        int selectedOrderSeq = selectedOrderDto.getOrd_seq();
        String selectedOrderStatus = selectedOrderDto.getOrd_stat();
        assertNotNull(selectedOrderDto);

        // 4
        assertTrue(createdOrderSeq == selectedOrderSeq);
        assertTrue(ORDER_DONE.isSameStatus(selectedOrderStatus));
    }

    @Test(expected = NullPointerException.class)
    public void 주문조회_실패테스트_주문일련번호없음() {
        /**
         * === give ===
         * 1.
         *
         * === do ===
         *
         * === assert ===
         */
    }

    /**
     * 주문 삭제 테스트
     * === 주문 삭제 성공 케이스
     * 1. ord_seq 를 넣어서 삭제되는 것을 확인한다.
     *
     * === 주문 삭제 실패 케이스
     * 1. 존재하지 않는 주문을 삭제하는 것을 시도한다. => 상관없음 ( == 0)
     * 2. 주문상품이 존재하는 주문을 삭제한다. (삭제되면 안됨) => 구현예정
     */

    @Test
    public void 주문삭제_성공테스트 () throws Exception {
        /**
         * === give
         * 1. 주문을 생성한다.
         * 2. 생성한 주문의 id 를 저장한다.
         * === do
         * 3. 생성한 주문을 삭제한다.
         * === assert
         * 4. 제대로 삭제되었는지 확인
         */

        String deleteUserId = "deleteTestUser";

        // 1
        OrderDto orderDto = new OrderDto(deleteUserId);
        // 2
        int createdOrderSeq = orderDao.createOrderAndReturnId(orderDto);
        // 3 삭제와 동시에 assert
        assertTrue(orderDao.deleteOrderById(createdOrderSeq) == SUCCESS_CODE);
        // 4
        OrderDto deletedOrderDto = orderDao.findOrderById(createdOrderSeq);
        assertNull(deletedOrderDto);
    }

    @Test
    public void 주문삭제_실패테스트_주문상품이존재하는주문 () throws Exception {
        /**
         * === give
         * 1. 주문을 생성한다.
         * 2. 주문 상품을 생성한다. (주문 fk 사용)
         * === do
         * 3. 생성한 주문 삭제를 실행한다.
         * === assert
         * 4. exception 발생
         */

    }

    /**
     * 주문 변경하기 테스트
     * === 성공 테스트 케이스
     * 1. 생성된 orderDto 를 수정하고 수정된 값을 비교하여 의도된 값인지 확인한다. 최근 수정 일자가 수정 전 일자와 다른지 확인한다.
     * === 실패 테스트 케이스
     * 1. 없는 주문을 변경하려고 시도하는 경우
     *
     */

    @Test
    public void 주문변경_성공테스트 () throws Exception {
        /**
         * === give
         * 1. 주문을 생성한다.
         * 2. 생성한 주문을 조회하여 생성한 주문의 정보를 저장한다.
         * 3. 변경할 값을 저장한다.
         * === do
         * 4. 변경할 값으로 orderDto 를 변경하여 update 를 진행한다.
         * 5. update 이후 주문을 조회하여 새로운 orderDto 에 저장한다.
         * === assert
         * 6. 저장한 값(3번 단계) 과 조회한 값(5번 단계)을 비교한다.
         */

        String updateUserId = "updateTestUser";

        // 1
        OrderDto createdOrderDto = new OrderDto(updateUserId);
        Integer createdOrderSeq = orderDao.createOrderAndReturnId(createdOrderDto);

        // 2
        OrderDto selectedOrderDto = orderDao.findOrderById(createdOrderSeq);

        // 3
        int deliveryFee = 3000;
        int totalProductPrice = 16800;
        int totalBenefitPrice = 1680;
        int totalOrderPrice = totalProductPrice - totalBenefitPrice + deliveryFee;
        String updatingId = "updatingTest";

        // 4
        selectedOrderDto.setDelivery_fee(deliveryFee);
        selectedOrderDto.setTotal_prod_pric(totalProductPrice);
        selectedOrderDto.setTotal_bene_pric(totalBenefitPrice);
        selectedOrderDto.setTotal_ord_pric(totalOrderPrice);
        selectedOrderDto.setUp_id(updatingId);
        assertTrue(orderDao.updateOrderById(selectedOrderDto, updatingId) == SUCCESS_CODE);

        // 5
        OrderDto updatedOrderDto = orderDao.findOrderById(selectedOrderDto.getOrd_seq());

        // 6
        assertEquals((int) updatedOrderDto.getDelivery_fee(), deliveryFee);
        assertEquals((int) updatedOrderDto.getTotal_bene_pric(), totalBenefitPrice);
        assertEquals((int) updatedOrderDto.getTotal_ord_pric(), totalOrderPrice);
        assertEquals(updatedOrderDto.getUp_id(), updatingId);
        assertEquals(updatedOrderDto.getUp_date(), selectedOrderDto.getUp_date());
    }

    @Test
    public void 주문변경_실패테스트_없는주문변경시도 () throws Exception {
        /**
         * === give
         * 1. 주문을 생성한다.
         * 2. 생성한 주문을 조회해서 저장한다.
         *
         * === do
         * 3. 주문을 삭제한다.
         * 4. 저장한 주문의 정보를 변경한다.
         * === assert
         * 5. update 를 진행한다 ( update X)
         * 6. 데이터 비교?
         *
         */

        // 1
        OrderDto createdOrderDto = new OrderDto("updateTest");
        Integer createdId = orderDao.createOrderAndReturnId(createdOrderDto);

        // 2
        OrderDto selectedOrderDto = orderDao.findOrderById(createdId);

        // 3
        assertTrue(orderDao.deleteOrderById(selectedOrderDto.getOrd_seq()) == SUCCESS_CODE);

        // 4
        int deliveryFee = 3000;
        int totalProductPrice = 16800;
        int totalBenefitPrice = 1680;
        int totalOrderPrice = totalProductPrice - totalBenefitPrice + deliveryFee;
        String updatingId = "updatingTest";

        selectedOrderDto.setDelivery_fee(deliveryFee);
        selectedOrderDto.setTotal_prod_pric(totalProductPrice);
        selectedOrderDto.setTotal_bene_pric(totalBenefitPrice);
        selectedOrderDto.setTotal_ord_pric(totalOrderPrice);
        selectedOrderDto.setUp_id(updatingId);

        // 5
        assertTrue(orderDao.updateOrderById(selectedOrderDto, updatingId) != SUCCESS_CODE);

        // 6


    }

}
