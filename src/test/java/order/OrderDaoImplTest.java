package order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void 주문생성_성공테스트() {
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
        }
    }


    //1. 고객 id 가 없는 orderDto 가 insert 되는 경우
    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_고객id없음() {
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
    public void 주문생성_실패테스트_배송비() {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotalBenePric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_총상품금액() {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotalProdPric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_총할인금액() {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotalBenePric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 주문생성_실패테스트_총주문금액() {
        OrderDto orderDto = new OrderDto("userId");
        orderDto.setTotalOrdPric(3000);
        orderDao.createOrderAndReturnId(orderDto);
    }


}
