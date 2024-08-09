package order.dao.orderDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderDeleteTest {
    @Autowired
    OrderDao orderDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int SINGLE = 1;
    final String USER_ID_NULL = null; // user id null

    /*
    주문 삭제

    테스트 경우의 수
    1. delete single
    2. delete multiple

    삭제가 안되는 케이스
    1. 존재하지 않는 주문을 삭제할 때
    2. 주문 상품이 존재하는 것을 삭제할 때

    실행과정
    1. 주문을 선택한다.
    2. 선택한 주문의 id 를 받아서 삭제한다.
    3. 삭제가 잘 되었는지 id 로 조회해본다.
    4. 전체 개수를 카운트 한다.
     */

    @Test
    public void 주문삭제_delete_single () throws Exception {
        // give
        String deleteUserId = "deleteTestUser";

        // 1. 현재 개수 카운트
        int startCount = orderDao.countAll();

        // 2. 주문을 생성한다.
        OrderDto orderDto = OrderDtoFactory.getInstance(deleteUserId);
        Integer orderSeq = orderDao.insertAndReturnSeq(orderDto);
        assertNotNull(orderSeq);

        // 3. 현재 개수 카운트
        int beforeCount = orderDao.countAll();
        assertTrue(startCount + SINGLE == beforeCount);

        // do
        // 3. 주문을 삭제한다.
        int deleteResult = orderDao.deleteBySeq(orderSeq);
        assertTrue(deleteResult == SUCCESS_CODE);

        // 4. 현재 개수 카운트
        int afterCount = orderDao.countAll();

        // assert
        // 5. 개수 비교
        assertTrue(beforeCount - SINGLE == afterCount);

        // 6. 삭제한 주문번호로 조회한다.
        OrderDto deletedOrderDto = orderDao.selectBySeq(orderSeq);
        assertNull(deletedOrderDto);
    }

    @Test
    public void 주문삭제_delete_multiple() throws Exception {
        // give
        int INSERT_COUNT = 10;
        
        // 1. 현재 개수 카운트
        int startCount = orderDao.countAll();

        // 2. 주문 생성
        String userId = null;
        OrderDto orderDto = null;
        Integer orderSeq = null;
        Set<Integer> orderSeqSet = new HashSet<>();

        for (int i = 0; i < INSERT_COUNT; i++) {
            userId = "deleteTestUser" + i;
            orderDto = OrderDtoFactory.getInstance(userId);
            orderSeq = orderDao.insertAndReturnSeq(orderDto);
            assertNotNull(orderSeq);
            orderSeqSet.add(orderSeq);
        }
        assertTrue(startCount + INSERT_COUNT == orderDao.countAll());
        // set 에 담겨있는지 확인
        assertTrue(orderSeqSet.size() == INSERT_COUNT);

        // 3. 현재 개수 카운트
        int beforeCount = orderDao.countAll();

        // do
        // 4. 받아온 주문번호로 주문 삭제
        for (Integer createdOrderSeq : orderSeqSet) {
            int deleteResult = orderDao.deleteBySeq(createdOrderSeq);
            assertTrue(deleteResult == SUCCESS_CODE);
        }

        // 5. 현재 개수 카운트
        int afterCount = orderDao.countAll();

        // assert
        // 6. 개수 비교
        assertTrue(startCount == afterCount);
        assertTrue(beforeCount - INSERT_COUNT == afterCount);

        // 7. 받아온 주문번호로 주문 조회
        for (Integer deletedOrderSeq : orderSeqSet) {
            OrderDto selectedOrderDto = orderDao.selectBySeq(deletedOrderSeq);
            assertNull(selectedOrderDto);
        }
    }
}
