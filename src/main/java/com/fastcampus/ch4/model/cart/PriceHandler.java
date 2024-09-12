package com.fastcampus.ch4.model.cart;

import com.fastcampus.ch4.dto.cart.PriceManipulatable;

public class PriceHandler {
    // 할인액 계산 함수
    private static int benefitPrice (PriceManipulatable bookDto, int basicPrice, double benefitPercent) {
        return (int) (basicPrice * (benefitPercent / 100));
    }

    // PRINTED 도서 할인액 산출 함수
    public static int pritedBenefitPrice (PriceManipulatable dtoInstance) {
        return benefitPrice(dtoInstance, dtoInstance.getPapr_pric(), dtoInstance.getPapr_disc());
    }

    // EBOOK 도서 할인액 산출 함수
    public static int eBookBenefitPrice (PriceManipulatable bookDto) {
        return benefitPrice(bookDto, bookDto.getE_pric(), bookDto.getE_disc());
    }

    // 적립액 계산 함수
    private static int pointPrice (PriceManipulatable bookDto, int basicPrice, double pointPercent) {
        return (int) (basicPrice * (pointPercent / 100));
    }

    // PRINTED 도서 적립액 산출 함수
    public static int printedPointPrice (PriceManipulatable bookDto) {
        return pointPrice(bookDto, bookDto.getPapr_pric(), bookDto.getPapr_point());
    }

    // EBOOK 도서 적립액 산출 함수
    public static int eBookPointPrice (PriceManipulatable bookDto) {
        return pointPrice(bookDto, bookDto.getE_pric(), bookDto.getE_point());
    }
}
