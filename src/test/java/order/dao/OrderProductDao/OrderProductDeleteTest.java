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

import static com.fastcampus.ch4.model.order.OrderConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderProductDeleteTest {
    @Autowired
    private OrderProductDao orderProductDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderDtoFactory orderDtoFactory;

    private final TempBookService bookService = new FakeBookServiceImpl();
    private OrderProductDto orderProductDto;
    private TempBookDto tempBookDto;
    private final String TEST_USER = "ORD_PROD_DELETE";

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
        tempBookDto = bookService.getBookByIsbn(TEST_ISBN);
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
    public void 주문삭제_deleteOne () {
        // 1. 주문 상품 생성
        Integer orderProductSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(orderProductSeq);

        // 2. 전체 갯수 카운트
        int beforeCount = orderProductDao.count();

        // 3. 삭제
        int deleteResult = orderProductDao.deleteBySeq(orderProductSeq);
        assertEquals(1, deleteResult);

        // 4. 전체 갯수 카운트
        int afterCount = orderProductDao.count();
        assertEquals(beforeCount - 1, afterCount);
    }


    @Test
    public void 주문삭제_deleteAll () {
        int beforeCount = orderProductDao.count();
        int deleteResult = orderProductDao.deleteAll();

        assertEquals(beforeCount, deleteResult);

        int afterCount = orderProductDao.count();
        assertEquals(0, afterCount);
    }
}
