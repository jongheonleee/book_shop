package order.service.OrderService;

import com.fastcampus.ch4.dto.order.OrderDto;
import com.fastcampus.ch4.dto.order.OrderProductDto;
import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.order.OrderService;
import com.fastcampus.ch4.service.order.fake.FakeBookServiceImpl;
import com.fastcampus.ch4.service.order.fake.TempBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;


/*
주문하기 기능 요구사항 = 장바구니 페이지 -> 주문하기 페이지 -> 주문하기 버튼 (주문 생성) -> 주문 완료 페이지
1. 비회원일 때 주문을 하면 에러 발생
2. 주문 하기 버튼을 누르면 무조건 주문이 생성되고 받아온 주문 상품을 추가한다.
3. 주문을 했을 때 주문의 가격 정보와 주문상품 들의 가격 정보의 총액이 일치해야한다.
4. itemQuantity 가 null 이거나 0 이하 일 경우 => IllegalArgumentException
=> itemQuantity 를 0 이하로 만들면 프론트에서 alert 를 발생시키는 것으로 처리한다.
5. 찾을 수 없는 isbn 인 경우(없는 도서를 조회하는 경우) => IllegalArgumentException
6. 도서유형을 받아서 가격을 가져왔는데 null 인 경우 (상품 유형 코드를 잘못 보낸 경우) => IllegalArgumentException
7. 유효하지 않은 도서유형을 전달받았을 때 (null 등..)=> IllegalArgumentException


8. 주문상품 생성 중 에러가 발생했을 때 주문도 같이 롤백
9. 회원일 때 주문 가능

2차 기능
. 장바구니에 있는 상품을 주문할 때는 장바구니에 추가한다. => 장바구니 기능 이후 추가
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    private TempBookService bookService = new FakeBookServiceImpl();

    final String TEST_USER = "ORDER_SERVICE_TEST";


    /*
    비회원 주문
    1. 에러 발생 => NullPointerException (null 이 허용되지 않는데 null 을 받았을 경우)

     */
    @Test(expected = NullPointerException.class)
    public void 비회원주문 () throws Exception {
        String NULL_USER = null;
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788966261024",
                        "item_quan", 2),
                Map.of( "isbn", "9791169212427",
                        "item_quan", 3)
        );
        orderService.createOrder(NULL_USER, ordProdList);
        fail();
    }

    /*
    - 추가한 주문 상품의 결과
    => order 에 대한 정보
    => orderProduct 에 대한 정보

     */
    @Test
    public void 주문상품추가하기 () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788966261024",
                        "item_quan", 2,
                        "prod_type_code", "printed"),
                Map.of( "isbn", "9791169212427",
                        "item_quan", 3,
                        "prod_type_code", "printed")
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        // 값이 들어가 있는지 확인한다.
        Integer ordSeq = result.getOrd_seq();
        List<OrderProductDto> itemList = result.getItemList();

        // 순회하면서 존재하는지 검사한다.
        for (int i = 0; i < ordProdList.size(); i++) {
            Map ordProdItem = ordProdList.get(i);
            Optional<OrderProductDto> existOrdProd = itemList.stream()
                    .filter(item -> ordProdItem.get("isbn").equals(item.getIsbn()))
                    .findAny();
            if (existOrdProd.isEmpty()) {
                fail();
            }
        }
    }


    /*
    주문을 했을 때 주문의 가격 정보와 주문상품 들의 가격 정보의 총액이 일치해야한다.
    상품을 추가한다.
    주문의 가격을 변경한다.
    등록 후 비교

    prod_type_code 에 따라서 가격 셋팅이 달라진다.

     */
    @Test
    public void 주문가격총액일치 () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788966261024",
                        "item_quan", 2,
                        "prod_type_code", BookType.PRINTED.getCode()),
                Map.of( "isbn", "9791169212427",
                        "item_quan", 3,
                        "prod_type_code", BookType.PRINTED.getCode())
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        // 값이 들어가 있는지 확인한다.
        Integer ordSeq = result.getOrd_seq();
        List<OrderProductDto> itemList = result.getItemList();

        int sumProductPrice = 0;
        int sumBenefitPrice = 0;
        int sumOrderPrice = 0;

        for (int i = 0; i < ordProdList.size(); i++) {
            Map<String, Object> currentOrdProd = ordProdList.get(i);
            TempBookDto bookDto = bookService.getBookByIsbn(currentOrdProd.get("isbn").toString());
            int itemQuantity = (int) currentOrdProd.get("item_quan");
            String prodTypeCode = currentOrdProd.get("prod_type_code").toString();

            if (prodTypeCode.equals(BookType.PRINTED.getCode())) {
                int productPrice = bookDto.getPapr_pric();
                int benefitPrice = (int) (productPrice * bookDto.getPapr_disc());
                int orderPrice = productPrice - benefitPrice;

                sumProductPrice += productPrice * itemQuantity;
                sumBenefitPrice += benefitPrice * itemQuantity;
                sumOrderPrice += orderPrice * itemQuantity;

            } else if (prodTypeCode.equals(BookType.EBOOK.getCode())) {
                int productPrice = bookDto.getE_pric();
                int benefitPrice = (int) (productPrice * bookDto.getE_disc());
                int orderPrice = productPrice - benefitPrice;

                sumProductPrice += productPrice * itemQuantity;
                sumBenefitPrice += benefitPrice * itemQuantity;
                sumOrderPrice += orderPrice * itemQuantity;
            }
        }

        assertEquals(result.getTotal_prod_pric().intValue(), sumProductPrice);
        assertEquals(result.getTotal_bene_pric().intValue(), sumBenefitPrice);
        assertEquals(result.getTotal_ord_pric().intValue(), sumOrderPrice);
    }

    /*
    itemQuantity 가 null 이거나 0 이하 일 경우 => IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void 주문상품수량체크_0 () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788966261024",
                        "item_quan", 2,
                        "prod_type_code", BookType.PRINTED.getCode()),
                Map.of( "isbn", "9791169212427",
                        "item_quan", 0,
                        "prod_type_code", BookType.PRINTED.getCode())
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        fail();
    }
    @Test(expected = IllegalArgumentException.class)
    public void 주문상품수량체크_minus () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788966261024",
                        "item_quan", -4,
                        "prod_type_code", BookType.PRINTED.getCode()),
                Map.of( "isbn", "9791169212427",
                        "item_quan", 5,
                        "prod_type_code", BookType.PRINTED.getCode())
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        fail();
    }

    /*
    찾을 수 없는 isbn 인 경우
    없는 도서를 조회하는 경우 IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void 찾을수없는도서 () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788966261024",
                        "item_quan", -4,
                        "prod_type_code", BookType.PRINTED.getCode()),
                Map.of( "isbn", "찾을수없는isbn",
                        "item_quan", 5,
                        "prod_type_code", BookType.PRINTED.getCode())
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        fail();
    }

    /*
    도서유형을 받아서 가격을 가져왔는데 null 인 경우 (상품 유형 코드를 잘못 보낸 경우) => IllegalArgumentException
     */

    @Test(expected = IllegalArgumentException.class)
    public void 도서유형에해당하는값정보가없는경우 () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788994492032",
                        "item_quan", 1,
                        "prod_type_code", BookType.EBOOK.getCode()), // EBOOK 에 해당하는 값이 없는 도서
                Map.of( "isbn", "9788966261024",
                        "item_quan", 5,
                        "prod_type_code", BookType.PRINTED.getCode())
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        fail();
    }

    /*
    유효하지 않은 도서유형을 전달받았을 때 (null 등..)=> IllegalArgumentException

     */

    @Test(expected = IllegalArgumentException.class)
    public void 유효한도서유형이아닌경우 () throws Exception {
        List<Map<String, Object>> ordProdList = List.of(
                Map.of( "isbn", "9788994492032",
                        "item_quan", 1,
                        "prod_type_code", "유효하지 않은 도서 유형"), // 유효하지 않은 도서 유형
                Map.of( "isbn", "9788966261024",
                        "item_quan", 5,
                        "prod_type_code", BookType.PRINTED.getCode())
        );

        OrderDto result = orderService.createOrder(TEST_USER, ordProdList);
        assertNotNull(result);

        fail();
    }


//    /*
//    회원 주문
//    1. 회원 주문을 성공했을 때 주문에 대한 정보가 반환되어야 한다.
//    2. 반환되는 정보 : 해당하는 주문 + 주문상품 정보
//     */
//    @Test
//    public void 회원주문 () {
//        String TEST_USER = "ORDER_SERVICE_TEST";
//        List<Map<String, Object>> ordProdList = List.of(
//                Map.of( "isbn", "9788966261024",
//                        "item_quan", 2),
//                Map.of( "isbn", "9791169212427",
//                        "item_quan", 3)
//        );
//
//        Object result = orderService.createOrder(TEST_USER, ordProdList);
//        assertNotNull(result);
//    }


}
