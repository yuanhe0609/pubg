package com.dataAnalysis.pubgUserService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgCommonService.utils.RedisUtil;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import com.dataAnalysis.pubgUserService.mapper.PubgUserMapper;
import com.dataAnalysis.pubgUserService.service.PubgUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PubgUserServiceImpl implements  PubgUserService {

    @Resource
    private PubgUserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result<Object> login(PubgUserEntity pubgUserEntity) {
        return null;
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
