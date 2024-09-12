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
import java.util.HashMap;
import java.util.Map;

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
    return "member/loginForm"; // 로그인 페이지 JSP
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestParam("id") String id,
                                 @RequestParam("pswd") String password,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 String toURL) {
    Map<String, Object> result = new HashMap<>();
    try {
      boolean isAuthenticated = memberAuthenticationService.login(id, password, request);
      toURL = (toURL == null || toURL.isEmpty()) ? "/ch4" : toURL;
      System.out.println("toURL = " + toURL);

      if (isAuthenticated) {
        // 로그인 성공 시 세션에 사용자 정보를 저장
        request.getSession().setAttribute("id", id);

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(id);
        response.setHeader("Authorization", "Bearer " + token);

        // 로그인 성공 시 리다이렉트할 URL 전달
        result.put("redirectUrl", toURL);
        result.put("token", token);
        return ResponseEntity.ok(result);
      } else {
        MemberDto member = memberManagementService.getMemberById(id);
        if (member != null && "Y".equals(member.getAcntLock())) {
          result.put("redirectUrl", "/member/enter-token");
        } else {
          result.put("redirectUrl", "/member/loginForm");
        }
        return ResponseEntity.ok(result);
      }
    } catch (Exception e) {
      result.put("redirectUrl", "/member/loginForm");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
    try {
      // 세션에서 사용자 정보를 삭제
      request.getSession().invalidate();

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
