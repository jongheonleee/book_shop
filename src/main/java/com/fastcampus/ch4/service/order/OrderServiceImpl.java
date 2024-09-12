package com.fastcampus.ch4.service.order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dao.order.OrderHistoryDao;
import com.fastcampus.ch4.dao.order.OrderProductDao;
import com.fastcampus.ch4.dao.order.OrderProductStatusHistoryDao;
import com.fastcampus.ch4.domain.order.*;
import com.fastcampus.ch4.domain.payment.PaymentStatus;
import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.order.*;
import com.fastcampus.ch4.dto.order.request.OrderCreateDto;
import com.fastcampus.ch4.dto.order.request.OrderItemDto;
import com.fastcampus.ch4.dto.order.request.OrderStatusUpdateDto;
import com.fastcampus.ch4.dto.order.response.OrderCountDto;
import com.fastcampus.ch4.dto.order.response.OrderViewDto;
import com.fastcampus.ch4.service.global.CodeService;
import com.fastcampus.ch4.service.item.BookService;
import com.fastcampus.ch4.util.GenerateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderHistoryDao orderHistoryDao;

    @Autowired
    OrderProductDao orderProductDao;

    @Autowired
    OrderProductStatusHistoryDao orderProductStatusHistoryDao;

    @Autowired
    BookService bookService;

    @Autowired
    CodeService codeService;

    /*
    method name : create
    description : 주문 생성
    parameter
    - orderCreateDto : OrderCreateDto (주문 상품 정보 Dto 리스트)
    return : OrderViewDto
    workflow
    1. OrderCreateDto List -> OrderProductDto List
    - select book from bookService (if book is not exist, throw exception)
    - create OrderProductDto and add List
    2. calculate OrderPrice
    - calculate totalOrderPrice, totalDiscountPrice, totalProductPrice
    3. OrderDto create & insert
    - set OrderPrice
    4. OrderHistoryDto
     & insert
    5. OrderProductDto List insert
    - set ord_seq
    6. OrderProductStatusHistoryDto List insert
    7. OrderViewDto create & return
     */
    @Override
    public OrderViewDto create(OrderCreateDto orderCreateDto) {
        // 공통적으로 사용할 변수
        Integer init_ord_stat = codeService.findByCode(OrderStatus.INIT.getCode()).getCode_id();
        Integer init_deli_stat = codeService.findByCode(DeliveryStatus.INIT.getCode()).getCode_id();
        Integer init_pay_stat = codeService.findByCode(PaymentStatus.INIT.getCode()).getCode_id();

        String cust_id = orderCreateDto.getCust_id();
        Integer delivery_fee = orderCreateDto.getDelivery_fee();
        // 1. OrderCreateDto List -> OrderProductDto List
        List<OrderProductDto> ordProdDtoList = new ArrayList<>();
        for (OrderItemDto itemDto : orderCreateDto.getOrderItemDtoList()) {
            // select book from bookService (if book is not exist, throw exception)
            BookDto bookDto = bookService.read(itemDto.getIsbn());
            if (bookDto == null) {
                String msg = String.format("book is not exist. isbn: %s", itemDto.getIsbn());
                throw new IllegalArgumentException(msg); //
            }

            // create OrderProductDto and add List
            OrderProductDto orderProductDto = OrderProductDto.from(
                    itemDto,
                    bookDto,
                    cust_id,
                    init_ord_stat,
                    init_deli_stat,
                    init_pay_stat);
            ordProdDtoList.add(orderProductDto);
        }

        // 2. calculate OrderPrice
        int totalProductPrice = 0;
        int totalDiscountPrice = 0;
        int totalOrderPrice = 0;

        // calculate totalOrderPrice, totalDiscountPrice, totalProductPrice
        for (OrderProductDto orderProductDto : ordProdDtoList) {
            totalProductPrice += orderProductDto.getBasic_pric() * orderProductDto.getItem_quan();
            totalDiscountPrice += orderProductDto.getDisc_pric() * orderProductDto.getItem_quan();
            totalOrderPrice += orderProductDto.getOrd_pric();
        }

        // 3. OrderDto create & insert
        // - set OrderPrice
        OrderDto orderDto = OrderDto.from(
                cust_id,
                totalProductPrice,
                totalDiscountPrice,
                totalOrderPrice,
                delivery_fee,
                init_ord_stat,
                init_deli_stat,
                init_pay_stat);
        int ordInsertResult = orderDao.insertOrder(orderDto);
        // insert error
        if (ordInsertResult == 0) {
            throw new RuntimeException("insert Error");
        }

        // 4. OrderHistoryDto create & insert
        String startTime = GenerateTime.getCurrentTime();
        String endTime = GenerateTime.getMaxTime();
        String changeReason = "ORDER CREATE";
        OrderHistoryDto orderHistoryDto = OrderHistoryDto.from(startTime, endTime, orderDto, changeReason);
        int ordHistResult = orderHistoryDao.insertOrderHistory(orderHistoryDto);
        // insert error
        if (ordHistResult == 0) {
            throw new RuntimeException("insert Error");
        }

        // 5. OrderProductDto List insert
        for (OrderProductDto orderProductDto : ordProdDtoList) {
            // set ord_seq
            orderProductDto.setOrd_seq(orderDto.getOrd_seq());
            int ordProdResult = orderProductDao.insertOrderProduct(orderProductDto);
            // insert error
            if (ordProdResult == 0) {
                throw new RuntimeException("insert Error");
            }

            // 6. OrderProductStatusHistoryDto List insert
            OrderProductStatusHistoryDto orderProductStatusHistoryDto = OrderProductStatusHistoryDto.from(startTime, endTime, orderProductDto);
            int ordProdHistResult = orderProductStatusHistoryDao.insertHistory(orderProductStatusHistoryDto);
            // insert error
            if (ordProdHistResult == 0) {
                throw new RuntimeException("insert Error");
            }
        }

        // 7. OrderViewDto create & return
        return OrderViewDto.from(orderDto, ordProdDtoList);
    }

    /*
    method name : findOrder
    description : 주문 단건 조회
    parameter
    - ord_seq : Integer (주문 번호)
    return : OrderViewDto
    1. select Order
    - if Order is not exist, throw exception
    2. select OrderProduct List
    3. create OrderViewDto & return
     */
    @Override
    public OrderViewDto findOrder(Integer ord_seq) {
        // 1. select Order
        Map<String, Object> ordSelectMap = new HashMap<>();
        ordSelectMap.put("ord_seq", ord_seq);
        OrderDto orderDto = orderDao.selectOrderByCondition(ordSelectMap).stream()
                .findFirst()
                .orElse(null);
        if (orderDto == null) {
            throw new IllegalArgumentException("order is not exist");
        }

        // 2. select OrderProduct List
        OrderProductSearchCondition orderProductSearchCondition = OrderProductSearchCondition.from(null, ord_seq, null, null, null, null);
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(orderProductSearchCondition);

        // 3. create OrderViewDto & return
        return OrderViewDto.from(orderDto, orderProductDtoList);
    }

    /*
    method name : searchOrderPage
    description : 조건에 따른 주문 목록 조회
    parameter
    - orderSearchCondition : OrderSearchCondition (주문 검색 조건)
    return : List<OrderViewDto>
    workflow
    1. select Order List by OrderSearchCondition
    2. create List<OrderViewDto>
    3. add List<OrderViewDto>
    - Traversal Order List
    - select OrderProduct List by Order List
    - create OrderViewDto
    - set OrderDto & OrderProductDto List
    - add OrderViewDto
    4. create OrderViewDto List & return
     */
    @Override
    public List<OrderViewDto> searchOrderPage(OrderSearchCondition orderSearchCondition) {
        // 1. select Order List by OrderSearchCondition
        List<OrderDto> orderDtoList = orderDao.selectOrderPage(orderSearchCondition);

        // 2. create List<OrderViewDto>
        List<OrderViewDto> orderViewDtoList = new ArrayList<>();

        // 3. add List<OrderViewDto>
        for (OrderDto orderDto : orderDtoList) {
            // select OrderProduct List by Order List
            OrderProductSearchCondition orderProductSearchCondition = OrderProductSearchCondition.from(
                    null,
                    orderDto.getOrd_seq(),
                    null,
                    null,
                    null,
                    null);
            List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(orderProductSearchCondition);

            // create OrderViewDto
            OrderViewDto orderViewDto = OrderViewDto.from(orderDto, orderProductDtoList);
            orderViewDtoList.add(orderViewDto);
        }

        // 4. create OrderViewDto List & return
        return orderViewDtoList;
    }

    /*
    method name : changeOrderStatus
    description : 주문 상태변경
    parameter
    - ord_seq : Integer (주문 번호)
    - orderStatus : String (주문 상태)
    return : OrderViewDto
    workflow
    1. select Order
    - if Order is not exist, throw exception
    2. select OrderHistory List
    3. update Order
    - update selected Order
    - update recent OrderHistory(existing) & insert OrderHistory(not exist)
    4. select OrderProduct List
    5. select OrderProductStatusHistory List
    6. update OrderProduct
    - update recent OrderProductStatusHistory(existing) & insert OrderProductStatusHistory(not exist)
    7. create OrderViewDto & return
     */
    @Override
    public OrderViewDto changeOrderStatus(Integer ord_seq, Integer ord_stat_id, String user_id) {
        // 1. select Order
        Map<String, Object> ordSelectMap = new HashMap<>();
        ordSelectMap.put("ord_seq", ord_seq);
        OrderDto orderDto = orderDao.selectOrderByCondition(ordSelectMap).stream()
                .findFirst()
                .orElse(null);
        // if Order is not exist, throw exception
        if (orderDto == null) {
            throw new IllegalArgumentException("order is not exist");
        }

        // 2. select OrderHistory List
        Map<String, Object> ordHistSelectMap = new HashMap<>();
        ordHistSelectMap.put("ord_seq", ord_seq);
        List<OrderHistoryDto> orderHistoryDtoList = orderHistoryDao.selectOrderHistoryByCondition(ordHistSelectMap);

        // 3. update Order (OrderStatus)
        // - update selected Order
        OrderStatusUpdateDto orderStatusUpdateDto = OrderStatusUpdateDto.from(
                ord_seq,
                user_id,
                ord_stat_id,
                orderDto.getDeli_stat(),
                orderDto.getPay_stat());

        // TODO : updateStatus 가 OrderStatusUpdateDto 를 받아야 하는데 Map 을 받고 있음
        int ordUpdateResult = orderDao.updateStatus(orderStatusUpdateDto);
        // update error
        if (ordUpdateResult == 0) {
            throw new RuntimeException("update Error");
        }

        // update recent OrderHistory(existing)
        String startTime = GenerateTime.getCurrentTime();
        String endTime = GenerateTime.getMaxTime();
        OrderHistoryDto recentOrderHistoryDto = orderHistoryDtoList.stream()
                .findFirst()
                .orElse(null);
        if (recentOrderHistoryDto == null) {
            throw new RuntimeException("OrderHistoryDto is null");
        }
        recentOrderHistoryDto.setChg_end_date(startTime);
        int ordHistUpdateResult = orderHistoryDao.updateOrderHistory(recentOrderHistoryDto);
        if (ordHistUpdateResult == 0) {
            throw new RuntimeException("update OrderHistory Error");
        }

        // insert OrderHistory(not exist)
        String changeReason = "ORDER STATUS CHANGE - " + ord_stat_id;
        OrderHistoryDto orderHistoryDto = OrderHistoryDto.from(startTime, endTime, orderDto, changeReason);
        int ordHistInsertResult = orderHistoryDao.insertOrderHistory(orderHistoryDto);
        if (ordHistInsertResult == 0) {
            throw new RuntimeException("insert OrderHistory Error");
        }

        // 4. select OrderProduct List
        OrderProductSearchCondition orderProductSearchCondition = OrderProductSearchCondition.from(
                null,
                ord_seq,
                null,
                null,
                null,
                null);
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(orderProductSearchCondition);

        for (OrderProductDto orderProductDto : orderProductDtoList) {
            // 5. select OrderProductStatusHistory List
            Map<String, Object> ordProdHistSelectMap = new HashMap<>();
            ordProdHistSelectMap.put("ord_prod_seq", orderProductDto.getOrd_prod_seq());
            List<OrderProductStatusHistoryDto> ordProdStatusHistDtoList = orderProductStatusHistoryDao.selectHistoryByCondition(ordProdHistSelectMap);

            // 6. update OrderProduct
            // - update recent OrderProductStatusHistory(existing)
            OrderProductStatusHistoryDto recentOrderProductStatusHistoryDto = ordProdStatusHistDtoList.stream()
                    .findFirst()
                    .orElse(null);
            if (recentOrderProductStatusHistoryDto == null) {
                throw new RuntimeException("OrderProductStatusHistoryDto is null");
            }
            recentOrderProductStatusHistoryDto.setChg_end_date(startTime);
            int ordProdHistUpdateResult = orderProductStatusHistoryDao.updateHistory(recentOrderProductStatusHistoryDto);
            if (ordProdHistUpdateResult == 0) {
                throw new RuntimeException("update OrderProductStatusHistory Error");
            }

            // - insert OrderProductStatusHistory(not exist)
            OrderProductStatusHistoryDto orderProductStatusHistoryDto = OrderProductStatusHistoryDto.from(startTime, endTime, orderProductDto);
            int ordProdHistInsertResult = orderProductStatusHistoryDao.insertHistory(orderProductStatusHistoryDto);
            if (ordProdHistInsertResult == 0) {
                throw new RuntimeException("insert OrderProductStatusHistory Error");
            }
        }   // end of for

        // 7. create OrderViewDto & return
        return OrderViewDto.from(orderDto, orderProductDtoList);
    }

    /*
    method name : getOrderCountByStatus
    description : 주문 상태별 주문 목록 조회
    parameter
    - user_id : String (사용자 아이디)
    return
     */
    @Override
    public OrderCountDto getOrderCountByStatus(String user_id) {
        // 1. selectOrderCount

        // total count
        OrderCountCondition totalCountCondition = OrderCountCondition.from(
                null,
                null,
                null,
                user_id);
        int totalOrderCount = orderDao.selectOrderCount(totalCountCondition);

        // order wait count
        Integer orderWaitCodeId = codeService.findByCode(OrderStatus.ORDER_WAIT.getCode()).getCode_id();
        OrderCountCondition orderWaitCountCondition = OrderCountCondition.from(
                orderWaitCodeId,
                null,
                null,
                user_id);
        int orderWaitCount = orderDao.selectOrderCount(orderWaitCountCondition);

        // delivery doing count
        Integer deliveryDoingCodeId = codeService.findByCode(DeliveryStatus.DELIVERY_DOING.getCode()).getCode_id();
        OrderCountCondition deliveryDoingCountCondition = OrderCountCondition.from(
                null,
                deliveryDoingCodeId,
                null,
                user_id);
        int deliveryDoingCount = orderDao.selectOrderCount(deliveryDoingCountCondition);

        // delivery done count
        Integer deliveryDoneCodeId = codeService.findByCode(DeliveryStatus.DELIVERY_DONE.getCode()).getCode_id();
        OrderCountCondition deliveryDoneCountCondition = OrderCountCondition.from(
                null,
                deliveryDoneCodeId,
                null,
                user_id);
        int deliveryDoneCount = orderDao.selectOrderCount(deliveryDoneCountCondition);

        OrderCountDto orderCountDto = OrderCountDto.from(
                totalOrderCount,
                orderWaitCount,
                deliveryDoingCount,
                deliveryDoneCount);

        return orderCountDto;
    }

    /*
    method name : getOrderData
    description : 주문 데이터 조회 (데이터 변경 X)
    parameter
    - orderCreateDto: OrderCreateDto
    return : OrderViewDto
     */
    @Override
    public OrderViewDto getOrderData(OrderCreateDto orderCreateDto) {
        String cust_id = orderCreateDto.getCust_id();
        Integer delivery_fee = orderCreateDto.getDelivery_fee();
        List<OrderItemDto> orderItemDtoList = orderCreateDto.getOrderItemDtoList();

        // 1. OrderCreateDto List -> OrderProductDto List
        List<OrderProductDto> ordProdDtoList = new ArrayList<>();
        for (OrderItemDto itemDto : orderItemDtoList) {
            // select book from bookService (if book is not exist, throw exception)
            BookDto bookDto = bookService.read(itemDto.getIsbn());
            if (bookDto == null) {
                String msg = String.format("book is not exist. isbn: %s", itemDto.getIsbn());
                throw new IllegalArgumentException(msg); //
            }

            // create OrderProductDto and add List
            OrderProductDto orderProductDto = OrderProductDto.from(
                    itemDto,
                    bookDto,
                    cust_id,
                    null,
                    null,
                    null);
            ordProdDtoList.add(orderProductDto);
        }

        // 2. calculate OrderPrice
        int totalProductPrice = 0;
        int totalDiscountPrice = 0;
        int totalOrderPrice = 0;

        // calculate totalOrderPrice, totalDiscountPrice, totalProductPrice
        for (OrderProductDto orderProductDto : ordProdDtoList) {
            totalProductPrice += orderProductDto.getBasic_pric() * orderProductDto.getItem_quan();
            totalDiscountPrice += orderProductDto.getDisc_pric() * orderProductDto.getItem_quan();
            totalOrderPrice += orderProductDto.getOrd_pric();
        }

        // 3. OrderDto create & insert
        // - set OrderPrice
        OrderDto orderDto = OrderDto.from(
                cust_id,
                totalProductPrice,
                totalDiscountPrice,
                totalOrderPrice,
                delivery_fee);

        OrderViewDto orderViewDto = OrderViewDto.from(orderDto, ordProdDtoList);
        return orderViewDto;
    }
}
