package cart.service;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.cart.CartService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


/*
장바구니 상품 삭제

- 하나라도 없으면 IllegalArgumentException
- 존재하는 장바구니 상품인지 확인한다. => 존재하지 않으면 IllegalArgumentException


 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceDeleteProductTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartProductDao cartProductDao;

    private final Integer FAIL_SEQ = -1000000;
    private final String TEST_USER = "CART_SV_DELETE";
    private final String PRINTED = BookType.PRINTED.getCode();
    private final String TEST_ISBN = "1000000000005";
    private final String FAIL_ISBN = "9788994492032";

    private final int SUCCESS = 1;


    @Test(expected = IllegalArgumentException.class)
    public void 필수매개변수없음 () {
        int deleteResult = cartService.deleteCartProduct(FAIL_SEQ, "", PRINTED);
        assertEquals(SUCCESS, deleteResult);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 없는장바구니상품Exception () {
        int deleteResult = cartService.deleteCartProduct(FAIL_SEQ, FAIL_ISBN, PRINTED);
        assertEquals(SUCCESS, deleteResult);
        fail();
    }

    @Test
    public void 성공케이스 () throws Exception {
        Integer cartSeq = cartService.add(null, TEST_ISBN, PRINTED, TEST_USER);
        assertNotNull(cartSeq);
        int addResult = cartService.addCartProduct(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
        assertEquals(SUCCESS, addResult);

        List<CartProductDto> beforeList = cartProductDao.selectListByCartSeq(cartSeq);
        assertFalse(beforeList.isEmpty());

        int beforeResult = cartService.deleteCartProduct(cartSeq, TEST_ISBN, PRINTED);
        assertEquals(SUCCESS, beforeResult);

        List<CartProductDto> afterList = cartProductDao.selectListByCartSeq(cartSeq);
        assertTrue(afterList.isEmpty());
        assertEquals(0, afterList.size());
    }

}
