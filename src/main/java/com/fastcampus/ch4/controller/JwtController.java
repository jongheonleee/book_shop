package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8000")  // Flask 서버 주소 허용
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        // JWT 토큰에서 "Bearer " 부분 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // JWT 검증
        String userID = jwtUtil.validateToken(token);
        if (userID != null) {
            // 검증 성공 시 true와 userID 반환
            return ResponseEntity.ok().body(
                    Map.of("valid", true, "userID", userID)
            );
        } else {
            // 검증 실패 시 false 반환
            return ResponseEntity.ok().body(
                    Map.of("valid", false)
            );
        }
    }
}