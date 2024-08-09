package order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    final String CREATE_USER_ID = "createUser"; // 주문 생성 시 사용할 userId


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
        OrderDto createdOrderDto = OrderDtoFactory.getInstance(CREATE_USER_ID);
        createdOrderDto.setOrd_stat(ORDER_DONE.getOrd_stat());
        Integer createdOrderSeq = orderDao.insertAndReturnId(createdOrderDto);
        assertNotNull(createdOrderSeq);

        // 3
        OrderDto selectedOrderDto = orderDao.selectById(createdOrderSeq);
        int selectedOrderSeq = selectedOrderDto.getOrd_seq();
        String selectedOrderStatus = selectedOrderDto.getOrd_stat();
        assertNotNull(selectedOrderDto);

        // 4
        assertTrue(createdOrderSeq == selectedOrderSeq);
        assertTrue(ORDER_DONE.isSameStatus(selectedOrderStatus));
    }

    @Test
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
