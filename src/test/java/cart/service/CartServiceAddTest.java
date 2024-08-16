package cart.service;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.cart.CartService;
import com.fastcampus.ch4.service.order.fake.FakeBookServiceImpl;
import com.fastcampus.ch4.service.order.fake.TempBookService;
import org.junit.After;
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
장바구니 추가
- 잘못된 isbn 인 경우에는 추가 시도를 하지 않는다. => IllegalArgumentException
- 장바구니 상품은 단일 추가이며 장바구니 상품을 추가하고 장바구니 pk 를 반환한다.

- 장바구니 상품 추가 시 기본적으로 구매 수량을 1개로 맞춘다.

- 비회원 일 때 장바구니가 없으면 장바구니를 생성하여 등록한다.
- 회원일 때 장바구니가 없으면 장바구니를 생성하여 등록한다.
=> 회원 id 가 있다면 회원 id를 우선적으로 조회해서 사용한다.
- 회원의 장바구니가 존재한다면 해당하는 장바구니에 상품을 추가한다.
- 동일한 장바구니에서는 같은 도서(isbn, prod_type_code)가 추가되지 않는다.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceAddTest {
    @Autowired
    CartService cartService;
    @Autowired
    CartDao cartDao;
    @Autowired
    CartProductDao cartProductDao;

    private final TempBookService bookService = new FakeBookServiceImpl();
    private final String TEST_USER = "CART_SERVICE_TEST";
    private final String PRINTED_BOOK = BookType.PRINTED.getCode();
    private final String E_BOOK = BookType.EBOOK.getCode();
    private final int SUCCESS = 1;
    private final String TEST_ISBN_1 = "9791162245408";
    private final int SINGLE = 1;
    private final int MULTIPLE = 5;

    @After
    public void tearDown() throws Exception {
        // TEST 를 위한 장바구니 중복 생성을 없애기 위함
        List<CartDto> cartDtoList = cartDao.selectListByCondition(null, TEST_USER);

        // 장바구니 상품 삭제 및 장바구니 삭제
        for (CartDto cartDto : cartDtoList) {
            Integer cartSeq = cartDto.getCart_seq();
            cartProductDao.deleteByCartSeq(cartSeq);
            Map<String, String> map = new HashMap<>();
            map.put("cart_seq", String.valueOf(cartSeq));
            cartDao.deleteByMap(map);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void 잘못된isbn () {
        final String FAIL_ISBN = "잘못된번호";

        // create cartDto
        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);

        // cartService.add()
        Integer addSeq = cartService.add(cartSeq, FAIL_ISBN, PRINTED_BOOK, TEST_USER);
        assertEquals(cartSeq, addSeq);

        // pass = test fail
        fail();
    }

    @Test
    public void 비회원add () {
        // 비회원 생성
        // cartService.add()
        Integer addSeq = cartService.add(null, TEST_ISBN_1, PRINTED_BOOK, null);
        assertNotNull(addSeq);

        // 생성완료 체크
        Map map = new HashMap<>();
        map.put("cart_seq", addSeq);
        CartDto selectedCart = cartDao.selectByMap(map);
        assertNotNull(selectedCart);

        // 장바구니 상품 체크
        List<CartProductDto> selectedCartProductList = cartProductDao.selectListByCartSeq(addSeq);
        assertFalse(selectedCartProductList.isEmpty());
    }

    @Test
    public void 비회원장바구니있음 () {
        // 비회원 생성
        // cartService.add()
        Integer addSeq = cartService.add(null, TEST_ISBN_1, PRINTED_BOOK, null);
        assertNotNull(addSeq);

        // 생성완료 체크
        Map map = new HashMap<>();
        map.put("cart_seq", addSeq);
        CartDto selectedCart = cartDao.selectByMap(map);
        assertNotNull(selectedCart);

        // 장바구니 상품 체크
        List<CartProductDto> beforeSelectedList = cartProductDao.selectListByCartSeq(addSeq);
        assertFalse(beforeSelectedList.isEmpty());

        // do
        // cartService.add(addSeq)
        Integer afterAddSeq = cartService.add(addSeq, TEST_ISBN_1, E_BOOK, null);
        assertNotNull(addSeq);
        assertEquals(addSeq, afterAddSeq);

        // assert
        List<CartProductDto> afterSelectedList = cartProductDao.selectListByCartSeq(addSeq);
        assertFalse(afterSelectedList.isEmpty());
        assertEquals(beforeSelectedList.size() + 1, afterSelectedList.size());
    }

    @Test
    public void 회원add () {
        // 회원 장바구니 생성
        // cartService.add()
        Integer addSeq = cartService.add(null, TEST_ISBN_1, PRINTED_BOOK, TEST_USER);
        assertNotNull(addSeq);

        // 장바구니 상품 추가
        Integer afterAddSeq = cartService.add(addSeq, TEST_ISBN_1, E_BOOK, TEST_USER);
        assertEquals(addSeq, afterAddSeq);

        // 장바구니 상품 체크
        List<CartProductDto> selectedCartProductList = cartProductDao.selectListByCartSeq(addSeq);
        assertFalse(selectedCartProductList.isEmpty());
    }

    @Test
    public void 동일도서상품추가 () {
        // give
        // 장바구니 생성
        Integer addSeq = cartService.add(null, TEST_ISBN_1, PRINTED_BOOK, TEST_USER);
        assertNotNull(addSeq);

        // 동일 도서 추가
        for (int i = 0; i < MULTIPLE; i++) {
            Integer afterAddSeq = cartService.add(addSeq, TEST_ISBN_1, PRINTED_BOOK, TEST_USER);
            assertEquals(addSeq, afterAddSeq);
        }

        // 개수 비교
        List<CartProductDto> selectedCartProductList = cartProductDao.selectListByCartSeq(addSeq);
        assertFalse(selectedCartProductList.isEmpty());
        assertEquals(SINGLE, selectedCartProductList.size());
    }
}
