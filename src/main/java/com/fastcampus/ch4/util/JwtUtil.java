package com.fastcampus.ch4.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@PropertySource("classpath:properties/jwt.properties")
public class JwtUtil {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long expirationTime;

    @Value("${jwt.signature_algorithm}")
    private String signatureAlgorithm;

    // secretKey를 Base64로 인코딩하여 반환하는 메서드
    private String getBase64EncodedSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(String id) {
        // signatureAlgorithm을 SignatureAlgorithm Enum으로 변환
        SignatureAlgorithm algorithm = SignatureAlgorithm.forName(signatureAlgorithm);

        String generatedToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("hongildong")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("userID", id)
                .signWith(algorithm, secretKey)
                .compact();
        return generatedToken;
    }

    public String validateToken(String token) {
        try {
            // 토큰 검증 및 클레임 추출
            return Jwts.parser()
                    .setSigningKey(secretKey)  // Base64 인코딩 없이 원래 비밀키 사용
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userID", String.class);  // "userID" 클레임에서 값을 추출

        } catch (Exception e) {
            System.out.println("토큰 검증 실패: " + e.getMessage());
            return null;
        }
    }
}