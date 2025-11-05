package com.dataAnalysis.pubgUserService.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import com.dataAnalysis.pubgUserService.mapper.PubgUserMapper;
import com.dataAnalysis.pubgUserService.service.PubgUserService;
import com.dataAnalysis.pubgUserService.utils.GenerateTokenUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PubgUserServiceImpl implements  PubgUserService {

    @Resource
    private PubgUserMapper userMapper;
    @Resource
    private GenerateTokenUtil generateTokenUtil;

    @Override
    public JSONObject register(PubgUserEntity pubgUserEntity) {
        JSONObject result = new JSONObject();
        result.put("isSuccess", false);
        result.put("message", "");
        if (pubgUserEntity != null) {
            if (StringUtils.isNotBlank(pubgUserEntity.getUserName())
                    && StringUtils.isNotBlank(pubgUserEntity.getUserPassword())
                    && StringUtils.isNotBlank(pubgUserEntity.getUserPubgName())) {

                // 密码加密处理
                String encryptedPassword = encryptPassword(pubgUserEntity.getUserPassword());
                pubgUserEntity.setUserPassword(encryptedPassword);

                // TODO: 保存用户信息到数据库

                result.put("isSuccess", true);
                result.put("message", "注册成功");
            } else {
                result.put("message", "用户名、密码或PUBG名称不能为空");
            }
        } else {
            result.put("message", "用户信息不能为空");
        }
        return result;
    }

    @Override
    public JSONObject login(PubgUserEntity pubgUserEntity) {
        JSONObject result = new JSONObject();
        result.put("isSuccess", false);
        result.put("message", "");

        if (pubgUserEntity != null) {
            if (StringUtils.isNotBlank(pubgUserEntity.getUserName())
                    && StringUtils.isNotBlank(pubgUserEntity.getUserPassword())) {

                // 从数据库查询用户信息
                PubgUserEntity dbUser = userMapper.selectOne(new QueryWrapper<PubgUserEntity>().eq("user_name",pubgUserEntity.getUserName()));

                if (dbUser != null) {
                    // 使用BCrypt验证密码
                    if (BCrypt.checkpw(pubgUserEntity.getUserPassword(), dbUser.getUserPassword())) {
                        result.put("isSuccess", true);
                        result.put("message", "登录成功");

                        // 生成Token
                        String token = generateTokenUtil.generateToken(dbUser);
                        result.put("token", token);
                        result.put("user", dbUser);
                    } else {
                        result.put("message", "密码错误");
                    }
                } else {
                    result.put("message", "用户不存在");
                }
            } else {
                result.put("message", "用户名或密码不能为空");
            }
        } else {
            result.put("message", "用户信息不能为空");
        }

        return result;
    }

    @Override
    public PubgUserEntity findByUserName(String userName) {
        return userMapper.selectOne(new QueryWrapper<PubgUserEntity>().eq("user_name", userName));
    }

    private String encryptPassword(String password) {
        // 使用BCrypt加密密码
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
