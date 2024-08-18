package cart.service;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.cart.CartService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/*
=== 기능 요구 사항



=== 동작 과정
- cart_seq, isbn, prod_type_code, isPlus 을 받는다.
-- 3개의 매개변수가 무조건 들어와야 한다. => 3개가 없으면 IllegalArgumentException

- CartProduct 를 조회한다.
-- 조회해서 없으면 fail

- 받아온 매개변수를 통해서 item_quan 을 추가할지 감소시킬지 결정한다.
-- 추가
-- 감소
--- 감소시킬 때 1이하의 숫자이면 무시한다.

- CartProductDao 를 통해서 item_quan 을 변경한다.

- 변경완료 확인
-- 변경 성공 => 성공한 코드를 반환한다.
-- 변경 실패 => 실패한 코드를 반환한다.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceUpdateItemQuantityTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartProductDao cartProductDao;

    private final String TEST_USER = "CAPR_UP_ITEM_QUAN";
    private final String TEST_ISBN = "1000000000119";
    private final String PRINTED = BookType.PRINTED.getCode();
    private final int FAIL = 0;
    private final int SUCCESS = 1;
    private final boolean PLUS = true;
    private final boolean MINUS = false;
    private final int ZERO = 0;
    private final int SINGLE = 1;
    private final int FIVE = 5;

    @Before
    public void setUp() throws Exception {
        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(cartSeq);
        int insertResult = cartService.addCartProduct(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
        assertEquals(SUCCESS, insertResult);
    }

    @After
    public void tearDown() throws Exception {
        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(cartSeq);

        int deleteCartProductResult = cartProductDao.deleteByCartSeq(cartSeq);
        assertTrue(deleteCartProductResult > 0);
        int deleteCartResult = cartProductDao.deleteByCartSeq(cartSeq);
    }


    @Test
    public void 필수매개변수없음() {
        // cartSeq 를 찾는다.
        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(cartSeq);

        // cartSeq 로 주문상품
//        cartProductDao.selectListByCartSeq()


        // 3개의 매개변수가 무조건 들어와야 한다. => 3개가 없으면 IllegalArgumentException
        // cartSeq
        try {
            int updateResult = cartService.updateItemQuantity(null, TEST_ISBN, PRINTED, FIVE, TEST_USER);
            assertEquals(SUCCESS, updateResult);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // isbn
        try {
            // 3개의 매개변수가 무조건 들어와야 한다. => 3개가 없으면 IllegalArgumentException
            int updateResult = cartService.updateItemQuantity(cartSeq, null, PRINTED, FIVE, TEST_USER);
            assertEquals(SUCCESS, updateResult);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // prod_type_code
        try {
            // 3개의 매개변수가 무조건 들어와야 한다. => 3개가 없으면 IllegalArgumentException
            int updateResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, null, FIVE, TEST_USER);
            assertEquals(SUCCESS, updateResult);
            fail();
        } catch (IllegalArgumentException iae) {
        }


        // isPlus
        try {
            // 3개의 매개변수가 무조건 들어와야 한다. => 3개가 없으면 IllegalArgumentException
            int updateResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, null, TEST_USER);
            assertEquals(SUCCESS, updateResult);
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

//        - CartProduct 를 조회한다.
//        -- 조회해서 없으면 fail
    @Test(expected = IllegalArgumentException.class)
    public void 없는장바구니상품조회() {
        // 없는 조합을 업데이트 치려고 하면 막아야한다? 아니면 무시? => 막는다.
        Integer FAIL_CART_SEQ = -99999999;
        String FAIL_TEST_ISBN = "실패하는 ISBN";
        String FAIL_BOOK_TYPE = "NOTYPE";

        int updateResult = cartService.updateItemQuantity(FAIL_CART_SEQ, FAIL_TEST_ISBN, FAIL_BOOK_TYPE, FIVE, TEST_USER);
        assertEquals(SUCCESS, updateResult);
        fail();
    }
//
////        - 받아온 매개변수를 통해서 item_quan 을 추가할지 감소시킬지 결정한다.
////                -- 추가
////                -- 감소
////                --- 감소시킬 때 1이하의 숫자이면 무시한다.
//    @Test
//    public void isPlus에따라서변경하기() {
//        // cartSeq 를 찾는다.
//        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
//        assertNotNull(cartSeq);
//
//        // 비교하기 위한 초기상태 조회
//        List<CartProductDto> initItemList = cartProductDao.selectListByCartSeq(cartSeq);
//        CartProductDto initCartProductDto = initItemList.get(0);
//        Integer initQuantity = initCartProductDto.getItem_quan();
//
//
//        // item_quan 을 +1 해준다.
//        int initUpdateResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, PLUS, TEST_USER);
//        assertEquals(SUCCESS, initUpdateResult);
//
//        // 비교하기 위한 이후상태 조회
//        List<CartProductDto> secondItemList = cartProductDao.selectListByCartSeq(cartSeq);
//        assertFalse(secondItemList.isEmpty());
//        CartProductDto secondCartProductDto = secondItemList.get(0);
//        Integer secondQuantity = secondCartProductDto.getItem_quan();
//
//        // 비교
//        assertEquals(initQuantity + 1, secondQuantity.intValue());
//
//        // 5회 증가
//        for (int i = 0; i < FIVE; i++) {
//            // item_quan 을 +1 해준다.
//            int fiveUpdateResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, PLUS, TEST_USER);
//            assertEquals(SUCCESS, fiveUpdateResult);
//        }
//
//        // 비교를 위한 이후상태 조회
//        List<CartProductDto> thirdItemList = cartProductDao.selectListByCartSeq(cartSeq);
//        assertFalse(thirdItemList.isEmpty());
//        CartProductDto thirdCartProductDto = thirdItemList.get(0);
//        Integer thirdQuantity = thirdCartProductDto.getItem_quan();
//
//        assertEquals(secondQuantity + FIVE, thirdQuantity.intValue());
//
//
//        // -1
//        // item_quan 을 -1 해준다.
//        int fourthResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, MINUS, TEST_USER);
//        assertEquals(SUCCESS, fourthResult);
//
//        // 비교를 위한 이후상태 조회
//        List<CartProductDto> fourthItemList = cartProductDao.selectListByCartSeq(cartSeq);
//        assertFalse(fourthItemList.isEmpty());
//        CartProductDto fourthCartProductDto = fourthItemList.get(0);
//        Integer fourthQuantity = fourthCartProductDto.getItem_quan();
//        assertEquals(thirdQuantity - 1, fourthQuantity.intValue());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void 감소시킬때1이하이면예외처리 () {
//        // cartSeq 를 찾는다.
//        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
//        assertNotNull(cartSeq);
//
//        // 비교를 위한 cartProduct 조회
//        List<CartProductDto> initItemList = cartProductDao.selectOneList(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
//        assertFalse(initItemList.isEmpty());
//        CartProductDto initCartProductDto = initItemList.get(0);
//        Integer initQuantity = initCartProductDto.getItem_quan();
//
//        // item_quan 을 확인하고 1로 만든다.
//        if (initQuantity > 1) {
//            int repeat = initQuantity - 1;
//
//            for (int i = 0; i < repeat; i++) {
//                int minusResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, MINUS, TEST_USER);
//                assertEquals(SUCCESS, minusResult);
//            }
//        }
//        // 비교를 위한 cartProduct 조회
//        List<CartProductDto> secondItemList = cartProductDao.selectOneList(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
//        assertFalse(secondItemList.isEmpty());
//        CartProductDto secondCartProductDto = secondItemList.get(0);
//        Integer secondQuantity = secondCartProductDto.getItem_quan();
//        assertEquals(SINGLE, secondQuantity.intValue());
//
//        // MINUS 를 해준다.
//        int minusResult = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, MINUS, TEST_USER);
//        assertEquals(SUCCESS, minusResult);
//
//        // item_quan == 1 이면 통과
//        // 비교를 위한 cartProduct 조회
//        List<CartProductDto> thirdItemList = cartProductDao.selectOneList(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
//        assertFalse(thirdItemList.isEmpty());
//        CartProductDto thirdCartProductDto = thirdItemList.get(0);
//        Integer thirdQuantity = thirdCartProductDto.getItem_quan();
//        assertEquals(SINGLE, thirdQuantity.intValue());
//    }

    @Test
    public void 업데이트개수가1이하면무시하고1을반환한다 () {
        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(cartSeq);

        int updateItemQuantity = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, ZERO, TEST_USER);
        assertEquals(SINGLE, updateItemQuantity);
    }

    @Test
    public void 개수업데이트 () {
        Integer cartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(cartSeq);

        // 개수 올리기
        for (int updateCount = 1; updateCount < FIVE + 1; updateCount++) {
            int updateItemQuantity = cartService.updateItemQuantity(cartSeq, TEST_ISBN, PRINTED, updateCount, TEST_USER);
            assertEquals(updateCount, updateItemQuantity);
        }

        // 검증하기
        List<CartProductDto> selectedList =  cartProductDao.selectOneList(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
        CartProductDto selectedDto = selectedList.get(0);

        assertEquals(FIVE, selectedDto.getItem_quan().intValue());
    }
}
