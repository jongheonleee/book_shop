package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.MemberAuthenticationService;
import com.fastcampus.ch4.service.member.MemberManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IdPasswordController {

  @Autowired
  private MemberAuthenticationService memberAuthenticationService;

  // 아이디 찾기 폼을 보여주는 메서드 (GET 요청)
  @GetMapping("/findId")
  public String showFindIdForm() {
    return "member/findIdForm";
  }

  // 아이디 찾기 요청을 처리하는 메서드 (POST 요청)
  @PostMapping("/findId")
  public String findId(
          @RequestParam String email,
          RedirectAttributes redirectAttributes
  ) {
    MemberDto id = memberAuthenticationService.findUsernameByEmail(email);

    if (id != null) {
      redirectAttributes.addFlashAttribute("id", id.getId()); // "id"라는 이름으로 플래시 속성 설정
    } else {
      redirectAttributes.addFlashAttribute("error", "이메일과 일치하는 아이디가 없습니다."); // "error"라는 이름으로 플래시 속성 설정
    }

    return "redirect:/findIdResult";
  }

  // 비밀번호 찾기 폼을 보여주는 메서드 (GET 요청)
  @GetMapping("/findPassword")
  public String showFindPasswordForm() {
    // 비밀번호 찾기 폼을 표시하는 뷰의 이름을 반환
    return "member/findPasswordForm";
  }

  // 비밀번호 찾기 요청을 처리하는 메서드 (POST 요청)
  @PostMapping("/findPassword")
  public String findPassword(
          @RequestParam String email, // 클라이언트로부터 이메일 파라미터를 받음
          @RequestParam String id,    // 클라이언트로부터 아이디 파라미터를 받음
          RedirectAttributes redirectAttributes // 리다이렉트 시 플래시 속성에 메시지를 추가하기 위한 객체
  ) {
    // 이메일과 아이디를 기반으로 비밀번호를 찾는 서비스 메서드 호출
    String password = memberAuthenticationService.findPasswordByEmailAndId(email, id);

    if (password != null) {
      // 비밀번호를 찾은 경우, 비밀번호를 메시지로 플래시 속성에 추가
      redirectAttributes.addFlashAttribute("message", "비밀번호: " + password);
    } else {
      // 이메일과 아이디가 일치하는 사용자가 없는 경우, 에러 메시지를 플래시 속성에 추가
      redirectAttributes.addFlashAttribute("message", "이메일과 아이디가 일치하는 사용자가 없습니다.");
    }

    // 비밀번호 찾기 결과를 표시하는 페이지로 리다이렉트
    return "redirect:/findPasswordResult";
  }

  // 아이디 찾기 결과 페이지를 보여주는 메서드 (GET 요청)
  @GetMapping("/findIdResult")
  public String showFindIdResult() {
    return "member/findIdResult";
  }

  // 비밀번호 찾기 결과 페이지를 보여주는 메서드 (GET 요청)
  @GetMapping("/findPasswordResult")
  public String showFindPasswordResult() {
    return "member/findPasswordResult";
  }
}
