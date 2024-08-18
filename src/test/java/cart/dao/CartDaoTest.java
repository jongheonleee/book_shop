package cart.dao;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/*
장바구니
1. 생성
- 장바구니 생성 시 cart_num (pk) 를 반환한다.
- userId 를 속성으로 하여 생성한다.
- userId 가 null 이어도 생성된다.

2. 조회
- seq 조회
- userId 조회

3. 삭제
- cartSeq 를 받아서 삭제하기
- userId 를 받아서 삭제하기
- 삭제 후 조회 확인 할 것

- 업데이트 테스트

 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartDaoTest {
    @Autowired
    private CartDao cartDao;

    final static String TEST_USER = "CART_TEST_USER";

    @Before
    public void afterTest () {
        int result = cartDao.deleteAll();
    }

    @Test
    public void 장바구니생성_withUserId (){
        // cartDto 생성
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);
    }

    @Test
    public void 장바구니생성_noUserId () {
        CartDto cartDto = CartDto.create();
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cart_seq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cart_seq);

        // 구체적으로 작성하도록!!
        // 해당 seq로 조회하여 userId 여부를 확인한다.
        Map map = new HashMap();
        map.put("cart_seq", cart_seq);
        CartDto cartDto1 = cartDao.selectByMap(map);
        assertNotNull(cartDto1);
        assertNotNull(cartDto1.getUserId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 장바구니생성_시스템컬럼미입력 () {
        CartDto cartDto = CartDto.create();

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);
    }

    // 조회 후 비교
    @Test
    public void 장바구니조회_cartSeq () {
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // give
        Map map = new HashMap<>();
        map.put("cart_seq", cartSeq);

        // selectOne
        CartDto selectedCartDto = cartDao.selectByMap(map);
        assertNotNull(selectedCartDto);

        assertTrue(cartDto.equals(selectedCartDto));

        // selectList
        // 정리할 것
        List<CartDto> selectedList = cartDao.selectListByCondition(cartSeq, null);
        assertFalse(selectedList.isEmpty());
        CartDto listElementDto = selectedList.get(0);
        assertTrue(selectedCartDto.equals(listElementDto));
    }

    @Test
    public void 장바구니조회_userId () {
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // give
        Map map = new HashMap<>();
        map.put("userId", TEST_USER);

        // select
        CartDto selectedCartDto = cartDao.selectByMap(map);
        assertNotNull(selectedCartDto);

        // selectList
        List<CartDto> selectedList = cartDao.selectListByCondition(null, TEST_USER);
        assertFalse(selectedList.isEmpty());
        CartDto listElementDto = selectedList.get(0);
        assertTrue(selectedCartDto.equals(listElementDto));
    }

    // 장바구니 번호가 비회원의 번호와 같도록, 세션 ID => 유니크 ID 만들기

    @Test
    public void select_조건두개 () {
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // give
        Map map = new HashMap<>();
        map.put("userId", TEST_USER);
        map.put("cart_seq", cartSeq);

        // select
        CartDto selectedCartDto = cartDao.selectByMap(map);
        assertNotNull(selectedCartDto);

        // selectList
        List<CartDto> selectedList = cartDao.selectListByCondition(cartSeq, TEST_USER);
        assertFalse(selectedList.isEmpty());
        CartDto listElementDto = selectedList.get(0);
        assertTrue(selectedCartDto.equals(listElementDto));
    }

    @Test
    public void 장바구니조회_조건두개_userIdNull () {
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // give
        Map map = new HashMap<>();
        map.put("userId", null);
        map.put("cart_seq", cartSeq);

        // select
        CartDto selectedCartDto = cartDao.selectByMap(map);
        assertNotNull(selectedCartDto);
    }

    @Test(expected = MyBatisSystemException.class)
    public void selectOne_조회결과2개이상 () {
        CartDto firstCartDto = CartDto.create();
        firstCartDto.setUserId(TEST_USER);
        firstCartDto.setReg_id(TEST_USER);
        firstCartDto.setUp_id(TEST_USER);

        // insert
        Integer firstCartSeq = cartDao.insertAndReturnSeq(firstCartDto);
        assertNotNull(firstCartSeq);

        CartDto secondCartDto = CartDto.create();
        secondCartDto.setUserId(TEST_USER);
        secondCartDto.setReg_id(TEST_USER);
        secondCartDto.setUp_id(TEST_USER);

        // insert
        Integer secondCartSeq = cartDao.insertAndReturnSeq(secondCartDto);
        assertNotNull(secondCartSeq);

        // give
        Map map = new HashMap<>();
        map.put("userId", TEST_USER);

        CartDto selectedCartDto = cartDao.selectByMap(map);
        assertNotNull(selectedCartDto);

        fail();
    }

    @Test
    public void selectList_조회결과2개이상 () {
        CartDto firstCartDto = CartDto.create();
        firstCartDto.setUserId(TEST_USER);
        firstCartDto.setReg_id(TEST_USER);
        firstCartDto.setUp_id(TEST_USER);

        // insert 1
        Integer firstCartSeq = cartDao.insertAndReturnSeq(firstCartDto);
        assertNotNull(firstCartSeq);

        CartDto secondCartDto = CartDto.create();
        secondCartDto.setUserId(TEST_USER);
        secondCartDto.setReg_id(TEST_USER);
        secondCartDto.setUp_id(TEST_USER);

        // insert 2
        Integer secondCartSeq = cartDao.insertAndReturnSeq(secondCartDto);
        assertNotNull(secondCartSeq);

        // sort 하는 것으로 순서를 보장시킨다.
        // selectList
        List<CartDto> selectedList = cartDao.selectListByCondition(null, TEST_USER);
        assertFalse(selectedList.isEmpty());
        CartDto firstElementDto = selectedList.get(0);
        assertTrue(firstCartDto.equals(firstElementDto));

        CartDto secondElementDto = selectedList.get(1);
        assertTrue(secondCartDto.equals(secondElementDto));
    }

    @Test
    public void 장바구니삭제_withCartSeq () {
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // delete condition
        Map deleteCondition = new HashMap<>();
        deleteCondition.put("cart_seq", cartSeq);

        // delete & assert
        int result = cartDao.deleteByMap(deleteCondition);
        assertEquals(1, result);

        // 삭제한 것 또 삭제하는 TEST

        // select condition
        Map selectCondition = new HashMap<>();
        selectCondition.put("cart_seq", cartSeq);

        // select & assert
        CartDto selectedCartDto = cartDao.selectByMap(selectCondition);
        assertNull(selectedCartDto);
    }

    @Test
    public void 장바구니삭제_withUserId () {
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);

        // insert
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // delete condition
        Map deleteCondition = new HashMap<>();
        deleteCondition.put("userId", TEST_USER);

        // delete & assert
        int result = cartDao.deleteByMap(deleteCondition);
        assertEquals(1, result);

        // select condition
        Map selectCondition = new HashMap<>();
        selectCondition.put("userId", TEST_USER);

        // select & assert
        CartDto selectedCartDto = cartDao.selectByMap(selectCondition);
        assertNull(selectedCartDto);
    }
}
