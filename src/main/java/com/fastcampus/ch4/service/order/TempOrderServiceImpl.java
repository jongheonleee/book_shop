package com.fastcampus.ch4.service.order;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.model.order.BookType;
import com.fastcampus.ch4.service.order.factory.OrderDtoFactory;
import com.fastcampus.ch4.service.order.factory.OrderProductDtoFactory;
import com.fastcampus.ch4.service.order.fake.FakeBookServiceImpl;
import com.fastcampus.ch4.service.order.fake.TempBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TempOrderServiceImpl implements TempOrderService {
    @Autowired
    TempOrderDao tempOrderDao;
    @Autowired
    OrderDtoFactory orderDtoFactory;
    @Autowired
    TempOrderProductDao tempOrderProductDao;

    // Book Service 대용
    private final TempBookService bookService = new FakeBookServiceImpl();

    public TempOrderDto createOrder (String userId, List<Map<String, Object>> ordProdList) throws Exception {
        // 비회원 주문 미허용 처리
        if (userId == null || userId.isEmpty()) {
            throw new NullPointerException("비회원 주문은 미지원입니다.");
        }

        TempOrderDto orderDto = orderDtoFactory.create(userId);
        orderDto.setItemList(new ArrayList<>());

        // 주문 상품 추가
        for (int i = 0; i < ordProdList.size(); i++) {
            String isbn = ordProdList.get(i).get("isbn").toString();
            String prodTypeCode = ordProdList.get(i).get("prod_type_code").toString();
            // 유효하지 않은 도서 유형 처리
            if (!BookType.isValid(prodTypeCode)) {
                throw new IllegalArgumentException("유효한 도서 유형 코드가 아닙니다.");
            }

            int itemQuantity = Integer.parseInt(ordProdList.get(i).get("item_quan").toString());
            // itemQuantity > 0 (Front 에서 alert 처리)
            if (itemQuantity <= 0) {
                throw new IllegalArgumentException("itemQuantity 는 1 이상부터 가능합니다.");
            }

            // 찾을 수 없는 도서 예외처리
            TempBookDto bookDto = bookService.read(isbn);
            if (bookDto == null) {
                throw new IllegalArgumentException("도서를 찾을 수 없습니다.");
            }

            // 상품 유형별 가격정보 셋팅
            if (prodTypeCode.equals(BookType.PRINTED.getCode())) {
                Integer paperPrice = bookDto.getPapr_pric();
                Double paperPointPercent = bookDto.getPapr_point();
                Double paperBenefitPercent = bookDto.getPapr_disc();
                if (paperPrice == null || paperPointPercent == null || paperBenefitPercent == null) {
                    throw new IllegalArgumentException("유효하지 않은 도서 유형입니다. 가격정보가 없습니다.");
                }
            } else if (prodTypeCode.equals(BookType.EBOOK.getCode())) {
                Integer eBookPrice = bookDto.getE_pric();
                Double eBookPointPercent = bookDto.getE_point();
                Double eBookBenefitPercent = bookDto.getE_disc();
                if (eBookPrice == null || eBookPointPercent == null || eBookBenefitPercent == null) {
                    throw new IllegalArgumentException("유효하지 않은 도서 유형입니다. 가격정보가 없습니다.");
                }
            }

            TempOrderProductDto tempOrderProductDto = OrderProductDtoFactory.create(orderDto, bookDto, prodTypeCode, itemQuantity);
            orderDto.getItemList().add(tempOrderProductDto);
        }

        // 주문상품 전부의 가격을 합산
        int totalProductPrice = orderDto.getItemList().stream().mapToInt(item -> item.getBasic_pric() * item.getItem_quan()).sum();
        int totalBenefitPrice = orderDto.getItemList().stream().mapToInt(item -> item.getBene_pric() * item.getItem_quan()).sum();
        int totalOrderPrice = orderDto.getItemList().stream().mapToInt(TempOrderProductDto::getOrd_pric).sum();
        int totalPointPrice = orderDto.getItemList().stream().mapToInt(item -> item.getPoint_pric() * item.getItem_quan()).sum();

        orderDto.setTotal_prod_pric(totalProductPrice);
        orderDto.setTotal_bene_pric(totalBenefitPrice);
        orderDto.setTotal_ord_pric(totalOrderPrice);

        // order와 orderproduct 를 등록한다.
        Integer orderSeq = tempOrderDao.insertAndReturnSeq(orderDto);
        orderDto.getItemList().forEach(item -> {
            item.setOrd_seq(orderSeq);
            Integer orderProdSeq = tempOrderProductDao.insertAndReturnSeq(item);
        });

        return orderDto;
    }
}
