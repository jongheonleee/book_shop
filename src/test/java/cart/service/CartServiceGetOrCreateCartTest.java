package cart.service;


import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.model.cart.UserType;
import com.fastcampus.ch4.service.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;


/*
장바구니 추가 (상품 X)
1. 반환값은 생성했거나 조회한 장바구니 번호이다.
2. 비회원이고 장바구니가 없을 경우 = 새로 생성한다.
3. 비회원이고 장바구니가 있는 경우 = 존재하는 장바구니를 사용한다.
+) 비회원이지만 장바구니 번호가 유효하지 않은 경우 = 장바구니를 새로 생성한다.
4. 회원이고 장바구니가 없는 경우 = 새로 생성한다.
5. 회원이고 장바구니가 있는 경우 = 존재하는 장바구니를 사용한다.
+) 회원이 가지고 있는 장바구니가 있는지 조회한다.
6. 회원이고 장바구니번호가 있으면 그대로 조회한다.
7. 회원이고 장바구니번호를 받았는데 매개변수로 받아온 장바구니 번호와 회원의 장바구니 번호가 다를 경우 에러 발생 => IllegalArgumentException
=> 뭔가 잘못 요청되고 있다고 판단하여 에러를 발생시킨다.

 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceGetOrCreateCartTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDao cartDao;

    private final String TEST_USER = "CART_SERVICE_TEST";
    private String NON_MEMBERSHIP = UserType.NON_MEMBERSHIP.getCode();

    @Before
    public void setUp() throws Exception {
        cartDao.deleteAll();
    }

    @Test
    public void 장바구니서비스_비회원_장바구니없음 () {
        // cartSeq = null && userId = null
        Integer cartSeq = cartService.createOrGetCart(null, null);
        assertNotNull(cartSeq);

        // select and assert
        Map map = Map.of("cart_seq", cartSeq);
        CartDto cartDto = cartDao.selectByMap(map);
        assertNotNull(cartDto);
    }

    @Test
    public void 장바구니서비스_비회원_장바구니있음 () {
        CartDto cartDto = CartDto.create(NON_MEMBERSHIP);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // cartSeq 가 있는 상황
        Integer serviceCartSeq = cartService.createOrGetCart(cartSeq, null);
        assertNotNull(serviceCartSeq);

        Map map =Map.of("cart_seq", serviceCartSeq);
        CartDto selectedDto = cartDao.selectByMap(map);
        assertNotNull(selectedDto);
        assertTrue(cartDto.equals(selectedDto));
    }

//    비회원이지만 장바구니 번호가 유효하지 않은 경우 = 장바구니를 새로 생성한다.
    @Test
    public void 장바구니서비스_비회원_유효하지않은장바구니번호 () {
        Integer FAIL_CART_SEQ = -100000;

        Integer serviceCartSeq = cartService.createOrGetCart(FAIL_CART_SEQ, null);
        assertNotNull(serviceCartSeq);

        Map map =Map.of("cart_seq", serviceCartSeq);
        CartDto selectedDto = cartDao.selectByMap(map);
        assertNotNull(selectedDto);
    }

    // 회원이고 장바구니가 없는 경우 생성해서 만들어준다.
    @Test
    public void 장바구니서비스_회원_장바구니없음_장바구니번호없음 () {
        Integer serviceCartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(serviceCartSeq);

        Map map = Map.of("user_id", TEST_USER);
        CartDto selectedDto = cartDao.selectByMap(map);
        assertNotNull(selectedDto);
    }

    @Test
    public void 장바구니서비스_회원_장바구니있음_장바구니번호없음 () {
        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        Integer serviceCartSeq = cartService.createOrGetCart(null, TEST_USER);
        assertNotNull(serviceCartSeq);
        assertEquals(cartSeq, serviceCartSeq);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 장바구니서비스_회원_잘못된매개변수 () {
        /*
        cart_seq, userId 를 받았는데 조회 결과가 없는 경우 => IllegalArgumentsException
         */

        final String FAIL_TEST_USER = "FAIL_TEST_USER";

        CartDto failCartDto = CartDto.create(FAIL_TEST_USER);
        Integer failCartSeq = cartDao.insertAndReturnSeq(failCartDto);
        assertNotNull(failCartSeq);

        // failCartSeq, TEST_USER 는 일치하지 않는다.
        Integer serviceCartSeq = cartService.createOrGetCart(failCartSeq, TEST_USER);
        assertNotNull(serviceCartSeq);
        assertEquals(failCartSeq, serviceCartSeq);

        // 실패
        fail();
    }
}