package order.dao.OrderProductDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dao.order.OrderProductDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import com.fastcampus.ch4.service.order.fake.FakeBookServiceImpl;
import com.fastcampus.ch4.service.order.fake.TempBookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fastcampus.ch4.model.order.OrderConstants.*;
import static org.junit.Assert.*;

/*
주문 상품 조회


 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderProductSelectTest {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderProductDao orderProductDao;

    @Autowired
    OrderDtoFactory orderDtoFactory;

    private final TempBookService bookService = new FakeBookServiceImpl();
    private OrderProductDto orderProductDto;
    private TempBookDto tempBookDto;
    private final String TEST_USER = "ORD_PROD_SELECT";

    @Before
    public void setUp() throws Exception {
        final String TEST_ISBN = "9791158395155";
        final String PRINTED_BOOK = "printed";
        final String E_BOOK = "eBook";
        final int ITEM_QUANTITY_SINGLE = 1;

        // 주문 생성
        OrderDto orderDto = orderDtoFactory.create(TEST_USER);
        Integer createdOrderSeq = orderDao.insertAndReturnSeq(orderDto);
        OrderDto createdOrderDto = orderDao.selectBySeq(createdOrderSeq);
        assertNotNull(createdOrderDto);

        // 2. 상품 정보를 조회한다.
        tempBookDto = bookService.read(TEST_ISBN);
        assertNotNull(tempBookDto);

        // 3. 주문 상품 생성
        String isbn = tempBookDto.getIsbn();
        String orderStatus = BASIC_ORDER_STATUS.getCode();
        String deliveryStatus = BASIC_DELIVERY_STATUS.getCode();
        String paymentStatus = BASIC_PAYMENT_STATUS.getCode();
        Integer itemQuantity = ITEM_QUANTITY_SINGLE;
        Double paperPointPercent = tempBookDto.getPapr_point();
        Integer paperPointPrice = tempBookDto.getPapr_pric();
        Integer paperBasicPrice = tempBookDto.getPapr_pric();
        Double paperBenefitPercent = tempBookDto.getPapr_disc();
        Integer paperBenefitPrice = (int) (paperBasicPrice * paperBenefitPercent);
        Integer salePrice = paperBasicPrice - paperBenefitPrice;
        Integer orderPrice = salePrice * itemQuantity;
        String userId = createdOrderDto.getUserId();

        orderProductDto = new OrderProductDto.Builder()
                .ord_seq(createdOrderSeq)
                .isbn(isbn)
                .prod_type_code(PRINTED_BOOK)
                .ord_stat(orderStatus)
                .deli_stat(deliveryStatus)
                .pay_stat(paymentStatus)
                .item_quan(itemQuantity)
                .point_perc(paperPointPercent)
                .point_pric(paperPointPrice)
                .basic_pric(paperBasicPrice)
                .bene_perc(paperBenefitPercent)
                .bene_pric(paperBenefitPrice)
                .sale_pric(salePrice)
                .ord_pric(orderPrice)
                .reg_id(userId)
                .up_id(userId)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        orderProductDto = null;
        tempBookDto = null;
    }


    @Test
    public void 주문상품_select () {
        // 상품 생성
        Integer ordProdSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(ordProdSeq);

        // 조회
        OrderProductDto selectedOrderProductDto = orderProductDao.selectBySeq(ordProdSeq);
        assertTrue(selectedOrderProductDto.equals(orderProductDto));
    }


    @Test
    public void 주문상품_selectAll () {
        // give
        int beforeCount = orderProductDao.count();
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectAll();
        int beforeSize = orderProductDtoList.size();
        assertEquals(beforeCount, beforeSize);

        // do
        Integer ordProdSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(ordProdSeq);

        // assert
        orderProductDtoList = orderProductDao.selectAll();
        int afterSize = orderProductDtoList.size();
        assertEquals(beforeSize + 1, afterSize);
    }

    /*
    selectedByCondition 으로 조회한 숫자와 orderSeq 로 조회한 숫자

     */
    @Test
    public void 주문상품_selectListByCondition () throws Exception {
        // userId 로 조건을 걸어서 조회
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", TEST_USER);
        List<OrderProductDto> selectedList = orderProductDao.selectListByCondition(userMap);
        int beforeCount = selectedList.size();

        // 유저가 생성한 주문을 전부 조회해서 주문 각각에 연결된 주문상품을 전부 조회하여 갯수 비교
        List<OrderDto> orderDtoList = orderDao.selectListByCondition(TEST_USER);
        int afterCount = orderDtoList.stream()
                .mapToInt(OrderDto::getOrd_seq)
                .mapToObj(orderSeq -> {
                    Map<String, Object> seqMap = new HashMap<>();
                    seqMap.put("ord_seq", orderSeq);
                    return orderProductDao.selectListByCondition(seqMap);
                })
                .mapToInt(List::size)
                .sum();

        assertEquals(beforeCount, afterCount);
    }

}
