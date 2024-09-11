package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dao.global.CodeDao;
import com.fastcampus.ch4.domain.order.OrderProductSearchCondition;
import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.dto.order.OrderProductStatusHistoryDto;
import com.fastcampus.ch4.util.GenerateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderProductStatusHistoryDaoImplTest {
    @Autowired
    OrderProductStatusHistoryDao orderProductStatusHistoryDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderProductDao orderProductDao;

    @Autowired
    CodeDao codeDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int FAIL_CODE = 0; // Query Execute Fail

    final String USER_ID_NULL = null; // user id null
    final String CREATE_USER_ID = "createOrdProdHist"; // 주문 생성 시 사용할 userId
    final String SELECT_USER_ID = "selectOrdProdHist"; // 주문 조회 시 사용할 userId
    final String UPDATE_USER_ID = "updateOrdProdHist"; // 주문 변경 시 사용할 userId
    final String DELETE_USER_ID = "deleteOrdProdHist"; // 주문 삭제 시 사용할 userId

    final String ORD_CODE = "ord-stat-01";
    final String DELI_CODE = "deli-stat-01";
    final String PAY_CODE = "pay-stat-01";

    final String PAPER_CODE = "paper";

    // 코드를 받아서 코드 id 를 반환하는 메서드
    public Integer getStatus(String code) {
        return codeDao.selectByCode(code).getCode_id();
    }

    /*
    이름 : orderDtoCreate
    역할 : OrderDto 클래스의 인스턴스를 생성하여 초기화해 주는 메서드
    매개변수
        String userId : 주문을 생성할 사용자의 아이디
    반환값 : OrderDto 클래스의 인스턴스
     */
    public OrderDto createOrderDto(String custId) {
        OrderDto orderDto = new OrderDto();
        orderDto.setCust_id(custId);
        orderDto.setOrd_stat(getStatus(ORD_CODE));
        orderDto.setDeli_stat(getStatus(DELI_CODE));
        orderDto.setPay_stat(getStatus(PAY_CODE));
        orderDto.setReg_id(custId);
        orderDto.setUp_id(custId);
        return orderDto;
    }

    /*
    메서드 이름 : FakeBookRepo
    역할 : 가짜 책 정보를 생성하여 반환하는 메서드
    반환값 : 가짜 책 정보 리스트 (List<BookDto>, 4개)
     */
    public List<BookDto> FakeBookRepo() {
        List<BookDto> bookRepo = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            bookRepo.add(new BookDto(
                    "isbn" + i,                  // isbn
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

    /*
    메서드 이름 : orderProductDtoCreate
    역할 : OrderProductDto 클래스의 인스턴스를 생성하여 초기화해 주는 메서드
    매개변수
        Integer ordSeq : 주문 번호
        String custId : 주문 상품을 생성할 사용자의 아이디
        Integer itemQuan : 주문 상품의 수량
        BookDto bookDto : 주문 상품의 정보
        String prodTypeCode : 주문 상품의 타입 코드
    반환값 : OrderProductDto 클래스의 인스턴스
    진행 과정
        1. OrderProductDto 클래스의 인스턴스를 생성한다.
        2. OrderProductDto 클래스의 인스턴스에 매개변수로 받은 값을 초기화한다.
        3. OrderProductDto 클래스의 인스턴스를 반환한다.
    주의 사항
        TEST 시에는 prodTypeCode 를 paper(종이책) 으로 설정한다. 이에 따라 할인율, 적립율도 paper 에 맞게 설정한다.
     */
    public OrderProductDto createOrderProductDto(Integer ordSeq, String custId, Integer itemQuan, BookDto bookDto, String prodTypeCode) {
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setOrd_seq(ordSeq);
        orderProductDto.setCust_id(custId);
        orderProductDto.setItem_quan(itemQuan);

        orderProductDto.setProd_type_code(prodTypeCode);

        orderProductDto.setOrd_stat(getStatus(ORD_CODE));
        orderProductDto.setDeli_stat(getStatus(DELI_CODE));
        orderProductDto.setPay_stat(getStatus(PAY_CODE));

        orderProductDto.setIsbn(bookDto.getIsbn());
        orderProductDto.setBook_title(bookDto.getTitle());
        orderProductDto.setPoint_perc(bookDto.getPapr_point());
        orderProductDto.setPoint_pric((int) (bookDto.getPapr_pric() * bookDto.getPapr_point()));
        orderProductDto.setDisc_perc(bookDto.getPapr_disc());
        orderProductDto.setDisc_pric((int) (bookDto.getPapr_pric() * bookDto.getPapr_disc()));
        orderProductDto.setBasic_pric(bookDto.getPapr_pric());
        orderProductDto.setSale_pric(orderProductDto.getBasic_pric() - orderProductDto.getDisc_pric());
        orderProductDto.setOrd_pric(orderProductDto.getSale_pric() * orderProductDto.getItem_quan());
        orderProductDto.setRepre_img(bookDto.getRepre_img());

        orderProductDto.setReg_id(custId);
        orderProductDto.setUp_id(custId);
        return orderProductDto;
    }

    /*
    메서드 이름 : createOrderProductStatusHistoryDto
    역할 : OrderProductStatusHistoryDto 인스턴스 생성
    매개변수
        String chg_start_date,
        String chg_end_date,
        OrderProductDto orderProductDto
    반환값 : OrderProductStatusHistoryDto
     */
    public OrderProductStatusHistoryDto createOrderProductStatusHistoryDto(String chg_start_date, String chg_end_date, OrderProductDto orderProductDto) {
        OrderProductStatusHistoryDto orderProductStatusHistoryDto = new OrderProductStatusHistoryDto();
        orderProductStatusHistoryDto.setOrd_prod_seq(orderProductDto.getOrd_prod_seq());
        orderProductStatusHistoryDto.setChg_start_date(chg_start_date);
        orderProductStatusHistoryDto.setChg_end_date(chg_end_date);
        orderProductStatusHistoryDto.setOrd_stat(getStatus(ORD_CODE));
        orderProductStatusHistoryDto.setDeli_stat(getStatus(DELI_CODE));
        orderProductStatusHistoryDto.setPay_stat(getStatus(PAY_CODE));
        orderProductStatusHistoryDto.setReg_id(orderProductDto.getReg_id());
        orderProductStatusHistoryDto.setUp_id(orderProductDto.getUp_id());
        return orderProductStatusHistoryDto;
    }

    /*
    메서드 이름 : beforeDelete
    역할 : 테스트 시작 전 삭제
    매개변수 : 없음
    반환값 : 없음
    진행 과정
        1. CREATE_USER_ID, SELECT_USER_ID, UPDATE_USER_ID, DELETE_USER_ID 에 해당하는 주문상품 삭제
      */
    @Before
    public void beforeDelete() {
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("cust_id", CREATE_USER_ID);
        int deleteCreate = orderProductStatusHistoryDao.deleteHistoryByCondition(deleteMap);
        deleteMap.put("cust_id", SELECT_USER_ID);
        int deleteSelect = orderProductStatusHistoryDao.deleteHistoryByCondition(deleteMap);
        deleteMap.put("cust_id", UPDATE_USER_ID);
        int deleteUpdate = orderProductStatusHistoryDao.deleteHistoryByCondition(deleteMap);
        deleteMap.put("cust_id", DELETE_USER_ID);
        int deleteDelete = orderProductStatusHistoryDao.deleteHistoryByCondition(deleteMap);
    }

    /*
    메서드 이름 : 주문상태이력_단건입력
    매개변수 : 없음
    반환값 : 없음
    진행과정
        1. 주문 생성 및 삽입
            1-1. OrderDto 생성
            1-2. orderDao.insertOrder(orderDto) 실행
            1-3. 주문 삽입 확인 (assertEquals(SUCCESS_CODE, orderDao.insertOrder(orderDto)))
        2. 주문 상품 생성 및 삽입
            2-1. OrderProductDto 생성
            2-2. orderProductDao.insertOrderProduct(orderProductDto) 실행
            2-3. 주문 상품 삽입 확인(assertEquals(SUCCESS_CODE, orderProductDao.insertOrderProduct(orderProductDto)))
        3. 주문 상품 상태 이력 생성 및 삽입
            3-1. OrderProductStatusHistoryDto 생성
            3-2. orderProductStatusHistoryDao.insertOrderProductStatusHistory(orderProductStatusHistoryDto) 실행
            3-3. 주문 상품 상태 이력 삽입 확인(assertEquals(SUCCESS_CODE, orderProductStatusHistoryDao.insertOrderProductStatusHistory(orderProductStatusHistoryDto)))
        4. 주문 상품 상태 이력 조회
            4-1. orderProductStatusHistoryDao.selectOrderProductStatusHistoryByCondition(map) 실행
            4-2. 주문 상품 상태 이력 조회 확인
     */
    @Test
    public void 주문상태이력_단건입력() {
        // 1. 주문 생성 및 삽입

        // 1-1. OrderDto 생성
        OrderDto orderDto = createOrderDto(CREATE_USER_ID);

        // 1-2. orderDao.insertOrder(orderDto) 실행
        int orderResult = orderDao.insertOrder(orderDto);

        // 1-3. 주문 삽입 확인
        assertEquals(SUCCESS_CODE, orderResult);

        // 2. 주문 상품 생성 및 삽입

        // 2-1. OrderProductDto 생성
        List<BookDto> bookRepo = FakeBookRepo();
        BookDto bookDto = bookRepo.get(0);
        OrderProductDto orderProductDto = createOrderProductDto(orderDto.getOrd_seq(), CREATE_USER_ID, 1, bookDto, PAPER_CODE);

        // 2-2. orderProductDao.insertOrderProduct(orderProductDto) 실행
        int ordProdResult = orderProductDao.insertOrderProduct(orderProductDto);

        // 2-3. 주문 상품 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdResult);

        // 3. 주문 상품 상태 이력 생성 및 삽입

        // 3-1. OrderProductStatusHistoryDto 생성
        String change_start = GenerateTime.getCurrentTime();
        String change_end = GenerateTime.getMaxTime();
        OrderProductStatusHistoryDto orderProductStatusHistoryDto = createOrderProductStatusHistoryDto(change_start, change_end, orderProductDto);

        // 3-2. orderProductStatusHistoryDao.insertOrderProductStatusHistory(orderProductStatusHistoryDto) 실행
        int ordProdStatHistResult =  orderProductStatusHistoryDao.insertHistory(orderProductStatusHistoryDto);

        // 3-3. 주문 상품 상태 이력 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdStatHistResult);

        // 4. 주문 상품 상태 이력 조회

        // 4-1. orderProductStatusHistoryDao.selectOrderProductStatusHistoryByCondition(map) 실행
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("ord_prod_stat_hist_seq", orderProductStatusHistoryDto.getOrd_prod_stat_hist_seq());
        List<OrderProductStatusHistoryDto> orderProductStatusHistoryDtoList = orderProductStatusHistoryDao.selectHistoryByCondition(selectMap);
        assertFalse(orderProductStatusHistoryDtoList.isEmpty());
        OrderProductStatusHistoryDto orderProductStatusHistoryDtoResult = orderProductStatusHistoryDtoList.get(0);

        // 4-2. 주문 상품 상태 이력 조회 확인
        assertTrue(orderProductStatusHistoryDto.equals(orderProductStatusHistoryDtoResult));
    }

    /*
    메서드 이름 : 주문상태이력_다건입력상황에서의변경
    매개변수 : 없음
    반환값 : 없음
    진행과정
        1. 주문 생성 및 삽입
            1-1. OrderDto 생성
            1-2. orderDao.insertOrder(orderDto) 실행
            1-3. 주문 삽입 확인 (assertEquals(SUCCESS_CODE, orderDao.insertOrder(orderDto)))
        2. 주문 상품 생성 및 삽입
            2-1. OrderProductDto 생성
            2-2. orderProductDao.insertOrderProduct(orderProductDto) 실행
            2-3. 주문 상품 삽입 확인(assertEquals(SUCCESS_CODE, orderProductDao.insertOrderProduct(orderProductDto)))
        3. 주문 상품 상태 이력 생성 및 삽입
            3-1. OrderProductStatusHistoryDto 생성
            3-2. orderProductStatusHistoryDao.insertOrderProductStatusHistory(orderProductStatusHistoryDto) 실행
            3-3. 주문 상품 상태 이력 삽입 확인(assertEquals(SUCCESS_CODE, orderProductStatusHistoryDao.insertOrderProductStatusHistory(orderProductStatusHistoryDto)))
        4. 주문 상품 상태 변경
            4-1. OrderProductDto 조회
            4-2. orderProductDao.updateOrderProduct(orderProductDto) 실행
            4-3. OrderProductStatusHistoryDto 생성 및 삽입
            4-4. 기존 주문 상품 상태 이력 중 가장 최근의 이력을 조회하여 변경
            4-5. orderProductStatusHistoryDao.updateOrderProductStatusHistory(orderProductStatusHistoryDto) 실행
            4-6. 주문 상품 상태 변경 확인
        5. 주문 상품 상태 이력 조회
     */
    @Test
    public void 주문상태이력_다건입력상황에서의변경() {
        // 1. 주문 생성 및 삽입
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);
        int orderResult = orderDao.insertOrder(orderDto);
        assertEquals(SUCCESS_CODE, orderResult);

        // 2. 주문 상품 생성 및 삽입
        List<BookDto> bookRepo = FakeBookRepo();
        BookDto bookDto = bookRepo.get(0);

        // 2-1. OrderProductDto 생성
        OrderProductDto orderProductDto = createOrderProductDto(orderDto.getOrd_seq(), UPDATE_USER_ID, 1, bookDto, PAPER_CODE);

        // 2-2. orderProductDao.insertOrderProduct(orderProductDto) 실행
        int ordProdResult = orderProductDao.insertOrderProduct(orderProductDto);

        // 2-3. 주문 상품 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdResult);

        // 3. 주문 상품 상태 이력 생성 및 삽입

        // 3-1. OrderProductStatusHistoryDto 생성
        String change_start = GenerateTime.getCurrentTime();
        String change_end = GenerateTime.getMaxTime();
        OrderProductStatusHistoryDto orderProductStatusHistoryDto = createOrderProductStatusHistoryDto(change_start, change_end, orderProductDto);

        // 3-2. orderProductStatusHistoryDao.insertOrderProductStatusHistory(orderProductStatusHistoryDto) 실행
        int ordProdStatHistResult =  orderProductStatusHistoryDao.insertHistory(orderProductStatusHistoryDto);

        // 3-3. 주문 상품 상태 이력 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdStatHistResult);

        // 4. 주문 상품 상태 변경

        // 4-1. OrderProductDto 조회
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("ord_prod_seq", orderProductDto.getOrd_prod_seq());
        OrderProductSearchCondition searchCondition = OrderProductSearchCondition.from(
                null,
                null,
                null,
                null,
                null,
                UPDATE_USER_ID);
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(searchCondition);

        // 4-2. orderProductDao.updateOrderProduct(orderProductDto) 실행
        OrderProductDto orderProductDtoUpdate = orderProductDtoList.get(0);
        orderProductDtoUpdate.setOrd_stat(getStatus("ord-stat-02"));
        orderProductDtoUpdate.setDeli_stat(getStatus("deli-stat-02"));
        orderProductDtoUpdate.setPay_stat(getStatus("pay-stat-02"));
        int ordProdUpdateResult = orderProductDao.updateOrderProduct(orderProductDtoUpdate);
        assertEquals(SUCCESS_CODE, ordProdUpdateResult);

        // 4-3. OrderProductStatusHistoryDto 생성 및 삽입
        String change_start_update = GenerateTime.getCurrentTime();
        String change_end_update = GenerateTime.getMaxTime();
        OrderProductStatusHistoryDto orderProductStatusHistoryDtoUpdate = createOrderProductStatusHistoryDto(change_start_update, change_end_update, orderProductDtoUpdate);
        int ordProdStatHistCreateResult = orderProductStatusHistoryDao.insertHistory(orderProductStatusHistoryDtoUpdate);
        assertEquals(SUCCESS_CODE, ordProdStatHistCreateResult);

        // 4-4. 기존 주문 상품 상태 이력 중 가장 최근의 이력을 조회하여 변경
        List<OrderProductStatusHistoryDto> orderProductStatusHistoryDtoList = orderProductStatusHistoryDao.selectHistoryByCondition(selectMap);
        OrderProductStatusHistoryDto orderProductStatusHistoryDtoRecent = orderProductStatusHistoryDtoList.get(0);
        orderProductStatusHistoryDtoRecent.setChg_end_date(change_start_update);

        // 4-5. orderProductStatusHistoryDao.updateOrderProductStatusHistory(orderProductStatusHistoryDto) 실행
        int ordProdStatHistUpdateResult = orderProductStatusHistoryDao.updateHistory(orderProductStatusHistoryDtoRecent);
        assertEquals(SUCCESS_CODE, ordProdStatHistUpdateResult);

        // 4-6. 주문 상품 상태 변경 확인
        Map<String, Object> selectMapUpdate = new HashMap<>();
        selectMapUpdate.put("ord_prod_seq", orderProductDtoUpdate.getOrd_prod_seq());
        OrderProductSearchCondition searchConditionUpdate = OrderProductSearchCondition.from(
                orderProductDtoUpdate.getOrd_prod_seq(),
                null,
                null,
                null,
                null,
                null);
        List<OrderProductDto> orderProductDtoListUpdate = orderProductDao.selectOrderProductByCondition(searchConditionUpdate);
        assertFalse(orderProductDtoListUpdate.isEmpty());
        OrderProductDto orderProductDtoResult = orderProductDtoListUpdate.get(0);
        assertTrue(orderProductDtoUpdate.equals(orderProductDtoResult));

        // 5. 주문 상품 상태 이력 조회
        // orderProductStatusHistoryDto : 첫 주문 상품 상태 변경 이력
        // orderProductStatusHistoryDtoUpdate : 두 번째 주문 상품 상태 변경 이력
        // orderProductStatusHistoryDtoRecent : 최근 주문 상품 상태 변경 이력

        // 5-1. 이전 주문 상품 상태 이력 비교
        Map<String, Object> beforeMap = new HashMap<>();
        selectMapUpdate.put("ord_prod_stat_hist_seq", orderProductStatusHistoryDtoRecent.getOrd_prod_stat_hist_seq());
        List<OrderProductStatusHistoryDto> orderProductStatusHistoryDtoListBefore = orderProductStatusHistoryDao.selectHistoryByCondition(beforeMap);
        assertFalse(orderProductStatusHistoryDtoListBefore.isEmpty());

        OrderProductStatusHistoryDto orderProductStatusHistoryDtoBefore = orderProductStatusHistoryDtoListBefore.get(0);
        assertTrue(orderProductStatusHistoryDtoRecent.equals(orderProductStatusHistoryDtoBefore));
    }
}
