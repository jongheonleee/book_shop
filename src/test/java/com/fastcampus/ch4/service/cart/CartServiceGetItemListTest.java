package com.fastcampus.ch4.service.cart;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.dto.cart.CartProductDetailDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.dto.item.BookDto;
import com.fastcampus.ch4.model.cart.PriceHandler;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.cart.CartService;
import com.fastcampus.ch4.service.item.BookService;
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

import static org.junit.Assert.*;

/*
목록 조회 테스트

- 장바구니 id (cart_seq) 로 목록을 조회한다.
- userId 로 목록을 조회한다.
=> 매개변수가 둘 다 없다면 IllegalArgumentException

- 할인, 적립은 퍼센트만 오기 때문에 할인액, 적립액을 계산해야하고 상품판매가(상품정가 - 할인액) 을 계산해야한다.
- 가격정보는 테이블에 저장하지 않고 조회요청을 할 때마다 계산한다.

- 목록 조회의 결과는 이미지, 책 제목, 정가, 상품판매가(정가 - 할인), 적립액, 구매수량 을 포함해야한다.
=> JOIN 한 것을 사용한다.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceGetItemListTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartProductDao cartProductDao;

    private final String TEST_USER = "GET_ITEM_LIST_TEST";
    private final TempBookService bookService = new FakeBookServiceImpl();
    private final String PRINTED = BookType.PRINTED.getCode();
    private final int SINGLE = 1;
    private final int SUCCESS = 1;
    private List<BookDto> bookList = bookService.getBookList();
    private int bookListSize = bookList.size();
    private Integer testCartSeq; // test 용 장바구니 번호 (Before 에서 갱신(생성), After 에서 삭제)


    @Before
    public void setUp() throws Exception {
        // 장바구니 생성
        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);
        // 변수에 추가
        testCartSeq = cartSeq;

        // 장바구니 추가
        for (BookDto BookDto : bookList) {
            String isbn = BookDto.getIsbn();
            CartProductDto cartProductDto = CartProductDto.create(cartSeq, isbn, PRINTED, SINGLE, TEST_USER);
            int insertResult = cartProductDao.insert(cartProductDto);
            assertEquals(SUCCESS, insertResult);
        }
    }

    @After
    public void tearDown() throws Exception {
        // 삭제해야할 장바구니번호 탐색
        Map<String, String> map = new HashMap<>();
        map.put("userId", TEST_USER);
        CartDto selectedCartDto = cartDao.selectByMap(map);
        Integer deleteSeq = selectedCartDto.getCart_seq();

        // 장바구니 상품 삭제
        int deleteCartProductRow = cartProductDao.deleteByCartSeq(deleteSeq);
        assertTrue(deleteCartProductRow > 0);
        // 장바구니 삭제
        int deleteCartRow = cartDao.deleteByMap(map);
        assertTrue(deleteCartRow > 0);

        // 변수 초기화
        testCartSeq = null;
    }

    @Test
    public void 장바구니id로조회 () {
        // 조회
        List<CartProductDetailDto> itemList = cartService.getItemList(testCartSeq, null);

        // 개수 비교
        assertEquals(bookListSize, itemList.size());

        // isbn 이 제대로 나왔는지 비교
        for (CartProductDetailDto item : itemList) {
            Boolean existIsbn = bookList.stream().anyMatch(book -> book.getIsbn().equals(item.getIsbn()));
            assertTrue(existIsbn);
        }
    }

    @Test
    public void userId로조회 () {
        // 조회
        List<CartProductDetailDto> itemList = cartService.getItemList(null, TEST_USER);

        // 개수 비교
        assertEquals(bookListSize, itemList.size());

        // isbn 이 제대로 나왔는지 비교
        for (CartProductDetailDto item : itemList) {
            Boolean existIsbn = bookList.stream().anyMatch(book -> book.getIsbn().equals(item.getIsbn()));
            assertTrue(existIsbn);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void 매개변수없음예외처리 () {
        // 조회
        List<CartProductDetailDto> itemList = cartService.getItemList(null, null);
        assertEquals(bookListSize, itemList.size());

        // 조회가 되면 테스트 실패
        fail();
    }

//    @Test
//    public void 조회시가격정보포함 () {
//        // 조회
//        List<CartProductDetailDto> itemList = cartService.getItemList(null, TEST_USER);
//        // 개수 비교
//        assertEquals(bookListSize, itemList.size());
//
//        // service 로 가져온 정보와 test 에서 계산한 가격정보를 비교한다. (비교대상 : 할인액, 적립액, 상품판매가(상품정가 - 할인액))
//        for (CartProductDetailDto item : itemList) {
//            // targetBookDto 기준으로 가격정보를 하나씩 계산한다.
//            String targetIsbn = item.getIsbn();
//            BookDto targetBookDto = bookService.read(targetIsbn);
//
//            // 가격 정보 셋팅하기
//            String targetProdCodeType = item.getProd_type_code();
//            Integer basicPrice = null;
//            Integer benefitPrice = null;
//            Integer pointPrice = null;
//            if (BookType.PRINTED.isSameType(targetProdCodeType)) {
//                basicPrice = targetBookDto.getPapr_pric();
//                benefitPrice = PriceHandler.pritedBenefitPrice(targetBookDto);
//                pointPrice = PriceHandler.printedPointPrice(targetBookDto);
//            } else if (BookType.EBOOK.isSameType(targetProdCodeType)) {
//                basicPrice = (int)targetBookDto.getE_pric();
//                benefitPrice = PriceHandler.eBookBenefitPrice(targetBookDto);
//                pointPrice = PriceHandler.eBookPointPrice(targetBookDto);
//            }
//            // 가격정보가 셋팅되지 않았다면 Exception
//            assertNotNull(basicPrice);
//            assertNotNull(benefitPrice);
//            assertNotNull(pointPrice);
//
//            // 상품 판매가
//            int salePrice = basicPrice - benefitPrice;
//
//            // 가격정보 비교
//            assertEquals(basicPrice.intValue(), item.getBasicPrice());
//            assertEquals(benefitPrice.intValue(),item.getBene_pric());
//            assertEquals(pointPrice.intValue(), item.getPoint_pric());
//            assertEquals(salePrice, item.getSalePrice());
//        }
//
//    }
}
