package order.orderDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;

import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderCountTest {
    @Autowired
    OrderDao orderDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int SINGLE = 1;

    /*
    countAllOrder 테스트
    1. insert
    2. delete
    3. 한 개 데이터 테스트
    4. 둘 이상 데이터 테스트
     */

    @Test
    public void 전체주문개수_insert_single () throws Exception {
        // give
        // 1. 현재 개수 카운트
        int beforeCount = orderDao.countAll();

        // do
        // 2. 1개 insert
        String userId = "countTestUser";
        OrderDto orderDto = OrderDtoFactory.getInstance(userId);
        Integer orderSeq = orderDao.insertAndReturnId(orderDto);
        assertNotNull(orderSeq);

        // 3. 현재 개수 카운트
        int afterCount = orderDao.countAll();

        // assert
        // 4. 개수 비교
        assertTrue(beforeCount + SINGLE == afterCount);
    }

    @Test
    public void 전체주문개수_insert_multiple () throws Exception {
        // give
        // 1. 현재 개수 카운트
        int beforeCount = orderDao.countAll();

        // do
        // 2. 5개 insert
        int INSERT_COUNT = 5;
        OrderDto orderDto = null;
        Integer orderSeq = null;
        for (int i = 0; i < INSERT_COUNT; i++) {
            String userId = "countTestUser" + i;
            orderDto = OrderDtoFactory.getInstance(userId);
            // 주문 생성
            orderSeq = orderDao.insertAndReturnId(orderDto);
            assertNotNull(orderSeq); // 주문생성 확인
        }
        // 3. 현재 개수 카운트
        int afterCount = orderDao.countAll();

        // assert
        // 4. 개수 비교
        assertTrue(beforeCount + INSERT_COUNT == afterCount);
    }

    @Test
    public void 전체주문개수_delete_single () throws Exception {
        // give
        // 1. 주문 생성
        String userId = "countTestUser";
        OrderDto orderDto = OrderDtoFactory.getInstance(userId);
        Integer orderSeq = orderDao.insertAndReturnId(orderDto);
        assertNotNull(orderSeq);

        // do
        // 2. 현재 개수 카운트
        int beforeCount = orderDao.countAll();

        // 3. 생성한 주문 삭제
        int deleteResult = orderDao.deleteById(orderSeq);
        assertTrue(deleteResult == SUCCESS_CODE);

        // 4. 현재 개수 카운트
        int afterCount = orderDao.countAll();

        // assert
        // 5. 개수비교
        assertTrue(beforeCount - SINGLE == afterCount);
    }

    @Test
    public void 전체주문개수_delete_multiple () throws Exception {
        int INSERT_COUNT = 5;

        // give
        // 1. 현재 개수 카운트
        int startCount = orderDao.countAll();

        // 2. 주문 생성 (생성 시 주문 갯수 체크)
        List<Integer> createdOrdSeqList = new ArrayList<>();

        OrderDto orderDto = null;
        String userId = null;
        Integer orderSeq = null;
        for (int i = 0; i < INSERT_COUNT; i++) {
            userId = "countTestUser" + i;
            orderDto = OrderDtoFactory.getInstance(userId);
            orderSeq = orderDao.insertAndReturnId(orderDto);
            assertNotNull(orderSeq);
            createdOrdSeqList.add(orderSeq);
        }
        assertTrue(createdOrdSeqList.size() == INSERT_COUNT); // list 에 제대로 들어갔는지 확인

        // do
        // 3. 현재 개수 카운트
        int beforeCount = orderDao.countAll();

        // 4. 주문 생성 확인
        assertTrue(startCount + INSERT_COUNT == beforeCount);

        // 5. 주문 삭제
        Integer deleteOrdSeq = null;
        for (int i = 0; i < createdOrdSeqList.size(); i++) {
            deleteOrdSeq = createdOrdSeqList.get(i);
            int deleteResult = orderDao.deleteById(deleteOrdSeq);
            assertTrue(deleteResult == SUCCESS_CODE);
        }

        // 6. 현재 개수 카운트
        int afterCount = orderDao.countAll();

        // assert
        // 7. 개수 비교
        assertTrue(beforeCount - INSERT_COUNT == afterCount);
    }
}
