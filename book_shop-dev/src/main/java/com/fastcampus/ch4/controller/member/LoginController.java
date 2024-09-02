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

@Controller
@RequestMapping("/member")
public class LoginController {

  @Autowired
  private MemberAuthenticationService memberAuthenticationService;

  @Autowired
  private MemberManagementService memberManagementService;

  @Autowired
  private AccountUnlockService accountUnlockService; // 비밀번호 재설정 서비스

  @RequestMapping("/login")
  public String showLoginForm() {
    return "member/login"; // 로그인 페이지 JSP
  }

  @PostMapping("/login")
  public String login(@RequestParam("id") String id,
                      @RequestParam("pswd") String password,
                      HttpServletRequest request,
                      Model model) {
    try {
      boolean isAuthenticated = memberAuthenticationService.login(id, password, request);

      if (isAuthenticated) {
        // 로그인 성공 시 세션에 사용자 정보를 저장
        request.getSession().setAttribute("id", id);
        model.addAttribute("message", "환영합니다, " + id + "님!");
        return "redirect:/home";
      } else {
        // 로그인 실패 처리
        MemberDto member = memberManagementService.getMemberById(id); // 사용자 정보를 다시 가져오기
        if (member != null) {
          // 로그인 실패 횟수 확인

          if ("Y".equals(member.getAcntLock())) {
            // 계정 잠금 상태인 경우 비밀번호 재설정 페이지로 리디렉션
            model.addAttribute("error", "비밀번호를 3회 이상 틀렸습니다. 계정이 잠겼습니다.");
            return "member/enter-token";// 비밀번호 재설정 요청 페이지로 리디렉션
          } else {
            // 로그인 실패 메시지 처리
            model.addAttribute("error", "잘못된 비밀번호입니다. 남은 기회: " + (3 - member.getFailLognAtmt()));
          }
        } else {
          model.addAttribute("error", "존재하지 않는 사용자입니다.");
        }
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
      return "redirect:/member/login";
    } catch (Exception e) {
      model.addAttribute("error", "로그아웃 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "error";
    }
  }
}
