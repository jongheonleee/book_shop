package order.dao.OrderProductDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dao.order.OrderProductDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.model.order.*;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import com.fastcampus.ch4.service.order.factory.OrderProductDtoFactory;
import com.fastcampus.ch4.service.order.fake.FakeBookServiceImpl;
import com.fastcampus.ch4.service.order.fake.TempBookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/*
update

1. 업데이트 성공

 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderProductUpdateTest {
    @Autowired
    private OrderProductDao orderProductDao;
    @Autowired
    private OrderDtoFactory orderDtoFactory;
    @Autowired
    OrderDao orderDao;

    private final TempBookService bookService = new FakeBookServiceImpl();
    private OrderProductDto orderProductDto;
    private TempBookDto tempBookDto;
    private final String TEST_USER = "ORD_PROD_UPDATE";

    @Before
    public void setUp() throws Exception {
        final String TEST_ISBN = "9791158395155";
        final String PRINTED_BOOK = BookType.PRINTED.getCode();
        final String E_BOOK = BookType.EBOOK.getCode();
        final int ITEM_QUANTITY_SINGLE = 1;

        tempBookDto = bookService.read(TEST_ISBN);
        assertNotNull(tempBookDto);

        OrderDto orderDto = orderDtoFactory.create(TEST_USER);
        Integer orderSeq = orderDao.insertAndReturnSeq(orderDto);
        assertNotNull(orderSeq);

        orderProductDto = OrderProductDtoFactory.create(orderDto, tempBookDto, PRINTED_BOOK, ITEM_QUANTITY_SINGLE);
    }

    @After
    public void tearDown() throws Exception {
        orderProductDto = null;
    }

    @Test
    public void 주문상품_update() {
        // 1. 주문 상품 생성
        Integer orderProductSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(orderProductSeq);

        OrderProductDto createdOrderProductDto = orderProductDao.selectBySeq(orderProductSeq);
        assertNotNull(createdOrderProductDto);

        // 2. update
        String changedOrderStatus = OrderStatus.ORDER_DONE.getCode();
        String changedDeliveryStatus = DeliveryStatus.DELIVERY_WAIT.getCode();
        String changedPaymentStatus = PaymentStatus.PAYMENT_DONE.getCode();
        Integer changedItemQuantity = createdOrderProductDto.getItem_quan() + 3;

        OrderProductDto toUpdateDto = new OrderProductDto.Builder()
                .ord_prod_seq(orderProductSeq)
                .ord_seq(createdOrderProductDto.getOrd_seq())
                .isbn(createdOrderProductDto.getIsbn())
                .prod_type_code(createdOrderProductDto.getProd_type_code())
                .ord_stat(changedOrderStatus)
                .deli_stat(changedDeliveryStatus)
                .pay_stat(changedPaymentStatus)
                .item_quan(changedItemQuantity)
                .basic_pric(createdOrderProductDto.getBasic_pric())
                .point_perc(createdOrderProductDto.getPoint_perc())
                .point_pric(createdOrderProductDto.getPoint_pric())
                .bene_perc(createdOrderProductDto.getBene_perc())
                .bene_pric(createdOrderProductDto.getBene_pric())
                .sale_pric(createdOrderProductDto.getSale_pric())
                .ord_pric(createdOrderProductDto.getOrd_pric())
                .reg_id(createdOrderProductDto.getReg_id())
                .up_id(createdOrderProductDto.getUp_id())
                .build();

        int updateResult = orderProductDao.update(toUpdateDto);
        assertEquals(1, updateResult);

        // 3. 비교
        OrderProductDto updatedOrderProductDto = orderProductDao.selectBySeq(orderProductSeq);
        assertTrue(toUpdateDto.equals(updatedOrderProductDto));
    }
}
