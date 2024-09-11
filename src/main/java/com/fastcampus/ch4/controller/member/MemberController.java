package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.dto.member.ShippingAddressDto;
import com.fastcampus.ch4.service.member.MemberManagementService;
import com.fastcampus.ch4.service.member.InfoChangeHistoryService;
import com.fastcampus.ch4.service.member.ShippingAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

  private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

  @Autowired
  private MemberManagementService memberManagementService;

  @Autowired
  private InfoChangeHistoryService infoChangeHistoryService;

  @Autowired
  private ShippingAddressService shippingAddressService;

  // 프로필 수정 폼을 보여주는 메서드 (GET 요청)
  @GetMapping("/editProfile")
  public String showEditProfileForm(HttpServletRequest request, Model model) {
    // 현재 사용자 정보를 가져옴
    MemberDto currentUser = memberManagementService.getCurrentUser(request);
    if (currentUser == null) {
      logger.error("No user found in session.");
      model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
      return "error";
    }

    // 현재 주소 정보를 가져옴
    ShippingAddressDto currentAddress = shippingAddressService.getAddressById(currentUser.getId());

    logger.info("Loaded profile for user ID: {}", currentUser.getId());
    model.addAttribute("user", currentUser);
    model.addAttribute("address", currentAddress);

    return "member/editProfile";
  }

  // 프로필 업데이트를 처리하는 메서드 (POST 요청)
  @PostMapping("/updateProfile")
  public String updateProfile(MemberDto user, ShippingAddressDto addressDto, HttpServletRequest request, Model model) {
    try {
      // 서비스에 업데이트 로직 위임
      boolean isProfileUpdated = memberManagementService.updateUserProfile(request, user, addressDto);
      if (isProfileUpdated) {
        model.addAttribute("message", "프로필이 성공적으로 업데이트되었습니다!");
      } else {
        model.addAttribute("message", "변경 사항이 없습니다.");
      }
      return "member/result";
    } catch (Exception e) {
      logger.error("Error updating profile", e);
      model.addAttribute("error", "프로필 업데이트 중 오류가 발생했습니다.");
      return "error";
    }
  }

}

