package com.dataAnalysis.pubgUserService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgCommonService.utils.RedisUtil;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import com.dataAnalysis.pubgUserService.mapper.PubgUserMapper;
import com.dataAnalysis.pubgUserService.service.PubgUserService;
import com.dataAnalysis.pubgUserService.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PubgUserServiceImpl implements  PubgUserService {

    @Resource
    private PubgUserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Result<Object> login(PubgUserEntity user) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword());

            // 认证
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            // 生成JWT
            UserDetails loginUser = (UserDetails) authenticate.getPrincipal();
            String jwt = jwtUtil.generateToken(loginUser);

            // 保存到Redis
            Map<String, String> map = Map.ofEntries(Map.entry("token", jwt));
            redisUtil.hmset("TOKEN_" + jwt, map, jwtUtil.getExpirationTime());

            return Result.ok("登陆成功！", jwt);
        } catch (BadCredentialsException e) {
            // 凭证无效，返回错误信息
            return Result.error("凭据无效！");
        } catch (AuthenticationException e) {
            // 其他认证错误，返回错误信息
            return Result.error("认证失败！");
        }
    }

    @Override
    public Result<Object> register(PubgUserEntity user) {

        userMapper.insert(user);
        return Result.ok("注册成功");
    }

    @Override
    public PubgUserEntity findByUserName(String userName) {
        return userMapper.selectOne(new QueryWrapper<PubgUserEntity>().eq("user_name", userName));
    }
}
