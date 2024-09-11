  package com.fastcampus.ch4.controller.member;

  import com.fastcampus.ch4.service.member.AccountUnlockService;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RequestParam;

  @Controller
  @RequestMapping("/member")
  public class AccountUnlockController {

    private static final Logger logger = LoggerFactory.getLogger(AccountUnlockController.class);

    @Autowired
    private AccountUnlockService accountUnlockService;

      @GetMapping("/request-reset-token")
      public String showRequestTokenPage() {
        logger.info("비밀번호 재설정 토큰 입력 페이지 호출됨.");
        return "member/enter-token";
      }

    @PostMapping("/enter-token")
    public String processToken(@RequestParam("token") String token,
                               @RequestParam("newPassword") String newPassword,
                               Model model) {
      logger.info("processToken 메서드 진입 확인");
      logger.info("입력된 토큰: {}, 새 비밀번호: [숨김]", token);

      try {
        boolean isValidToken = accountUnlockService.validatePasswordResetToken(token);

        if (isValidToken) {
          logger.info("토큰이 유효함: {}", token);
          accountUnlockService.resetPassword(token, newPassword);
          logger.info("비밀번호가 성공적으로 재설정되었습니다.");
          return "redirect:/member/login";
        } else {
          logger.warn("유효하지 않은 토큰: {}", token);
          model.addAttribute("error", "유효하지 않은 토큰입니다. 다시 시도해주세요.");
          return "member/enter-token";
        }
      } catch (Exception e) {
        logger.error("토큰 처리 중 오류 발생: {}", e.getMessage());
        model.addAttribute("error", "토큰 처리 중 오류가 발생했습니다: " + e.getMessage());
        return "member/enter-token";
      }
    }

  }
