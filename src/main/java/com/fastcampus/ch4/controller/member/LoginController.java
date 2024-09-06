package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.AccountUnlockService;
import com.fastcampus.ch4.service.member.MemberAuthenticationService;
import com.fastcampus.ch4.service.member.MemberManagementService;
import com.fastcampus.ch4.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
public class LoginController {

  @Autowired
  private MemberAuthenticationService memberAuthenticationService;

  @Autowired
  private MemberManagementService memberManagementService;

  @Autowired
  private AccountUnlockService accountUnlockService; // 비밀번호 재설정 서비스

  @Autowired
  private JwtUtil jwtUtil;  // JwtUtil을 의존성 주입받음

  @RequestMapping("/login")
  public String showLoginForm() {
    return "member/login"; // 로그인 페이지 JSP
  }

  @PostMapping("/login")
  public String login(@RequestParam("id") String id,
                      @RequestParam("pswd") String password,
                      HttpServletRequest request,
                      HttpServletResponse response,
                      Model model) {
    try {
      boolean isAuthenticated = memberAuthenticationService.login(id, password, request);

      if (isAuthenticated) {
        // JWT 토큰 생성
        String token = jwtUtil.generateToken(id);
        System.out.println("로그인 컨트롤러 생성된 토큰:" + token);
        System.out.println(jwtUtil.validateToken(token));
        // 응답 헤더에 JWT 토큰 추가
        response.setHeader("Authorization", "Bearer " + token);

        // 세션에 사용자 정보 저장 (필요한 경우)
//        request.getSession().setAttribute("token", token);

        // 로그인 성공 시 홈으로 리디렉션
        return "index";
      } else {
        // 로그인 실패 처리
        MemberDto member = memberManagementService.getMemberById(id);
        if (member != null) {
          if ("Y".equals(member.getAcntLock())) {
            model.addAttribute("error", "비밀번호를 3회 이상 틀렸습니다. 계정이 잠겼습니다.");
            return "/member/enter-token";  // 비밀번호 재설정 페이지로 리디렉션
          } else {
            model.addAttribute("error", "잘못된 비밀번호입니다. 남은 기회: " + (3 - member.getFailLognAtmt()));
          }
        } else {
          model.addAttribute("error", "존재하지 않는 사용자입니다.");
        }
        return "/member/login";  // 로그인 실패 시 로그인 페이지로 리디렉션
      }
    } catch (Exception e) {
      model.addAttribute("error", "로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "/member/login";  // 오류 발생 시 로그인 페이지로 리디렉션
    }
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
    try {
      // 세션에서 사용자 정보를 삭제(필요시)
//      request.getSession().invalidate();

      // JWT 무효화를 위해 클라이언트의 토큰 삭제 요청
      response.setHeader("Authorization", "");
      model.addAttribute("message", "로그아웃되었습니다.");

      // 추가적으로 토큰 블랙리스트 처리 (선택 사항)
//       String token = request.getHeader("Authorization");
//       JwtBlacklistService.addToBlacklist(token); // 블랙리스트 처리 예시
      return "redirect:/member/login";
    } catch (Exception e) {
      model.addAttribute("error", "로그아웃 처리 중 오류가 발생했습니다: " + e.getMessage());
      return "error";
    }
  }
}
