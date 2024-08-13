package com.fastcampus.ch4.service.cart;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.model.cart.UserType;
import com.fastcampus.ch4.service.order.fake.FakeBookServiceImpl;
import com.fastcampus.ch4.service.order.fake.TempBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao cartDao;
    @Autowired
    CartProductDao cartProductDao;

    // bookService 연결 시 변경
    private TempBookService bookService = new FakeBookServiceImpl();

    final static int BASIC_ITEM_QUANTITY = 1;
    final static int SUCCESS_CODE = 1;

    @Override
    public Integer add(Integer cart_seq, String isbn, String prod_type_code, String userId) {
        try {
            Integer cartSeq = createOrGetCart(cart_seq, userId);
            int insertedRowCount = addCartProduct(cartSeq, isbn, prod_type_code, userId);
            if (insertedRowCount < 1) {
                throw new RuntimeException("장바구니에 상품이 추가되지 않았습니다. 다시 시도해주세요.");
            }
            return cartSeq;
        } catch (IllegalStateException iae) {
            // 잘못된 매개변수 예외 처리
            throw iae;
        } catch (Exception e) {
            // 전체 예외 처리
            throw e;
        }
    }

    @Override
    public Integer createOrGetCart(Integer cartSeq, String userId) {
        // 비회원 고객
        if (userId == null || userId.isEmpty()) {
            // 비회원 && 장바구니번호 없음
            if (cartSeq == null) {
                CartDto cartDto = CartDto.create(UserType.NON_MEMBERSHIP);
                return cartDao.insertAndReturnSeq(cartDto);
            } else { // 장바구니번호 있음
                Map map = new HashMap();
                map.put("cart_seq", cartSeq);
                CartDto cartDto = cartDao.selectByMap(map);

                // 유효하지 않은 cartSeq 이라면 새로 만들어서 반환한다.
                if (cartDto == null) {
                    cartDto = CartDto.create(UserType.NON_MEMBERSHIP);
                    return cartDao.insertAndReturnSeq(cartDto);
                }
                return cartDto.getCart_seq();
            }
        } else { // 회원인 경우
            if (cartSeq == null) { // 장바구니 번호를 받아오지 않음
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                CartDto cartDto = cartDao.selectByMap(map);
                // 회원에 해당하는 cartDto 가 없으면 생성하여 반환한다.
                // 유효하지 않은 장바구니 번호로 조회하면 새로 반환한다.
                if (cartDto == null) {
                    CartDto createCartDto = CartDto.create(userId);
                    return cartDao.insertAndReturnSeq(createCartDto);
                }
                return cartDto.getCart_seq();
            } else { // 장바구니 번호와 회원 아이디 둘다 받아옴
                Map map = new HashMap();
                map.put("cart_seq", cartSeq);
                map.put("userId", userId);
                CartDto cartDto = cartDao.selectByMap(map);

                if (cartDto == null) {
                    throw new IllegalArgumentException("잘못된 cart_seq 와 userId 입니다.");
                }
                return cartDto.getCart_seq();
            }
        }
    }

    @Override
    public int addCartProduct(Integer cartSeq, String isbn, String prod_type_code, String userId) {
        TempBookDto selectedBookDto = bookService.getBookByIsbn(isbn);
        // 매개변수에서 받아온 isbn 이 없을 때
        if (selectedBookDto == null) {
            throw new IllegalArgumentException("유효하지 않은 도서입니다.");
        }

        // 장바구니 상품을 조회하여 같은 것이 있으면 insert 하지 않고 성공한 것으로 처리
        List<CartProductDto> existCartProductDtoList = cartProductDao.selectListByCartSeq(cartSeq);
        for (CartProductDto cartProductDto : existCartProductDtoList) {
            // equals : isbn && prod_type_code
            boolean eqaulIsbn = cartProductDto.getIsbn().equals(selectedBookDto.getIsbn());
            boolean equalProdTypeCode = cartProductDto.getProd_type_code().equals(prod_type_code);
            if (eqaulIsbn && equalProdTypeCode) {
                return SUCCESS_CODE;
            }
        }

        // 비회원일 때 reg_id 와 up_id 를 셋팅하기 위해서 정적 팩토리 메서드에서 매개변수로 받아와야 한다.
        UserType currentUserType = (userId == null || userId.isEmpty()) ? UserType.NON_MEMBERSHIP : UserType.MEMBERSHIP;
        // dto 생성
        CartProductDto cartProductDto = CartProductDto.create(cartSeq, isbn, prod_type_code, BASIC_ITEM_QUANTITY, userId, currentUserType);

        // DB insert
        int insertResult = cartProductDao.insert(cartProductDto);

        return insertResult;
    }
}
