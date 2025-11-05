
package com.dataAnalysis.pubgUserService.filter;

import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import com.dataAnalysis.pubgUserService.service.PubgUserService;
import com.dataAnalysis.pubgUserService.utils.GenerateTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private GenerateTokenUtil generateTokenUtil;
    @Resource
    private PubgUserService pubgUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (token != null) {
            try {
                // 验证token并获取用户信息
                String username = generateTokenUtil.validateAndExtractUsername(token);
                if (username != null) {
                    PubgUserEntity user = pubgUserService.findByUserName(username);
                    // 将认证信息设置到SecurityContext中
                    // SecurityContextHolder.getContext().setAuthentication(...);
                    if (user != null) {
                        // 创建认证对象
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        user,  // principal
                                        null,  // credentials
                                        Collections.emptyList()  // authorities
                                );

                        // 设置认证信息到SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e) {
                log.debug("JWT token expired for user");
                SecurityContextHolder.clearContext();
            } catch (UnsupportedJwtException e) {
                log.debug("JWT token unsupported");
                SecurityContextHolder.clearContext();
            } catch (MalformedJwtException e) {
                log.debug("JWT token malformed");
                SecurityContextHolder.clearContext();
            } catch (Exception e) {
                log.error("Unexpected error during token validation", e);
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}