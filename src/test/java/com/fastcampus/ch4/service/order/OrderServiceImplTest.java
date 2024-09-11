package com.fastcampus.ch4.service.order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dao.order.OrderProductDao;
import com.fastcampus.ch4.domain.item.BookType;
import com.fastcampus.ch4.domain.order.OrderProductSearchCondition;
import com.fastcampus.ch4.domain.order.OrderSearchCondition;
import com.fastcampus.ch4.domain.order.OrderStatus;
import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.dto.order.request.OrderCreateDto;
import com.fastcampus.ch4.dto.order.request.OrderItemDto;
import com.fastcampus.ch4.dto.order.response.OrderViewDto;
import com.fastcampus.ch4.service.global.CodeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderProductDao orderProductDao;

    @Autowired
    CodeService codeService;

    final String TEST_USER_ID = "ORD_SERVICE_TEST";
    final int DELIVERY_FEE = 3000;
    final int repoSize = 7;

    /*
   메서드 이름 : FakeBookRepo
   역할 : 가짜 책 정보를 생성하여 반환하는 메서드
   반환값 : 가짜 책 정보 리스트 (List<BookDto>, 4개)
    */
    public List<BookDto> FakeBookRepo() {
        List<BookDto> bookRepo = new ArrayList<>();
        for (int i = 0; i <= repoSize; i++) {
            bookRepo.add(new BookDto(
                    "100000000000" + i,                  // isbn
                    "01",                       // cate_num
                    "pub_name" + i,              // pub_name
                    "book_title" + i,            // title
                    "2024-08-07 15:35:58",       // pub_date
                    "Available",                 // sale_stat
                    i,                           // sale_vol
                    i,                           // papr_pric
                    5.0,                         // e_pric
                    5.0,                         // papr_point
                    5.0,                         // e_point
                    i,                           // tot_page_num
                    i,                           // tot_book_num
                    "",                          // sale_com
                    "",                          // cont
                    4.5,                         // rating
                    "",                          // info
                    "",                          // intro_award
                    "",                          // rec
                    "",                          // pub_review
                    i,                           // pre_start_page
                    i,                           // pre_end_page
                    "",                          // ebook_url
                    new Date(),                  // book_reg_date
                    "test",                      // regi_id
                    new Date(),                  // reg_date
                    "test",                      // reg_id
                    new Date(),                  // up_date
                    "test",                      // up_id
                    "repre_img" + i,             // repre_img_url
                    i,                           // papr_disc
                    i,                           // e_disc
                    "whol_layr_name" + i,        // whol_layr_name
                    "wr_cb_num" + i,             // cb_num
                    "trl_cb_num" + i,            // trl_cb_num
                    "wr_name" + i,               // wr_name
                    "trl_name" + i               // trl_name
            ));
        }

        return bookRepo;
    }

    @Before
    public void setUp() {
        Map<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("cust_id", TEST_USER_ID);
        orderProductDao.deleteOrderProductByCondition(deleteCondition);
        orderDao.deleteOrderByCondition(deleteCondition);
    }

    /*
    test name : 주문생성_테스트
    description : 주문 생성 테스트
    parameter : void
    return : void
     */
    @Test
    public void 주문생성_테스트() {
        // given
        List<BookDto> bookRepo = FakeBookRepo();
        List<OrderItemDto> orderItemDtoList = bookRepo.stream()
                .map(bookDto -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setIsbn(bookDto.getIsbn());
                    orderItemDto.setProd_type_code(BookType.PAPER.getCode());
                    orderItemDto.setItem_quan((int) (Math.random() * 10) + 1);
                    return orderItemDto;
                }).collect(Collectors.toList());

        OrderCreateDto orderCreateDto = OrderCreateDto.from(DELIVERY_FEE, TEST_USER_ID, orderItemDtoList);

        // when
        OrderViewDto beforeViewDto = orderService.create(orderCreateDto);

        // then
        // 1. orderDto price check
        OrderDto orderDto = beforeViewDto.getOrderDto();
        int totalProductPrice = orderDto.getTotal_prod_pric();
        int totalDiscountPrice = orderDto.getTotal_disc_pric();
        int totalOrderPrice = orderDto.getTotal_ord_pric();

        // 2. sum orderProductDtoList
        List<OrderProductDto> orderProductDtoList = beforeViewDto.getOrderProductDtoList();
        int sumProductPrice = orderProductDtoList.stream()
                .mapToInt(orderProductDto -> {
                    return orderProductDto.getBasic_pric() * orderProductDto.getItem_quan();
                })
                .sum();
        assertEquals(sumProductPrice, totalProductPrice);

        int sumDiscountPrice = orderProductDtoList.stream()
                .mapToInt(orderProductDto -> {
                    return orderProductDto.getDisc_pric() * orderProductDto.getItem_quan();
                })
                .sum();
        assertEquals(totalDiscountPrice, sumDiscountPrice);

        int sumOrderPrice = orderProductDtoList.stream()
                .mapToInt(OrderProductDto::getOrd_pric)
                .sum();
        assertEquals(totalOrderPrice, sumOrderPrice);

        // 3. find order and check
        OrderViewDto afterViewDto = orderService.findOrder(beforeViewDto.getOrderDto().getOrd_seq());
        OrderDto afterOrderDto = afterViewDto.getOrderDto();
        assertEquals(totalProductPrice, afterOrderDto.getTotal_prod_pric().intValue());
        assertEquals(totalDiscountPrice, afterOrderDto.getTotal_disc_pric().intValue());
        assertEquals(totalOrderPrice, afterOrderDto.getTotal_ord_pric().intValue());

        // 4. orderProduct check
        List<OrderProductDto> afterOrderProductList = afterViewDto.getOrderProductDtoList();
        assertEquals(orderProductDtoList.size(), afterOrderProductList.size());

        // check same order product list
        for (OrderProductDto afterOrdProdDto : afterOrderProductList) {
            boolean checkedEqualDto = false;
            for (OrderProductDto beforeOrdProdDto : orderProductDtoList) {
                if (afterOrdProdDto.equals(beforeOrdProdDto)) {
                    checkedEqualDto = true;
                    break;
                }
            }
            // 같은 OrderProductDto 가 없으면 SELECT 가 제대로 안된 것
            if (!checkedEqualDto) {
                fail();
            }
        }
    }

    /*
    method name : 주문리스트조회
    description : 조건에 따라서 주문리스트를 조회 기능 테스트
    parameters : void
    return : void
     */
    @Test
    public void 주문리스트조회() {
        // given
        // 주문생성 * 5
        List<OrderViewDto> orderViewDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<BookDto> bookRepo = FakeBookRepo();
            List<OrderItemDto> orderItemDtoList = bookRepo.stream()
                    .map(bookDto -> {
                        OrderItemDto orderItemDto = new OrderItemDto();
                        orderItemDto.setIsbn(bookDto.getIsbn());
                        orderItemDto.setProd_type_code(BookType.PAPER.getCode());
                        orderItemDto.setItem_quan((int) (Math.random() * 10) + 1);
                        return orderItemDto;
                    }).collect(Collectors.toList());

            OrderCreateDto orderCreateDto = new OrderCreateDto();
            orderCreateDto.setCust_id(TEST_USER_ID);
            orderCreateDto.setDelivery_fee(DELIVERY_FEE);
            orderCreateDto.setOrderItemDtoList(orderItemDtoList);

            OrderViewDto orderViewDto = orderService.create(orderCreateDto);
            orderViewDtoList.add(orderViewDto);
        }

        // when
        // 주문리스트 조회
        OrderSearchCondition orderSearchCondition = new OrderSearchCondition();
        orderSearchCondition.setCust_id(TEST_USER_ID);
        List<OrderViewDto> searchOrderPage = orderService.searchOrderPage(orderSearchCondition);
        assertEquals(orderViewDtoList.size(), searchOrderPage.size());

        // then
        // 주문리스트 조회 결과 확인
        for (OrderViewDto searchViewDto : searchOrderPage) {
            boolean checkedEqualDto = false;
            OrderDto searchOrderDto = searchViewDto.getOrderDto();
            for (OrderViewDto orderViewDto : orderViewDtoList) {
                if (Objects.equals(searchOrderDto.getOrd_seq(), orderViewDto.getOrderDto().getOrd_seq())) {
                    assertEquals(searchViewDto.getOrderProductDtoList().size(), orderViewDto.getOrderProductDtoList().size());
                    checkedEqualDto = true;
                    break;
                }
            }
            // 같은 OrderViewDto 가 없으면 SELECT 가 제대로 안된 것
            if (!checkedEqualDto) {
                fail();
            }
        }
    }

    /*
    method name : 주문상태에 따른 조회 결과
    description : 주문상태에 따른 조회 결과 확인
    parameters : void
    return : void
     */
    @Test
    public void 주문상태에따른조회결과() {
        //given
        // 주문생성 * 5
        List<OrderViewDto> orderViewDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<BookDto> bookRepo = FakeBookRepo();
            List<OrderItemDto> orderItemDtoList = bookRepo.stream()
                    .map(bookDto -> {
                        OrderItemDto orderItemDto = new OrderItemDto();
                        orderItemDto.setIsbn(bookDto.getIsbn());
                        orderItemDto.setProd_type_code(BookType.PAPER.getCode());
                        orderItemDto.setItem_quan((int) (Math.random() * 10) + 1);
                        return orderItemDto;
                    }).collect(Collectors.toList());

            OrderCreateDto orderCreateDto = new OrderCreateDto();
            orderCreateDto.setCust_id(TEST_USER_ID);
            orderCreateDto.setDelivery_fee(DELIVERY_FEE);
            orderCreateDto.setOrderItemDtoList(orderItemDtoList);

            OrderViewDto orderViewDto = orderService.create(orderCreateDto);
            orderViewDtoList.add(orderViewDto);
        }

        // when
        // 주문리스트 조회
        OrderSearchCondition orderSearchCondition = new OrderSearchCondition();
        orderSearchCondition.setCust_id(TEST_USER_ID);
        List<OrderViewDto> searchOrderPage = orderService.searchOrderPage(orderSearchCondition);
        assertEquals(orderViewDtoList.size(), searchOrderPage.size());
        // 주문상태 변경
        for (OrderViewDto orderViewDto : orderViewDtoList) {
            OrderDto orderDto = orderViewDto.getOrderDto();

            Integer UPDATE_CDOE_ID = codeService.findByCode(OrderStatus.ORDER_DONE.getCode()).getCode_id();

            orderService.changeOrderStatus(orderDto.getOrd_seq(), UPDATE_CDOE_ID, TEST_USER_ID);
        }

        // then


    }

    /*
    method name : 주문상태변경
    description : 주문상태 변경 테스트
    parameters : void
    return : void
    workflow
    = given
    1. 주문생성 * 5
    = when
    2. 주문상태 변경
    = then
    3. 주문상태 변경 확인
     */
    @Test
    public void 주문상태변경() {
        // given
        OrderStatus updatedOrderStatus = OrderStatus.ORDER_DONE;

        // 1. 주문생성 * 5
        List<OrderViewDto> orderViewDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<BookDto> bookRepo = FakeBookRepo();
            List<OrderItemDto> orderItemDtoList = bookRepo.stream()
                    .map(bookDto -> {
                        OrderItemDto orderItemDto = new OrderItemDto();
                        orderItemDto.setIsbn(bookDto.getIsbn());
                        orderItemDto.setProd_type_code(BookType.PAPER.getCode());
                        orderItemDto.setItem_quan((int) (Math.random() * 10) + 1);
                        return orderItemDto;
                    }).collect(Collectors.toList());
            // 주문 생성을 위한 매개변수 생성
            OrderCreateDto orderCreateDto = OrderCreateDto.from(DELIVERY_FEE, TEST_USER_ID, orderItemDtoList);

            // 주문 생성
            OrderViewDto orderViewDto = orderService.create(orderCreateDto);
            orderViewDtoList.add(orderViewDto);
        }

        // when
        // 2. 주문상태 변경
        for (OrderViewDto orderViewDto : orderViewDtoList) {
            OrderDto orderDto = orderViewDto.getOrderDto();
            Integer UPDATE_CDOE_ID = codeService.findByCode(updatedOrderStatus.getCode()).getCode_id();
            orderService.changeOrderStatus(orderDto.getOrd_seq(), UPDATE_CDOE_ID, TEST_USER_ID);
        }

        // then
        // 3. 주문상태 변경 확인
        for (OrderViewDto orderViewDto : orderViewDtoList) {
            // order 변경 확인
            OrderDto orderDto = orderViewDto.getOrderDto();
            OrderViewDto afterOrderView = orderService.findOrder(orderDto.getOrd_seq());
            assertEquals(updatedOrderStatus.getCode(), afterOrderView.getOrderDto().getOrd_stat_code());

            // orderProduct 변경 확인
            List<OrderProductDto> orderProductDtoList = orderViewDto.getOrderProductDtoList();
            orderProductDtoList.forEach(orderProductDto -> {
                OrderProductSearchCondition searchCondition = OrderProductSearchCondition.from(
                        orderProductDto.getOrd_seq(),
                        null,
                        null,
                        null,
                        null,
                        null);
                orderProductDao.selectOrderProductByCondition(searchCondition)
                        .forEach(afterOrderProductDto -> {
                            assertEquals(updatedOrderStatus.getCode(), afterOrderProductDto.getOrd_stat_code());
                        });
            });

        }
    }
}
