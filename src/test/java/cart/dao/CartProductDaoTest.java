package cart.dao;

import com.fastcampus.ch4.dao.cart.CartDao;
import com.fastcampus.ch4.dao.cart.CartProductDao;
import com.fastcampus.ch4.dao.cart.CartProductDaoImpl;
import com.fastcampus.ch4.dto.cart.CartDto;
import com.fastcampus.ch4.dto.cart.CartProductDetailDto;
import com.fastcampus.ch4.dto.cart.CartProductDto;
import com.fastcampus.ch4.dto.item.BookDto;
//import com.fastcampus.ch4.dto.order.temp.BookDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.item.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/*
insert
1. 전부 NOT NULL 이기 때문에 그대로 insert
2. 생성 성공 시 성공한 row 의 개수를 반환한다.
3. 단일 생성 / 여러 개 생성
4. 동일한 cart_seq, isbn, prod_code_type 이 들어가면 안된다. => service 에서 처리

select
1. cart_seq 와 일치하는 ROW 들을 조회한다.
2. 도서 의 정보를 JOIN 하는 것으로 상품 정보를 줘야한다. => 잘못된 도서정보를 걸러주는 것은 service에서 처리해야한다.

delete
1. cart_seq 와 일치하는 ROW 들을 삭제한다.
2. 삭제 성공 시 성공한 ROW 의 개수를 반환한다
3. 개별 삭제 / cart_seq 에 속한 전체 삭제

update
1. item_quan 의 값을 +1 해준다.
2. item_quan 의 값을 -1 해준다.

- item_quan > 0 을 계산해주는 것은 Service 레이어에서 진행한다.

 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartProductDaoTest {
    @Autowired
    CartDao cartDao;
    @Autowired
    CartProductDao cartProductDao;
    @Autowired
    private CartProductDaoImpl cartProductDaoImpl;

    @Autowired
    BookService bookService;

    private static final String TEST_USER = "CART_PROD_INSERT";
    final String UPDATE_TEST_USER = "CART_PROD_UPDATE";
    final String TEST_ISBN = "1000000000251";
    final String TEST_ISBN_FIRST = "1000000000257";
    final String TEST_ISBN_SECOND = "1000000000276";
//    private static final TempBookService bookService = new FakeBookServiceImpl();
    private static final Integer SINGLE = 1;
    private static final Integer DOUBLE = 2;
    private static final Integer MULTIPLE = 5;
    private static final int SUCCESS = 1;

    @Before
    public void tearDown() throws Exception {
        List<CartDto> cartList = cartDao.selectListByCondition(null, TEST_USER);


        if (!cartList.isEmpty()) {
            cartList.forEach(cartDto -> {
                int deleteResult = cartProductDao.deleteByCartSeq(cartDto.getCart_seq());
                assertTrue(deleteResult >= 0);
            });
        }

        cartList = cartDao.selectListByCondition(null, UPDATE_TEST_USER);
        if (!cartList.isEmpty()) {
            cartList.forEach(cartDto -> {
                int deleteResult = cartProductDao.deleteByCartSeq(cartDto.getCart_seq());
                assertTrue(deleteResult >= 0);
            });
        }
    }

    @Test
    public void 장바구니상품_insertSingle() {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        final String bookType = BookType.PRINTED.getCode();

        // 생성
        CartProductDto cartProductDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        // insert
        int result = cartProductDao.insert(cartProductDto);
        assertEquals(SUCCESS, result);
    }

    @Test
    public void 장바구니상품_insert_도서유형별등록() {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String TEST_ISBN = "9791162245408";
        final String bookType = BookType.PRINTED.getCode();

        // 일반도서 insert
        CartProductDto printedBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(printedBookCartProdDto);
        assertEquals(SUCCESS, pritedResult);

        // Ebook insert
        CartProductDto eBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, BookType.EBOOK.getCode(), SINGLE, TEST_USER);

        // insert
        int eBookResult = cartProductDao.insert(eBookCartProdDto);
        assertEquals(SUCCESS, eBookResult);
    }

    /*
    cart_seq 와 일치하는 ROW 들을 조회한다. => selectList
     */
    @Test
    public void 장바구니상품_selectOne() throws Exception {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String bookType = BookType.PRINTED.getCode();
        BookDto bookDto = bookService.read(TEST_ISBN);
        assertNotNull(bookDto);

        // 일반도서 insert
        CartProductDto cartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int result = cartProductDao.insert(cartProdDto);
        assertEquals(SUCCESS, result);

        // selectOne
        CartProductDto selectedDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(selectedDto);
        assertTrue(cartProdDto.equals(selectedDto));
    }

    @Test
    public void 장바구니상품_selectList() throws Exception {
        final String bookType = BookType.PRINTED.getCode();

        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        BookDto bookDto = bookService.read(TEST_ISBN);
        assertNotNull(bookDto);

        // 일반도서 insert
        CartProductDto printedBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(printedBookCartProdDto);
        assertEquals(SUCCESS, pritedResult);

        // Ebook insert
        CartProductDto eBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        // insert
        int eBookResult = cartProductDao.insert(eBookCartProdDto);
        assertEquals(SUCCESS, eBookResult);

        // selectedList
        List<CartProductDto> selectedList = cartProductDao.selectListByCartSeq(cartSeq);

        // selectedList 의 결과에서 추가한 것이 다 있으면 성공
        List<CartProductDto> createdList = List.of(
                printedBookCartProdDto,
                eBookCartProdDto
        );

        // 개수 비교
        assertEquals(createdList.size(), selectedList.size());

        for (int i = 0; i < createdList.size(); i++) {
            // contain = equals 사용
            boolean containSelectedList = selectedList.contains(createdList.get(i));
            assertTrue(containSelectedList);
        }
    }

