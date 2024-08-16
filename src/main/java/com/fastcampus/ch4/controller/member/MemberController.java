package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.MemberService;
import com.fastcampus.ch4.service.member.InfoChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

  @Autowired
  private MemberService memberService;

  @Autowired
  private InfoChangeHistoryService infoChangeHistoryService;

  @GetMapping("/editProfile")
  public String showEditProfileForm(HttpServletRequest request, Model model) {
    MemberDto currentUser = memberService.getCurrentUser(request);
    model.addAttribute("id", currentUser);
    return "/member/editProfile"; // editProfile.jsp 페이지로 이동
  }

  @PostMapping("/updateProfile")
  public String updateProfile(MemberDto user, HttpServletRequest request, Model model) {
    String userId = (String) request.getSession().getAttribute("id");
    if (userId == null) {
      throw new IllegalStateException("세션에서 사용자 정보를 찾을 수 없습니다.");
    }

    boolean isUpdated = infoChangeHistoryService.updateUserProfile(userId, user);
    if (isUpdated) {
      model.addAttribute("message", "Profile updated successfully!");
    } else {
      model.addAttribute("message", "Failed to update profile!");
    }
    return "result";
  }
}
