package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.AccountUnlockService;
import com.fastcampus.ch4.service.member.MemberAuthenticationService;
import com.fastcampus.ch4.service.member.MemberManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/member")
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  private MemberAuthenticationService memberAuthenticationService;

  @Autowired
  private MemberManagementService memberManagementService;

  @Autowired
  private AccountUnlockService accountUnlockService;

  @RequestMapping("/login")
  public String showLoginForm() {
    return "member/login";
  }

  @PostMapping("/login")
  public String login(@RequestParam("id") String id,
                      @RequestParam("pswd") String password,
                      HttpServletRequest request,
                      Model model) {
    try {
      logger.info("Processing login for user ID: {}", id);
      boolean isAuthenticated = memberAuthenticationService.login(id, password, request);

      if (isAuthenticated) {
        request.getSession().setAttribute("id", id);
        model.addAttribute("message", "환영합니다, " + id + "님!");
        logger.info("Login successful for user ID: {}", id);
        return "redirect:/home";
      } else {
        return "redirect:/member/request-reset-token"; // 계정 잠김 시 이메일 발송은 서비스에서 처리
      }
    } catch (Exception e) {
      logger.error("Error during login processing for user ID: {}", id, e);
      model.addAttribute("error", "로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "member/login";
    }
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request, Model model) {
    try {
      request.getSession().invalidate();
      model.addAttribute("message", "로그아웃되었습니다.");
      logger.info("User logged out successfully.");
      return "redirect:/member/login";
    } catch (Exception e) {
      logger.error("Error during logout processing", e);
      model.addAttribute("error", "로그아웃 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "error";
    }
  }

  @RequestMapping("/member/enter-token")
  public String showEnterTokenPage() {
    return "member/enter-token"; // JSP 파일의 경로가 /WEB-INF/views/member/enter-token.jsp에 있어야 합니다.
  }

  @PostMapping("/member/enter-token")
  public String processToken(@RequestParam("token") String token,
                             @RequestParam("newPassword") String newPassword,
                             Model model) {
    try {
      // 토큰 검증 및 비밀번호 재설정 처리
      boolean isValidToken = accountUnlockService.validatePasswordResetToken(token);

      if (isValidToken) {
        accountUnlockService.resetPassword(token, newPassword);
        model.addAttribute("message", "비밀번호가 성공적으로 재설정되었습니다. 다시 로그인해주세요.");
        return "redirect:/member/login";
      } else {
        model.addAttribute("error", "유효하지 않은 토큰입니다. 다시 시도해주세요.");
        return "member/enter-token";
      }
    } catch (Exception e) {
      logger.error("Error processing token", e);
      model.addAttribute("error", "토큰 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "member/enter-token";
    }
  }
}
