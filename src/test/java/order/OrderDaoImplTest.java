package order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderDaoImplTest {
    @Autowired
    OrderDao orderDao;

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
    public void 주문생성_성공테스트() throws Exception {
        /**
         * 주문 생성 테스트
         * === give ===
         *
         * === do ===
         * 2. 고객 id 가 포함되어 있는 orderDto 10개를 생성하고 insert
         * === assert ===
         * 3. insert 를 할 때마다 제대로 생성되어 있는지 확인한다.
         */


        for (int i = 0; i < 10; i++) {
            // 2. 고객 id 가 포함되어 있는 orderDto 10개를 생성하고 insert
            String userId = "userId" + i;
            OrderDto orderDto = new OrderDto(userId);
            Integer ordSeq = orderDao.createOrderAndReturnId(orderDto);
            System.out.println("orderDto.ordSeq : " + ordSeq);
            assertTrue(ordSeq != null);
        }
    }


    //1. 고객 id 가 없는 orderDto 가 insert 되는 경우
    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_고객id없음() throws Exception {
        /**
         * === give ===
         * 1. 고객 id가 null 인 orderDto 를 생성한다.
         *
         * === do ===
         * 2. orderDto 를 insert 한다.
         *
         * === assert ===
         * 3. IllegalArgumentException 가 발생하면 성공
         */

        OrderDto orderDto = new OrderDto(null);
        orderDao.createOrderAndReturnId(orderDto);
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
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotal_bene_pric(3000);
        orderDao.createOrderAndReturnId(orderDto);
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
         */

        // 1, 2
        Integer createOrderSeq = orderDao.createOrderAndReturnId(new OrderDto("findTestUser"));

        // 3
        OrderDto orderDto = orderDao.findOrderById(createOrderSeq);

        // 4
        assertTrue(createOrderSeq.compareTo(orderDto.getOrd_seq()) == 0);
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



}
