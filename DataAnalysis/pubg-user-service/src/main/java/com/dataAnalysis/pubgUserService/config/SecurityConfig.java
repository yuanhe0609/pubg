package com.dataAnalysis.pubgUserService.config;

import com.dataAnalysis.pubgUserService.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    // 配置自定义的 UserDetailsService
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsServiceImpl();
    }

    // 配置密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                // formLogin() 用于配置表单登录功能
                // AbstractAuthenticationFilterConfigurer::permitAll 表示允许所有用户访问表单登录页面
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                // httpBasic() 启用 HTTP 基本认证
                // withDefaults() 表示使用默认的 HTTP 基本认证配置
                .httpBasic(withDefaults());

        return http.build();
    }
}
