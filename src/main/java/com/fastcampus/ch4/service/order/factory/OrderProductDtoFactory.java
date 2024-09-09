package com.fastcampus.ch4.service.order.factory;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import com.fastcampus.ch4.model.order.BookType;

import static com.fastcampus.ch4.model.order.OrderConstants.*;

public class OrderProductDtoFactory {
    static final String PRINTED = BookType.PRINTED.getCode();
    static final String EBOOK = BookType.EBOOK.getCode();

    static public TempOrderProductDto create(TempOrderDto orderDto, TempBookDto bookDto, String prodTypeCode, Integer itemQuantity) {
        String isbn = bookDto.getIsbn();
        String orderStatus = BASIC_ORDER_STATUS.getCode();
        String deliveryStatus = BASIC_DELIVERY_STATUS.getCode();
        String paymentStatus = BASIC_PAYMENT_STATUS.getCode();

        Integer basicPrice = 0;
        Double pointPercent = 0.0;
        Integer pointPrice;
        Double benefitPercent = 0.0;
        Integer benefitPrice;
        Integer salePrice;
        Integer orderPrice;

        if (prodTypeCode.equals(PRINTED)) {
            basicPrice = bookDto.getPapr_pric();
            pointPercent = bookDto.getPapr_point();
            benefitPercent = bookDto.getPapr_disc();
        } else if (prodTypeCode.equals(EBOOK)) {
            pointPercent = bookDto.getE_point();
            basicPrice = bookDto.getE_pric();
            benefitPercent = bookDto.getE_disc();
        }

        pointPrice = (int) (basicPrice * pointPercent);
        benefitPrice = (int) (basicPrice * benefitPercent);
        salePrice = basicPrice - benefitPrice;
        orderPrice = salePrice * itemQuantity;

        String userId = orderDto.getUserId();
        Integer orderSeq = orderDto.getOrd_seq();

        return new TempOrderProductDto.Builder()
                .ord_seq(orderSeq)
                .isbn(isbn)
                .prod_type_code(PRINTED)
                .ord_stat(orderStatus)
                .deli_stat(deliveryStatus)
                .pay_stat(paymentStatus)
                .item_quan(itemQuantity)
                .basic_pric(basicPrice)
                .point_perc(pointPercent)
                .point_pric(pointPrice)
                .bene_perc(benefitPercent)
                .bene_pric(benefitPrice)
                .sale_pric(salePrice)
                .ord_pric(orderPrice)
                .reg_id(userId)
                .up_id(userId)
                .build();
    }

}
