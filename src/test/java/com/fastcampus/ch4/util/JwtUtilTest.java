package com.fastcampus.ch4.util;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testGenerateToken() {
        // given
        String userId = "testUser";

        // when
        String token = jwtUtil.generateToken(userId);

        // then
        assertNotNull(token);  // 토큰이 생성되었는지 확인
        System.out.println("Generated Token: " + token);
    }

    @Test
    public void testValidateToken_ValidToken() {
        // given
        String userId = "testUser";
        String token = jwtUtil.generateToken(userId);

        // when
        String validatedUserId = jwtUtil.validateToken(token);

        // then
        assertEquals(userId, validatedUserId);  // 검증된 userID가 올바른지 확인
    }

    @Test
    public void testValidateToken_InvalidToken() {
        // given
        String userId = "testUser";
        String token = jwtUtil.generateToken(userId);
        String tamperedToken = token + "tampered";  // 유효하지 않은 토큰 생성

        // when
        String validatedUserId = jwtUtil.validateToken(tamperedToken);

        // then
        assertNull(validatedUserId);  // 유효하지 않은 토큰은 null을 반환해야 함
    }
}