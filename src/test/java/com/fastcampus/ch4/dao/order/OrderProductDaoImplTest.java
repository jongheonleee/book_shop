package com.fastcampus.ch4.dao.order;

import com.fastcampus.ch4.dao.global.CodeDao;
import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
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
public class OrderProductDaoImplTest {
    @Autowired
    OrderProductDao orderProductDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CodeDao codeDao;

    final int SUCCESS_CODE = 1; // Query Execute Success
    final int FAIL_CODE = 0; // Query Execute Fail

    final String USER_ID_NULL = null; // user id null
    final String CREATE_USER_ID = "createOrdProd"; // 주문 생성 시 사용할 userId
    final String SELECT_USER_ID = "selectOrdProd"; // 주문 조회 시 사용할 userId
    final String UPDATE_USER_ID = "updateOrdProd"; // 주문 변경 시 사용할 userId
    final String DELETE_USER_ID = "deleteOrdProd"; // 주문 삭제 시 사용할 userId

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
        int deleteCreate = orderProductDao.deleteOrderProductByCondition(deleteMap);
        deleteMap.put("cust_id", SELECT_USER_ID);
        int deleteSelect = orderProductDao.deleteOrderProductByCondition(deleteMap);
        deleteMap.put("cust_id", UPDATE_USER_ID);
        int deleteUpdate = orderProductDao.deleteOrderProductByCondition(deleteMap);
        deleteMap.put("cust_id", DELETE_USER_ID);
        int deleteDelete = orderProductDao.deleteOrderProductByCondition(deleteMap);
    }


    /*
    메서드 이름 : 주문상품_단건입력
    역할 : 주문상품 입력 테스트
    매개변수 : 없음
    반환값 : 없음
    진행 과정
        1. 주문 생성
            1-1. OrderDto 생성
            1-2. OrderDto 삽입
            1-3. OrderDto 삽입 확인
        2. 주문 상품 생성
            2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
            2-2. OrderProductDto 생성
            2-3. OrderProductDto 삽입
            2-4. OrderProductDto 삽입 확인
        3. 주문 상품 비교
            3-1. OrderProductDto 조회
            3-2. OrderProductDto 비교
     */
    @Test
    public void 주문상품_단건입력() {
        // 1. 주문 생성
        // 1-1. OrderDto 생성
        OrderDto orderDto = createOrderDto(CREATE_USER_ID);

        // 1-2. OrderDto 삽입
        int ordInsertResult = orderDao.insertOrder(orderDto);

        // 1-3. OrderDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordInsertResult);

        // 2. 주문 상품 생성
        // 2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
        List<BookDto> bookRepo = FakeBookRepo();
        BookDto bookDto = bookRepo.get((int) (Math.random() * bookRepo.size()));

        // 2-2. OrderProductDto 생성
        int itemQuan = 1;
        OrderProductDto orderProductDto = createOrderProductDto(orderDto.getOrd_seq(), CREATE_USER_ID, itemQuan, bookDto, PAPER_CODE);

        // 2-3. OrderProductDto 삽입
        int ordProdInsertResult = orderProductDao.insertOrderProduct(orderProductDto);

        // 2-4. OrderProductDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdInsertResult);

        // 3. 주문 상품 비교
        // 3-1. OrderProductDto 조회
        Map<String, Object> selectMap = Map.of("ord_prod_seq", orderProductDto.getOrd_prod_seq());
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
        assertFalse(orderProductDtoList.isEmpty());

        // 3-2. OrderProductDto 비교
        OrderProductDto selectedOrderProductDto = orderProductDtoList.get(0);
        assertEquals(orderProductDto.getOrd_prod_seq(), selectedOrderProductDto.getOrd_prod_seq());
    }

    /*
    메서드 이름 : 주문상품_다건입력
    역할 : 주문상품 다건 입력 테스트
    매개변수 : 없음
    반환값 : 없음
    진행 과정
        1. 주문 생성
            1-1. OrderDto 생성
            1-2. OrderDto 삽입
            1-3. OrderDto 삽입 확인
        2. 주문 상품 생성 (3회 반복)
            2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
            2-2. OrderProductDto 생성
            2-3. OrderProductDto 삽입
            2-4. OrderProductDto 삽입 확인
            2-5. OrderProductDto 조회
            2-6. OrderProductDto 비교
        3. 주문 상품 확인
            3-1. OrderProductDto 조회 (ord_seq 로 조회)
            3-2. 삽입한 개수 비교
     */
    @Test
    public void 주문상품_다건입력() {
        // 1. 주문 생성
        // 1-1. OrderDto 생성
        OrderDto orderDto = createOrderDto(CREATE_USER_ID);

        // 1-2. OrderDto 삽입
        int ordInsertResult = orderDao.insertOrder(orderDto);

        // 1-3. OrderDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordInsertResult);

        // 2. 주문 상품 생성 (3회 반복)
        int repeatCount = 3;
        for (int i = 0; i < repeatCount; i++) {
            // 2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
            List<BookDto> bookRepo = FakeBookRepo();
            BookDto bookDto = bookRepo.get(i); // 중복으로 들어갈 수 있으므로

            // 2-2. OrderProductDto 생성
            int itemQuan = (int) (Math.random() * 5);
            OrderProductDto orderProductDto = createOrderProductDto(orderDto.getOrd_seq(), CREATE_USER_ID, itemQuan, bookDto, PAPER_CODE);

            // 2-3. OrderProductDto 삽입
            int ordProdInsertResult = orderProductDao.insertOrderProduct(orderProductDto);

            // 2-4. OrderProductDto 삽입 확인
            assertEquals(SUCCESS_CODE, ordProdInsertResult);

            // 2-5. OrderProductDto 조회
            Map<String, Object> selectMap = Map.of("ord_prod_seq", orderProductDto.getOrd_prod_seq());
            List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
            assertFalse(orderProductDtoList.isEmpty());

            // 2-6. OrderProductDto 비교
            OrderProductDto selectedOrderProductDto = orderProductDtoList.get(0);
            assertEquals(orderProductDto.getOrd_prod_seq(), selectedOrderProductDto.getOrd_prod_seq());
        }

        // 3. 주문 상품 확인
        // 3-1. OrderProductDto 조회 (ord_seq 로 조회)
        Map<String, Object> selectMap = Map.of("ord_seq", orderDto.getOrd_seq());
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
        assertFalse(orderProductDtoList.isEmpty());

        // 3-2. 삽입한 개수 비교
        assertEquals(repeatCount, orderProductDtoList.size());
    }

    /*
    메서드 이름 : 주문상품_단건수정
    역할 : 주문상품 단건 수정 테스트
    매개변수 : 없음
    반환값 : 없음
    진행 과정
        1. 주문 생성
            1-1. OrderDto 생성
            1-2. OrderDto 삽입
            1-3. OrderDto 삽입 확인
        2. 주문 상품 생성
            2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
            2-2. OrderProductDto 생성
            2-3. OrderProductDto 삽입
            2-4. OrderProductDto 삽입 확인
        3. 주문 상품 수정
            3-1. OrderProductDto 조회
            3-2. OrderProductDto 수정
            3-3. OrderProductDto 수정 확인 (2-2 에서 생성한 OrderProductDto 와 비교)
     */
    @Test
    public void 주문상품_단건수정() {
        // 1. 주문 생성
        // 1-1. OrderDto 생성
        OrderDto orderDto = createOrderDto(UPDATE_USER_ID);

        // 1-2. OrderDto 삽입
        int ordInsertResult = orderDao.insertOrder(orderDto);

        // 1-3. OrderDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordInsertResult);

        // 2. 주문 상품 생성
        // 2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
        List<BookDto> bookRepo = FakeBookRepo();
        BookDto bookDto = bookRepo.get((int) (Math.random() * bookRepo.size()));

        // 2-2. OrderProductDto 생성
        int itemQuan = 1;
        OrderProductDto orderProductDto = createOrderProductDto(orderDto.getOrd_seq(), UPDATE_USER_ID, itemQuan, bookDto, PAPER_CODE);

        // 2-3. OrderProductDto 삽입
        int ordProdInsertResult = orderProductDao.insertOrderProduct(orderProductDto);

        // 2-4. OrderProductDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdInsertResult);

        // 3. 주문 상품 수정
        // 3-1. OrderProductDto 조회
        Map<String, Object> selectMap = Map.of("ord_prod_seq", orderProductDto.getOrd_prod_seq());
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
        assertFalse(orderProductDtoList.isEmpty());

        // 3-2. OrderProductDto 수정
        // 변경할 값
        int changeItemQuan = 2;
        String changeOrdStat = "ord-stat-02";
        String changeDeliStat = "deli-stat-02";
        String changePayStat = "pay-stat-02";

        // 변경
        OrderProductDto selectedOrderProductDto = orderProductDtoList.get(0);
        selectedOrderProductDto.setItem_quan(changeItemQuan);
        selectedOrderProductDto.setOrd_stat(getStatus(changeOrdStat));
        selectedOrderProductDto.setDeli_stat(getStatus(changeDeliStat));
        selectedOrderProductDto.setPay_stat(getStatus(changePayStat));

        int ordProdUpdateResult = orderProductDao.updateOrderProduct(selectedOrderProductDto);
        assertEquals(SUCCESS_CODE, ordProdUpdateResult);

        // 3-3. OrderProductDto 수정 확인 (2-2 에서 생성한 OrderProductDto 와 비교)
        List<OrderProductDto> updatedOrderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
        assertFalse(updatedOrderProductDtoList.isEmpty());
        OrderProductDto updatedOrderProductDto = updatedOrderProductDtoList.get(0);
        assertEquals(selectedOrderProductDto.getOrd_prod_seq(), updatedOrderProductDto.getOrd_prod_seq());
    }

    /*
    메서드 이름 : 주문상품_단건삭제
    역할 : 주문상품 단건 삭제 테스트
    매개변수 : 없음
    반환값 : 없음
    진행 과정
        1. 주문 생성
            1-1. OrderDto 생성
            1-2. OrderDto 삽입
            1-3. OrderDto 삽입 확인
        2. 주문 상품 생성
            2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
            2-2. OrderProductDto 생성
            2-3. OrderProductDto 삽입
            2-4. OrderProductDto 삽입 확인
        3. 주문 상품 삭제
            3-1. OrderProductDto 조회
            3-2. OrderProductDto 삭제
            3-3. OrderProductDto 삭제 확인
     */
    @Test
    public void 주문상품_단건삭제() {
        // 1. 주문 생성
        // 1-1. OrderDto 생성
        OrderDto orderDto = createOrderDto(DELETE_USER_ID);

        // 1-2. OrderDto 삽입
        int ordInsertResult = orderDao.insertOrder(orderDto);

        // 1-3. OrderDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordInsertResult);

        // 2. 주문 상품 생성
        // 2-1. FakeBookRepo 에서 도서 상품 DTO 가져오기 (Math.random 활용을 통한 추출)
        List<BookDto> bookRepo = FakeBookRepo();
        BookDto bookDto = bookRepo.get((int) (Math.random() * bookRepo.size()));

        // 2-2. OrderProductDto 생성
        int itemQuan = 1;
        OrderProductDto orderProductDto = createOrderProductDto(orderDto.getOrd_seq(), DELETE_USER_ID, itemQuan, bookDto, PAPER_CODE);

        // 2-3. OrderProductDto 삽입
        int ordProdInsertResult = orderProductDao.insertOrderProduct(orderProductDto);

        // 2-4. OrderProductDto 삽입 확인
        assertEquals(SUCCESS_CODE, ordProdInsertResult);

        // 3. 주문 상품 삭제
        // 3-1. OrderProductDto 조회
        Map<String, Object> selectMap = Map.of("ord_prod_seq", orderProductDto.getOrd_prod_seq());
        List<OrderProductDto> orderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
        assertFalse(orderProductDtoList.isEmpty());

        // 3-2. OrderProductDto 삭제
        int ordProdDeleteResult = orderProductDao.deleteOrderProductByCondition(selectMap);
        assertEquals(SUCCESS_CODE, ordProdDeleteResult);

        // 3-3. OrderProductDto 삭제 확인
        List<OrderProductDto> deletedOrderProductDtoList = orderProductDao.selectOrderProductByCondition(selectMap);
        assertTrue(deletedOrderProductDtoList.isEmpty());
    }
}
