package cart.service;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.cart.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/*
주문 상품 추가
- 잘못된 isbn 인 경우에는 추가 시도를 하지 않는다. => join 시 null 을 반환하기 때문에 service 에서 체크
=> IllegalArgumentException

- 주문 상품 추가 시 기본적으로 구매 수량을 1개로 맞춘다.

- 장바구니 상품은 단일 추가이며 성공여부에 따라 insert 에 성공한 row 개수를 반환한다.

- 같은 도서 (isbn, prod_type_code)일 때는 추가되지 않는다.
=> 같은 도서 : UNIQUE 를 사용해야하지만 일단 pass
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceAddCartProductTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartProductDao cartProductDao;

    private final String TEST_USER = "CART_SERVICE_TEST";
    private final String PRINTED = BookType.PRINTED.getCode();
    private final String EBOOK = BookType.EBOOK.getCode();

    private final String TEST_ISBN = "9791162245408";
    private final String TEST_ISBN_NON_EBOOK = "9788994492032";

    private final int SUCCESS = 1;


    // 잘못된 isbn 으로 추가시도
    // isbn 과 userId 는 있다고 가정
    @Test(expected = IllegalArgumentException.class)
    public void 장바구니서비스_잘못된isbn () {
        final String FAIL_ISBN = "FAIL_ISBN";

        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        int addResult = cartService.addCartProduct(cartSeq, FAIL_ISBN, PRINTED, TEST_USER);
        assertEquals(SUCCESS, addResult);

        List<CartProductDto> selectedList = cartProductDao.selectListByCartSeq(cartSeq);
        assertEquals(1, selectedList.size());

        fail();
    }


    @Test
    public void 장바구니서비스_구매수량기본값1 () {
        final int BASIC_QUANTITY = 1;

        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        int addResult = cartService.addCartProduct(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
        assertEquals(SUCCESS, addResult);

        CartProductDto selectedDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, PRINTED);
        assertNotNull(selectedDto);
        // 비교
        assertEquals(BASIC_QUANTITY, selectedDto.getItem_quan().intValue());
    }

    /*
    같은 도서 + 같은 유형이면 추가하지 않는다.
    => return 값 = 추가한 것으로 취급한다.
     */
    @Test
    public void 장바구니서비스_같은도서끼리는추가되지않음 () {
        final int REPEAT = 5;

        // 장바구니 생성
        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // 장바구니 상품 추가
        int addResult = 0;
        for (int i = 0; i < REPEAT; i++) {
            addResult = cartService.addCartProduct(cartSeq, TEST_ISBN, PRINTED, TEST_USER);
            assertEquals(SUCCESS, addResult);

            // 초기화
            addResult = 0;
        }

        // assert : select 결과가 1개면 Pass
        List<CartProductDto> selectedList = cartProductDao.selectListByCartSeq(cartSeq);
        assertEquals(1, selectedList.size());
    }

}
