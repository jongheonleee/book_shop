package com.fastcampus.ch4.controller.order;

import com.fastcampus.ch4.dto.order.request.OrderCreateDto;
import com.fastcampus.ch4.dto.order.response.OrderViewDto;
import com.fastcampus.ch4.service.order.OrderService;
import com.fastcampus.ch4.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    JwtUtil jwtUtil;

    // 주문 데이터 조회
    @PostMapping(value = "/orderView")
    public String orderView(OrderCreateDto orderCreateDto, Model model, HttpServletRequest request) {

        // user id 셋팅
        String user_id = (String) request.getSession().getAttribute("id");
        orderCreateDto.setCust_id(user_id);

        // 주문 정보 조회 (DB 조회만)
        OrderViewDto orderViewDto = orderService.getOrderData(orderCreateDto);

        model.addAttribute("orderDto", orderViewDto.getOrderDto());
        model.addAttribute("orderItemList", orderViewDto.getOrderProductDtoList());

        return "order/order";
    }

    // 주문 생성
    @PostMapping(value = "/order")
    @ResponseBody()
    public Object order(@RequestBody OrderCreateDto orderCreateDto, HttpServletRequest request) {

        // user id 셋팅
        String user_id = (String) request.getSession().getAttribute("id");
        orderCreateDto.setCust_id(user_id);

        // 주문 생성
        OrderViewDto orderViewDto = orderService.create(orderCreateDto);

        return orderViewDto;
    }

    @GetMapping(value = "/order")
    public String order(Model model, HttpServletRequest request) {
        return "order/order";
    }

    @GetMapping(value="/order/success")
    public String orderSuccess(Model model, HttpServletRequest request) {
        return "order/success";
    }
}
