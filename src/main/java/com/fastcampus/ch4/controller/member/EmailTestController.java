//package com.fastcampus.ch4.controller.member;
//
//
//import com.fastcampus.ch4.service.member.EmailTestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class EmailTestController {
//
//    @Autowired
//    private EmailTestService emailTestService;
//
//    // 테스트 이메일 전송
//    @GetMapping("/test-email")
//    public String testEmail() {
//        emailTestService.sendTestEmail();
//        return "이메일 전송 테스트 완료";
//    }
//}
