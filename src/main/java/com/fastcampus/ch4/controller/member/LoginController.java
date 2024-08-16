package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member")
public class LoginController {

  @Autowired
  private MemberService memberService;

  @RequestMapping("/login")
  public String showLoginForm() {
    return "member/login"; // 해당 JSP 파일이 존재해야 함
  }

  @PostMapping("/login")
  public String login(@RequestParam("id") String id,
                      @RequestParam("pswd") String password,
                      HttpServletRequest request,
                      Model model) {


    try {


      boolean isAuthenticated = memberService.login(id, password, request);

      if (isAuthenticated) {
        // 로그인 성공 시 세션에 사용자 정보를 저장
        request.getSession().setAttribute("id", id);
        model.addAttribute("message", "환영합니다, " + id + "님!");
        return "redirect:/home";
      } else {
        model.addAttribute("error", "잘못된 사용자 이름 또는 비밀번호입니다.");
        return "member/login";
      }
    } catch (Exception e) {
      model.addAttribute("error", "로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "member/login";
    }
  }
  @PostMapping("/logout")
  public String logout(HttpServletRequest request, Model model) {
    try {
      // 세션에서 사용자 정보를 삭제
      request.getSession().invalidate();
      model.addAttribute("message", "로그아웃되었습니다.");
      return "redirect:/login";
    } catch (Exception e) {
      model.addAttribute("error", "로그아웃 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "error";
    }
  }
}
