package com.dataAnalysis.pubgUserService.utils;


// 在 PubgUserServiceImpl 类中添加

import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class GenerateTokenUtil {
    private Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long tokenExpiration = 86400000; // 24小时

    // 在登录成功后生成Token
    public String generateToken(PubgUserEntity user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpiration);

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtKey)
                .compact();
    }

    public String generateSimpleToken(PubgUserEntity user) {
        String token = UUID.randomUUID().toString();
        // TODO: 将token存储到Redis或数据库中，关联用户信息
        // redisTemplate.opsForValue().set(token, user.getUserName(), Duration.ofHours(24));
        return token;
    }
}
