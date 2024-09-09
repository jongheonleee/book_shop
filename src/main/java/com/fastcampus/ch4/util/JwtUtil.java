package com.fastcampus.ch4.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("classpath:properties/jwt.properties")
public class JwtUtil {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long expirationTime;

    @Value("${jwt.signature_algorithm}")
    private String signatureAlgorithm;


    public String generateToken(String id) {
        // signatureAlgorithm을 SignatureAlgorithm Enum으로 변환
        SignatureAlgorithm algorithm = SignatureAlgorithm.forName(signatureAlgorithm);

        String generatedToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("hongildong")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("userID", id)
                .signWith(algorithm, secretKey)  // Base64 인코딩 없이 비밀키 사용
                .compact();

        System.out.println("jwtUtils 생성된 토큰: " + generatedToken);
        System.out.println("사용한 비밀키: " + secretKey);
        System.out.println("사용한 알고리즘: " + algorithm);
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