//    @Test
//    public void 장바구니상품_selectOne_JoinBook() throws Exception {
//        // cartDto 생성 & insert
//        CartDto cartDto = CartDto.create();
//        cartDto.setUserId(TEST_USER);
//        cartDto.setReg_id(TEST_USER);
//        cartDto.setUp_id(TEST_USER);
//        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
//        assertNotNull(cartSeq);
//
//        // select bookDto
//
//        final String bookType = BookType.PRINTED.getCode();
//
//        BookDto firstBookDto = bookService.read(TEST_ISBN_FIRST);
//        BookDto secondBookDto = bookService.read(TEST_ISBN_SECOND);
//
//        CartProductDto firstCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN_FIRST, bookType, SINGLE, TEST_USER);
//
//        // insert
//        int firstResult = cartProductDao.insert(firstCartProdDto);
//        assertEquals(SUCCESS, firstResult);
//
//        CartProductDto secondCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN_SECOND, bookType, SINGLE, TEST_USER);
//
//        // insert
//        int secondResult = cartProductDao.insert(secondCartProdDto);
//        assertEquals(SUCCESS, secondResult);
//
//        // select & assert
//        CartProductDetailDto firstDetailDto = cartProductDao.selectOneDetail(cartSeq, TEST_ISBN_FIRST, bookType);
//        String firstBookTitle = firstBookDto.getTitle();
//        String firstDetailTitle = firstDetailDto.getBook_title();
//        assertEquals(firstBookTitle, firstDetailTitle);
//
//        CartProductDetailDto secondDetailDto = cartProductDao.selectOneDetail(cartSeq, TEST_ISBN_SECOND, bookType);
//        String secondBookTitle = secondBookDto.getTitle();
//        String secondDetailTitle = secondDetailDto.getBook_title();
//        assertEquals(secondBookTitle, secondDetailTitle);
//    }

    @Test
    public void 장바구니상품_selectList_JoinBook() throws Exception {
        final String bookType = BookType.PRINTED.getCode();

        List<BookDto> bookList = new ArrayList<>();

        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        BookDto firstBookDto = bookService.read(TEST_ISBN_FIRST);
        bookList.add(firstBookDto);
        BookDto secondBookDto = bookService.read(TEST_ISBN_SECOND);
        bookList.add(secondBookDto);

        CartProductDto firstCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN_FIRST, bookType, SINGLE, TEST_USER);

        // insert
        int firstResult = cartProductDao.insert(firstCartProdDto);
        assertEquals(SUCCESS, firstResult);

        CartProductDto secondCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN_SECOND, bookType, SINGLE, TEST_USER);

        // insert
        int secondResult = cartProductDao.insert(secondCartProdDto);
        assertEquals(SUCCESS, secondResult);

        // select & assert
        List<CartProductDetailDto> selectedList = cartProductDao.selectListDetailByCartSeq(cartSeq);
        assertTrue(!selectedList.isEmpty());


        // selectedList 개수 비교
        assertEquals(bookList.size(), selectedList.size());

        // 추가한 bookDto 의 정보와 동일한 selectedList 요소가 있어야 한다.
        for (BookDto bookDto : bookList) {
            boolean found = false;
            for (CartProductDetailDto cartProductDto : selectedList) {
                String bookTitle = cartProductDto.getTitle();
                String detailTitle = cartProductDto.getTitle();
                if (bookTitle.equals(detailTitle)) {
                    found = true;
                    break;
                }
            }

            // 동일한 것이 없으면 테스트 미통과
            assertTrue(found);
        }
    }

    @Test
    public void 장바구니상품_deleteOne() {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        final String TEST_ISBN = "9791162245408";
        final String bookType = BookType.PRINTED.getCode();

        // 일반도서 insert
        CartProductDto printedBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(printedBookCartProdDto);
        assertEquals(SUCCESS, pritedResult);

        // delete
        int deleteResult = cartProductDao.deleteOne(cartSeq, TEST_ISBN, bookType);
        assertEquals(SUCCESS, deleteResult);

        // select 체크
        CartProductDto selectedDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNull(selectedDto);
    }

    @Test
    public void 장바구니상품_deleteByCartSeq() {
        final int INSERT_COUNT = 2;

        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String TEST_ISBN = "9791162245408";
        final String PRINTED_BOOK = BookType.PRINTED.getCode();
        final String E_BOOK = BookType.EBOOK.getCode();

        // 일반도서 insert
        CartProductDto printedBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, PRINTED_BOOK, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(printedBookCartProdDto);
        assertEquals(SUCCESS, pritedResult);

        // Ebook insert
        CartProductDto eBookCartProdDto = CartProductDto.create(cartSeq, TEST_ISBN, E_BOOK, SINGLE, TEST_USER);

        // insert
        int eBookResult = cartProductDao.insert(eBookCartProdDto);
        assertEquals(SUCCESS, eBookResult);

        // deleteByCartSeq
        int deleteResult = cartProductDao.deleteByCartSeq(cartSeq);
        assertEquals(INSERT_COUNT, deleteResult);

        // 조회가 가능한지 체크

        CartProductDto selected1 = cartProductDao.selectOne(cartSeq, TEST_ISBN, PRINTED_BOOK);
        assertNull(selected1);

        CartProductDto selected2 = cartProductDao.selectOne(cartSeq, TEST_ISBN, E_BOOK);
        assertNull(selected2);
    }

    /*
    update
    1. item_quan 의 값을 +1 해준다.
    2. item_quan 의 값을 -1 해준다.

    => 나머지는 전부 새로 생성하는 것으로 하기 때문에 item_quan 만 업데이트 할 수 있도록 한다.
     */
    @Test
    public void 장바구니상품_updateItemQuantity() {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String TEST_ISBN = "9791162245408";
        final String bookType = BookType.PRINTED.getCode();

        // 일반도서 insert
        CartProductDto createdDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(createdDto);
        assertEquals(SUCCESS, pritedResult);

        // update plus * 1
        int updateResult = cartProductDao.updateItemQuantity(cartSeq, TEST_ISBN, bookType, DOUBLE, UPDATE_TEST_USER);
        assertEquals(SUCCESS, updateResult);

        CartProductDto selectedDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(selectedDto);

        int creaetedQuantity = createdDto.getItem_quan();
        int firstUpdatedQuantity = selectedDto.getItem_quan();

        // 기존 수량과 비교
        assertEquals(creaetedQuantity + 1, (int) firstUpdatedQuantity);

        // update plus * 5
        for (int i = 1; i < MULTIPLE + 1; i++) {
            updateResult = cartProductDao.updateItemQuantity(cartSeq, TEST_ISBN, bookType, i, UPDATE_TEST_USER);
            assertEquals(SUCCESS, updateResult);
        }

        selectedDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(selectedDto);

        int secondUpdatedQuantity = selectedDto.getItem_quan();

        // 기존 수량과 비교
        assertEquals(firstUpdatedQuantity + MULTIPLE, (int) secondUpdatedQuantity);

        // update minus * 5
        for (int i = 1; i < MULTIPLE + 1; i++) {
            updateResult = cartProductDao.updateItemQuantity(cartSeq, TEST_ISBN, bookType, i, UPDATE_TEST_USER);
            assertEquals(SUCCESS, updateResult);
        }

        selectedDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(selectedDto);

        int thirdUpdatedQuantity = selectedDto.getItem_quan();

        // 기존 수량과 비교
        assertEquals(secondUpdatedQuantity - MULTIPLE, (int) thirdUpdatedQuantity);
    }


    /*
    필요한 데이터 : cart_seq, isbn, prod_type_code, userId

    - CartProductDao 에서 plusItemQuantity 를 호출한다.
    - [Mapper] cart_seq, isbn, prod_type_code 으로 변경할 로우를 특정해준다.
    - [Mapper] UPDATE QUERY
    -- item_quan
    -- updated_at
    -- up_date
    - 변경한 ROW 를 반환한다. => 반환된 숫자는 1이어야 한다.
    - 나머지 컬럼들을 확인한다.
     */

    @Test
    public void 장바구니상품_plusItemQuantity() {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create();
        cartDto.setUserId(TEST_USER);
        cartDto.setReg_id(TEST_USER);
        cartDto.setUp_id(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String TEST_ISBN = "9791162245408";
        final String bookType = BookType.PRINTED.getCode();

        // 일반도서 insert
        CartProductDto creatingDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(creatingDto);
        assertEquals(SUCCESS, pritedResult);

        // insert 한 도서 확인
        CartProductDto createdDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(createdDto);
        assertTrue(creatingDto.equals(createdDto));


        // === 비회원 고객 장바구니

        // CartProductDao 에서 plusItemQuantity 를 호출한다.
        int updateResult = cartProductDao.plusItemQuantity(cartSeq, TEST_ISBN, bookType, null);
        assertEquals(SUCCESS, updateResult);

        List<CartProductDto> selectedList = cartProductDao.selectListByCartSeq(cartSeq);
        assertFalse(selectedList.isEmpty());

        // 컬럼 전체 확인
        CartProductDto selectedDto = selectedList.get(0);
        assertEquals(createdDto.getCart_seq(), selectedDto.getCart_seq());
        assertEquals(createdDto.getIsbn(), selectedDto.getIsbn());
        assertEquals(createdDto.getProd_type_code(), selectedDto.getProd_type_code());
        assertEquals(createdDto.getReg_date(), selectedDto.getReg_date());
        assertEquals(createdDto.getReg_id(), selectedDto.getReg_id());
        assertEquals(createdDto.getCreated_at(), selectedDto.getCreated_at());
        // 갯수 비교
        assertEquals(createdDto.getItem_quan() + SINGLE, selectedDto.getItem_quan().intValue());
    }

    @Test
    public void 장바구니상품_minusItemQuantity() {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String TEST_ISBN = "9791162245408";
        final String bookType = BookType.PRINTED.getCode();

        // 일반도서 insert
        CartProductDto creatingDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(creatingDto);
        assertEquals(SUCCESS, pritedResult);

        // insert 한 도서 확인
        CartProductDto createdDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(createdDto);
        assertTrue(creatingDto.equals(createdDto));

        // plusItemQuantity * 5
        for (int i = 0; i < MULTIPLE; i++) {
            int plusResult = cartProductDao.plusItemQuantity(cartSeq, TEST_ISBN, bookType, TEST_USER);
            assertEquals(SUCCESS, plusResult);
        }

        // minus * 5
        for (int i = 0; i < MULTIPLE; i++) {
            int minusResult = cartProductDao.minusItemQuantity(cartSeq, TEST_ISBN, bookType, TEST_USER);
            assertEquals(SUCCESS, minusResult);
        }

        // 변경한 ROW 조회
        List<CartProductDto> selectedList = cartProductDao.selectListByCartSeq(cartSeq);
        assertFalse(selectedList.isEmpty());

        // 컬럼 전체 확인
        CartProductDto selectedDto = selectedList.get(0);
        assertEquals(createdDto.getCart_seq(), selectedDto.getCart_seq());
        assertEquals(createdDto.getIsbn(), selectedDto.getIsbn());
        assertEquals(createdDto.getProd_type_code(), selectedDto.getProd_type_code());
        assertEquals(createdDto.getReg_date(), selectedDto.getReg_date());
        assertEquals(createdDto.getReg_id(), selectedDto.getReg_id());
        assertEquals(createdDto.getCreated_at(), selectedDto.getCreated_at());
        // 갯수 비교
        assertEquals(createdDto.getItem_quan(), selectedDto.getItem_quan());
    }

    @Test(expected = UncategorizedSQLException.class)
    public void update_1보다작은값을업데이트하려고하면예외발생 () {
        // cartDto 생성 & insert
        CartDto cartDto = CartDto.create(TEST_USER);
        Integer cartSeq = cartDao.insertAndReturnSeq(cartDto);
        assertNotNull(cartSeq);

        // select bookDto
        final String TEST_ISBN = "9791162245408";
        final String bookType = BookType.PRINTED.getCode();

        // 일반도서 insert
        CartProductDto creatingDto = CartProductDto.create(cartSeq, TEST_ISBN, bookType, SINGLE, TEST_USER);

        int pritedResult = cartProductDao.insert(creatingDto);
        assertEquals(SUCCESS, pritedResult);

        // insert 한 도서 확인
        CartProductDto createdDto = cartProductDao.selectOne(cartSeq, TEST_ISBN, bookType);
        assertNotNull(createdDto);
        assertTrue(creatingDto.equals(createdDto));

        // minus * 5
        for (int i = 0; i < MULTIPLE; i++) {
            int minusResult = cartProductDao.minusItemQuantity(cartSeq, TEST_ISBN, bookType, TEST_USER);
            assertEquals(SUCCESS, minusResult);
        }

        // 변경한 ROW 조회
        List<CartProductDto> selectedList = cartProductDao.selectListByCartSeq(cartSeq);
        assertFalse(selectedList.isEmpty());

        // 컬럼 전체 확인
        CartProductDto selectedDto = selectedList.get(0);
        assertEquals(createdDto.getCart_seq(), selectedDto.getCart_seq());
        assertEquals(createdDto.getIsbn(), selectedDto.getIsbn());
        assertEquals(createdDto.getProd_type_code(), selectedDto.getProd_type_code());
        assertEquals(createdDto.getReg_date(), selectedDto.getReg_date());
        assertEquals(createdDto.getReg_id(), selectedDto.getReg_id());
        assertEquals(createdDto.getCreated_at(), selectedDto.getCreated_at());
        // 갯수 비교
        assertEquals(createdDto.getItem_quan(), selectedDto.getItem_quan());

        // 위 코드가 문제가 없다면 테스트는 실패해야한다.
        fail();
    }

}
