package com.dataAnalysis.pubgUserService.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername(String username) :: " + username);
        // 模拟从数据库中加载用户信息
        // 后面章节将详细介绍如何从数据中加载数据
        if ("admin".equals(username)) {
            // 创建用户权限列表
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            // 创建 UserDetails 对象
            // 注意，密码通过 BCryptPasswordEncoder 进行加密获得
            // 正常情况下，密码从数据库查询
            String password = new BCryptPasswordEncoder().encode("123456");
            return new User(username, password, authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
