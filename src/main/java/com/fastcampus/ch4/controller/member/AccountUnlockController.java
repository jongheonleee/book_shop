package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.service.member.AccountUnlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountUnlockController {

  @Autowired
  private AccountUnlockService accountUnlockService;

  @GetMapping("/request-reset-token")
  public String showRequestTokenPage() {
    return "member/enter-token"; // 비밀번호 재설정 토큰 입력 페이지
  }

  @PostMapping("/verify-token")
  public String verifyToken(@RequestParam("token") String token, Model model) {
    boolean isValid = accountUnlockService.validatePasswordResetToken(token);
    if (isValid) {
      model.addAttribute("token", token);
      return "member/reset-password"; // 비밀번호 재설정 페이지
    } else {
      model.addAttribute("error", "유효하지 않은 토큰입니다.");
      return "member/error"; // 유효하지 않은 토큰일 때 보여줄 페이지 (error.jsp 등)
    }
  }

  @PostMapping("/reset-password")
  public String resetPassword(@RequestParam("token") String token,
                              @RequestParam("newPassword") String newPassword,
                              Model model) {
    try {
      accountUnlockService.resetPassword(token, newPassword);
      return "redirect:/password-reset-success"; // 성공 페이지로 리다이렉트
    } catch (RuntimeException e) {
      model.addAttribute("error", e.getMessage());
      return "member/error"; // 에러 페이지 (error.jsp 등)
    }
  }
}
