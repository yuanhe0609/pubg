package com.dataAnalysis.pubgCommonService.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Getter
public class JwtUtil {
    private String secretKey;

    private int expirationTime; // 3 days

    @Value("${jwt.secret}")
    private void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Value("${jwt.expiration}")
    private void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public SecretKey key() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserDetails user) {
        System.out.println(secretKey);
        return Jwts.builder()
                .setSubject(user.getUsername()) // 主题
                .setIssuedAt(new Date()) // 签发时间
                .setIssuer("Natuie")
                .claim("password", user.getPassword()) // 密码
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 过期时间
                .signWith(key()) // 签名
                .compact(); // 压缩
    }

    public Jws<Claims> parseJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        try {
            parseJWT(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Token过期
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
