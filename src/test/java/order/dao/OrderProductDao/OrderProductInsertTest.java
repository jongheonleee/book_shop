package order.dao.OrderProductDao;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dao.order.OrderProductDao;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import order.dao.fake.FakeBookServiceImpl;
import order.dao.fake.TempBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.fastcampus.ch4.model.order.OrderConstants.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/*
주문 상품 INSERT 테스트

발생할 수 있는 경우의 수
1. 주문이 없을 떄 => 서비스에서 테스트
2. 상품이 없을 떄 => 서비스에서 테스트
3. 상품 유형이 없을 때
4. 상태 정보가 없을 떄 (주문상태, 배송상태, 결제상태)
5. 적립율, 할인율(double) 에 int 넣어보기 => OrderProductDto 에서 타입으로 지정해주기 때문에 테스트 필요 없음.
6. 가격이 제대로 계산되어 있는지 검산하기 => 서비스에서 테스트

 */

/*
Equals 로 비교할 때 어떻게 비교해야할까?



 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderProductInsertTest {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderProductDao orderProductDao;

    @Autowired
    OrderDtoFactory orderDtoFactory;

    // Fake Object 사용을 위함
    private final TempBookService bookService = new FakeBookServiceImpl();
    private final String TEST_USER = "ORD_PROD_INSERT";
    private final String PRINTED_BOOK = "printed";
    private final String E_BOOK = "eBook";
    private final Integer ITEM_QUANTITY_SINGLE = 1;


    @Test
    public void 주문상품_insert_single () throws Exception {
        final String TEST_ISBN = "9791158395155";

        // give
        // 1. 주문을 생성한다.
        OrderDto orderDto = orderDtoFactory.create(TEST_USER);
        Integer createdOrderSeq = orderDao.insertAndReturnSeq(orderDto);
        OrderDto createdOrderDto = orderDao.selectBySeq(createdOrderSeq);
        assertNotNull(createdOrderDto);

        // 2. 상품 정보를 조회한다.
        TempBookDto bookDto = bookService.getBookByIsbn(TEST_ISBN);
        assertNotNull(bookDto);

        // do
        // 3. 주문 상품을 생성한다.
        // 일반 도서 상품 주문
        String isbn = bookDto.getIsbn();
        String prodTypeCode = PRINTED_BOOK;
        String orderStatus = BASIC_ORDER_STATUS.getCode();
        String deliveryStatus = BASIC_DELIVERY_STATUS.getCode();
        String paymentStatus = BASIC_PAYMENT_STATUS.getCode();
        Integer itemQuantity = ITEM_QUANTITY_SINGLE;
        Double paperPointPercent = bookDto.getPapr_point();
        Integer paperPointPrice = bookDto.getPapr_pric();
        Integer basicPrice = bookDto.getPapr_pric();
        Double benefitPercent = bookDto.getPapr_disc();
        Integer benefitPrice = (int) (basicPrice * benefitPercent);
        Integer salePrice = basicPrice - benefitPrice;
        Integer orderPrice = salePrice * itemQuantity;
        String userId = createdOrderDto.getUserId();


        OrderProductDto orderProductDto = new OrderProductDto.Builder()
                .ord_seq(createdOrderSeq)
                .isbn(isbn)
                .prod_type_code(prodTypeCode)
                .ord_stat(orderStatus)
                .deli_stat(deliveryStatus)
                .pay_stat(paymentStatus)
                .item_quan(itemQuantity)
                .point_perc(paperPointPercent)
                .point_pric(paperPointPrice)
                .basic_pric(basicPrice)
                .bene_perc(benefitPercent)
                .bene_pric(benefitPrice)
                .sale_pric(salePrice)
                .ord_pric(orderPrice)
                .reg_id(userId)
                .up_id(userId)
                .build();

        Integer orderProductSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(orderProductSeq);

        // assert
        // 4. 생성 검증을 한다.
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void 주문상품_insert_도서유형코드없음 () throws Exception {
        final String TEST_ISBN = "9791158395155";
        final String TEST_BOOK_TYPE = null; // TEST

        // 1. 주문을 생성한다.
        OrderDto orderDto = orderDtoFactory.create(TEST_USER);
        Integer createdOrderSeq = orderDao.insertAndReturnSeq(orderDto);
        OrderDto createdOrderDto = orderDao.selectBySeq(createdOrderSeq);
        assertNotNull(createdOrderDto);

        // 2. 상품 정보 조회
        TempBookDto bookDto = bookService.getBookByIsbn(TEST_ISBN);
        assertNotNull(bookDto);

        // 3. 주문 상품 생성한다.
        // 일반 도서 상품 주문
        String isbn = bookDto.getIsbn();
        String prodTypeCode = PRINTED_BOOK;
        String orderStatus = BASIC_ORDER_STATUS.getCode();
        String deliveryStatus = BASIC_DELIVERY_STATUS.getCode();
        String paymentStatus = BASIC_PAYMENT_STATUS.getCode();
        Integer itemQuantity = ITEM_QUANTITY_SINGLE;
        Double paperPointPercent = bookDto.getPapr_point();
        Integer paperPointPrice = bookDto.getPapr_pric();
        Integer basicPrice = bookDto.getPapr_pric();
        Double benefitPercent = bookDto.getPapr_disc();
        Integer benefitPrice = (int) (basicPrice * benefitPercent);
        Integer salePrice = basicPrice - benefitPrice;
        Integer orderPrice = salePrice * itemQuantity;
        String userId = createdOrderDto.getUserId();


        OrderProductDto orderProductDto = new OrderProductDto.Builder()
                .ord_seq(createdOrderSeq)
                .isbn(isbn)
                .prod_type_code(prodTypeCode)
                .ord_stat(orderStatus)
                .deli_stat(deliveryStatus)
                .pay_stat(paymentStatus)
                .item_quan(itemQuantity)
                .point_perc(paperPointPercent)
                .point_pric(paperPointPrice)
                .basic_pric(basicPrice)
                .bene_perc(benefitPercent)
                .bene_pric(benefitPrice)
                .sale_pric(salePrice)
                .ord_pric(orderPrice)
                .reg_id(userId)
                .up_id(userId)
                .build();

        Integer orderProductSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(orderProductSeq);

        // 4. Exception = Test success
        fail();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 주문상품_insert_주문상태없음 () throws Exception {
        final String TEST_ISBN = "9791158395155";

        // 1. 주문을 생성한다.
        OrderDto orderDto = orderDtoFactory.create(TEST_USER);
        Integer createdOrderSeq = orderDao.insertAndReturnSeq(orderDto);
        OrderDto createdOrderDto = orderDao.selectBySeq(createdOrderSeq);
        assertNotNull(createdOrderDto);

        // 2. 상품 정보 조회
        TempBookDto bookDto = bookService.getBookByIsbn(TEST_ISBN);
        assertNotNull(bookDto);

        // 3. 주문 상품 생성 및 Insert
        String isbn = bookDto.getIsbn();
        String prodTypeCode = PRINTED_BOOK;
        // null
        String orderStatus = null;
        String deliveryStatus = BASIC_DELIVERY_STATUS.getCode();
        String paymentStatus = BASIC_PAYMENT_STATUS.getCode();
        Integer itemQuantity = ITEM_QUANTITY_SINGLE;
        Double paperPointPercent = bookDto.getPapr_point();
        Integer paperPointPrice = bookDto.getPapr_pric();
        Integer basicPrice = bookDto.getPapr_pric();

        Double benefitPercent = bookDto.getPapr_disc();
        Integer benefitPrice = (int) (basicPrice * benefitPercent);

        Integer salePrice = basicPrice - benefitPrice;
        Integer orderPrice = salePrice * itemQuantity;
        String userId = createdOrderDto.getUserId();

        OrderProductDto orderProductDto = new OrderProductDto.Builder()
                .ord_seq(createdOrderSeq)
                .isbn(isbn)
                .prod_type_code(prodTypeCode)
                .ord_stat(orderStatus)
                .deli_stat(deliveryStatus)
                .pay_stat(paymentStatus)
                .item_quan(itemQuantity)
                .point_perc(paperPointPercent)
                .point_pric(paperPointPrice)
                .basic_pric(basicPrice)
                .bene_perc(benefitPercent)
                .bene_pric(benefitPrice)
                .sale_pric(salePrice)
                .ord_pric(orderPrice)
                .reg_id(userId)
                .up_id(userId)
                .build();

        Integer orderProductSeq = orderProductDao.insertAndReturnSeq(orderProductDto);
        assertNotNull(orderProductSeq);

        // 4. 검증
        fail();
    }
}
