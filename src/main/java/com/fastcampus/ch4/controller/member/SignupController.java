package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.TermDto;
import com.fastcampus.ch4.service.member.MemberManagementService;
import com.fastcampus.ch4.service.member.SignUpService;
import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/signup")
public class SignupController {

  private final MemberManagementService memberManagementService;
  private final SignUpService signUpService;
  private final TermService termService;

  @Autowired
  public SignupController(MemberManagementService memberManagementService, SignUpService signUpService, TermService termService) {
    this.memberManagementService = memberManagementService;
    this.signUpService = signUpService;
    this.termService = termService;
  }

  // 회원가입 폼을 보여주는 메서드
  @GetMapping
  public String showSignupForm(Model model) {
    // 약관 데이터를 조회하여 모델에 추가
    List<TermDto> terms = termService.getAllTerms();
    model.addAttribute("terms", terms);
    return "member/signup"; // /WEB-INF/views/member/signup.jsp를 반환
  }

  @PostMapping
  public String processSignup(
          @RequestParam("id") String username,
          @RequestParam("email") String email,
          @RequestParam("pswd") String password,
          @RequestParam(value = "requiredTermAgreedTermIds", required = false) List<Integer> requiredTerms,
          @RequestParam(value = "optionalTermAgreedTermIds", required = false) List<Integer> optionalTerms,
          @RequestParam("address") String address,
          Model model) {

    try {
      // 회원가입 처리 로직
      MemberDto member = new MemberDto();
      member.setId(username);
      member.setEmail(email);
      member.setPswd(password);

      signUpService.processSignup(member, requiredTerms, optionalTerms, address);
    } catch (IllegalArgumentException e) {
      // 예외가 발생한 경우 에러 메시지를 모델에 추가하고 폼으로 돌아감
      model.addAttribute("errorMessage", e.getMessage());
      return "member/signUp";
    }
    return "redirect:/signup/signupSuccess";
  }

  @GetMapping("/signupSuccess")
  public String showSignupSuccess(Model model) {
    return "member/signupSuccess"; // /WEB-INF/views/member/signupSuccess.jsp를 반환
  }
}
