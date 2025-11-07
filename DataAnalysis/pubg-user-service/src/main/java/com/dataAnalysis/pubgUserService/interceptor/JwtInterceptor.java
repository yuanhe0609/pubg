package com.dataAnalysis.pubgUserService.interceptor;

import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgUserService.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.SignatureException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getMethod());
        if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            String authHeader = request.getHeader("Authorization");

            // 检查 Authorization 头是否存在且格式正确
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(request, response, Result.error("缺少有效的认证令牌"));
            }

            // 提取实际的 JWT token（去除 "Bearer " 前缀）
            String token = authHeader.substring(7);

            try {
                jwtUtil.parseJWT(token);
            } catch (SignatureException e) {
                return unauthorized(request, response, Result.error("无效签名"));
            } catch (UnsupportedJwtException e) {
                return unauthorized(request, response, Result.error("不支持的签名"));
            } catch (ExpiredJwtException e) {
                return unauthorized(request, response, Result.error("token过期"));
            } catch (MalformedJwtException e) {
                return unauthorized(request, response, Result.error("不支持的签名格式"));
            } catch (Exception e) {
                return unauthorized(request, response, Result.error("token无效"));
            }
        }
        return true;
    }

    private boolean unauthorized(HttpServletRequest request, HttpServletResponse response, Result<Object> result) throws Exception {
        String json = new ObjectMapper().writeValueAsString(result);
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
