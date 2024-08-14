package com.fastcampus.ch4.controller.cart;

import com.fastcampus.ch4.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping()
    public String cartPage() {
        // userId 나 cartSeq 찾기
        // 장바구니를 조회해서 가져오거나 생성해서 반환하기
        // 장바구니 상품 추가하기
        // 비회원 고객일 때 쿠키나 세션에 cart_seq 저장하기
        // model 에 장바구니 상품 목록을 조회해서 보내주기

        return "cart/cart";
    }

}
