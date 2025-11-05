package com.dataAnalysis.pubgUserService.utils;


// 在 PubgUserServiceImpl 类中添加

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class GenerateTokenUtil {
    private Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long tokenExpiration = 86400000; // 24小时

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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

    public String validateAndExtractUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtKey)      // 使用相同密钥验证签名
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();         // 提取用户名
        } catch (Exception e) {
            // token无效或过期
            throw new RuntimeException("Invalid token");
        }
    }

    public JSONObject validateToken(String token,PubgUserEntity user) {
        JSONObject result = new JSONObject();
        result.put("isValid", false);

        try {
            String username = validateAndExtractUsername(token);

            // 查询用户信息
            if (user != null && username.equals(user.getUserName())) {
                result.put("isValid", true);
                result.put("user", user);
            }
        } catch (Exception e) {
            result.put("message", "Token验证失败");
        }

        return result;
    }
}
