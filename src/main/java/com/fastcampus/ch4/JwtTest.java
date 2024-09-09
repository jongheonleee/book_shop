package com.fastcampus.ch4;

import com.fastcampus.ch4.util.JwtUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.fastcampus.ch4")
public class JwtTest {

    public static void main(String[] args) {
        // Spring ApplicationContext에서 JwtUtil 빈을 가져옵니다.
        ApplicationContext context = new AnnotationConfigApplicationContext(JwtTest.class);
        JwtUtil jwtUtil = context.getBean(JwtUtil.class);

        // 1. 토큰 생성
        String userId = "asdfg";
        String token = jwtUtil.generateToken(userId);
        System.out.println("Generated Token: " + token);

        // 2. 토큰 검증
        String validatedUserId = jwtUtil.validateToken(token);
        System.out.println("Validated User ID: " + validatedUserId);

        // 3. 유효하지 않은 토큰 검증 (테스트)
        String invalidToken = token + "tampered";
        String invalidUserId = jwtUtil.validateToken(invalidToken);
        if (invalidUserId == null) {
            System.out.println("Invalid token detected!");
        }
    }
}