package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.ShippingAddressDto;
import com.fastcampus.ch4.service.member.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addresses")
public class ShippingAddressController {

  @Autowired
  private ShippingAddressService shippingAddressService;

  // 주소 추가 폼을 보여줍니다.
  @GetMapping("/add")
  public String showAddAddressForm(Model model) {
    model.addAttribute("shippingAddress", new ShippingAddressDto());
    return "add-address";
  }

  // 주소를 추가합니다.
  @PostMapping("/add")
  public String addAddress(@ModelAttribute ShippingAddressDto shippingAddressDto) {
    shippingAddressService.addAddress(shippingAddressDto);
    return "redirect:/addresses/recent?userId=" + shippingAddressDto.getUserId();
  }

  // 사용자의 최근 주소를 보여줍니다.
}
