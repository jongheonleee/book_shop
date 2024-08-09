package order.factory;

import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static com.fastcampus.ch4.dto.order.OrderStatus.ORDER_DONE;
import static org.junit.Assert.*;


/*
1. Dto 안에 알맞은 값이 생성되는지
2. 동일한 Dto 가 생성되지는 않는지
3. Dto 여러 개를 넣었을 때
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderDtoFactoryTest {
    @Test
    public void orderDtoFactoryTest_생성값비교() {
        final String DTO_TEST_USER_ID = "orderDtoTestUser";
        final int EXPECTED_PRICE = 0;
        final String EXPECTED_ORD_STAT = ORDER_DONE.getOrd_stat();

        // 1. 생성
        OrderDto orderDto = OrderDtoFactory.getInstance(DTO_TEST_USER_ID);

        // 2. 값 비교
        int totalProdPric = orderDto.getTotal_prod_pric();
        assertEquals(EXPECTED_PRICE, totalProdPric);

        int totalBenePric = orderDto.getTotal_bene_pric();
        assertEquals(EXPECTED_PRICE, totalBenePric);

        int deliveryFee = orderDto.getDelivery_fee();
        assertEquals(EXPECTED_PRICE, deliveryFee);

        int totalOrdPric = orderDto.getTotal_ord_pric();
        assertEquals(EXPECTED_PRICE, totalOrdPric);

        String ord_stat = orderDto.getOrd_stat();
        assertEquals(EXPECTED_ORD_STAT, ord_stat);

        String regId = orderDto.getReg_id();
        assertEquals(DTO_TEST_USER_ID, regId);

        String upId = orderDto.getUp_id();
        assertEquals(DTO_TEST_USER_ID, upId);
    }

    @Test
    public void orderDtoFactoryTest_같은DTO생성() {
        final String DTO_TEST_USER_ID = "orderDtoTestUser";
        final int GENERATE_COUNT = 50;

        // 1. 생성한 두 객체 비교
        OrderDto firstOrderDto = OrderDtoFactory.getInstance(DTO_TEST_USER_ID);
        OrderDto secondOrderDto = OrderDtoFactory.getInstance(DTO_TEST_USER_ID);
        assertNotEquals(firstOrderDto, secondOrderDto);

        // 2. 다수 생성 시 비교
        Set<OrderDto> orderDtoSet = new HashSet<>();
        OrderDto orderDto = null;
        for (int i = 0; i < GENERATE_COUNT; i++) {
            orderDto = OrderDtoFactory.getInstance(DTO_TEST_USER_ID);
            orderDtoSet.add(orderDto);
            orderDto = null;
        }
        assertTrue(orderDtoSet.size() == GENERATE_COUNT);

    }
}
