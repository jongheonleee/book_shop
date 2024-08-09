package order.dao.orderDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/*
주문 조회 테스트
1. 주문을 생성해여 조회하여 값을 비교한다.
2. 주문을 여러 개 생성해서 갯수를 비교한다.

예외 발생 케이스
1. 없는 일련번호로 주문을 조회한다.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderSelectTest {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderDtoFactory orderDtoFactory;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int SINGLE = 1;
    final String USER_ID_NULL = null; // user id null
    final String CREATE_USER_ID = "createUser"; // 주문 생성 시 사용할 userId

    @Test
    public void 주문조회_select () throws Exception {
        // give
        // 1. 주문을 생성한다.
        OrderDto createdOrderDto = orderDtoFactory.create(CREATE_USER_ID);

        Integer createdOrdSeq = orderDao.insertAndReturnSeq(createdOrderDto);
        assertNotNull(createdOrdSeq);

        // do
        // 2. 주문을 조회한다.
        OrderDto selectedOrderDto = orderDao.selectBySeq(createdOrdSeq);
        assertNotNull(selectedOrderDto);

        // assert
        // 3. 생성한 주문의 값과 조회한 주문의 값을 비교한다.
        Integer selectedOrdSeq = selectedOrderDto.getOrd_seq();
        assertEquals(createdOrdSeq, selectedOrdSeq);

        String selectedUserId = selectedOrderDto.getUserId();
        assertEquals(CREATE_USER_ID, selectedUserId);

        String selectedRegId = selectedOrderDto.getReg_id();
        assertEquals(CREATE_USER_ID, selectedRegId);

        String selectedUpId = selectedOrderDto.getUp_id();
        assertEquals(CREATE_USER_ID, selectedUpId);
    }

    @Test
    public void 주문조회_selectNoSeq () throws Exception {
        final int PLUS_NUMBER = 100;

        // 1. 가장 큰 Seq 를 탐색한다.
        List<OrderDto> orderDtoList = orderDao.selectAll("ord_seq", true);

        // 2. 가장 큰 Seq 보다 더 큰 값으로 조회한다.
        OrderDto lastOrderDto = orderDtoList.get(0);
        Integer lastOrdSeq = lastOrderDto.getOrd_seq();
        Integer toSelectSeq = lastOrdSeq + PLUS_NUMBER;

        OrderDto selectedOrderDto = orderDao.selectBySeq(toSelectSeq);
        assertNull(selectedOrderDto);
    }
}
