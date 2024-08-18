package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.TermDto;
import com.fastcampus.ch4.service.member.MemberManagementService;
import com.fastcampus.ch4.service.member.SignUpService;
import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/signup")
public class SignupController {

  @Autowired
  private MemberManagementService memberManagementService;

  @Autowired
  private SignUpService signUpService;

  @Autowired
  private TermService termService;

  // 회원가입 폼을 보여주는 메서드
  @RequestMapping
  public String showSignupForm(Model model) {
    // 약관 데이터를 조회하여 모델에 추가
    List<TermDto> terms = termService.getAllTerms();
    model.addAttribute("member", new MemberDto());
    model.addAttribute("terms", terms);
    return "member/signup"; // /WEB-INF/views/member/signup.jsp를 반환
  }

  @PostMapping
  public String processSignup(@ModelAttribute("member") MemberDto member,
                              @RequestParam(value = "requiredTermAgreedTermIds", required = false) List<Integer> requiredTerms,
                              @RequestParam(value = "optionalTermAgreedTermIds", required = false) List<Integer> optionalTerms,
                              @RequestParam(value = "address", required = true) String address,
                              Model model) {
    try {
      // 회원 가입 처리
      memberManagementService.addMember(member);

      // 약관 동의 정보 저장
      signUpService.processSignup(member, requiredTerms, optionalTerms, address);

    } catch (IllegalArgumentException e) {
      // 예외가 발생한 경우 에러 메시지를 모델에 추가하고 폼으로 돌아감
      model.addAttribute("errorMessage", e.getMessage());
      return "member/signup";
    }
    return "redirect:/signup/signupSuccess";
  }

  @RequestMapping("/signupSuccess")
  public String showSignupSuccess(Model model) {
    return "member/signupSuccess"; // /WEB-INF/views/member/signupSuccess.jsp를 반환
  }
}
