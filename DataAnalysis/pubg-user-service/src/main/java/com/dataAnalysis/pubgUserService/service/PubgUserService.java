package com.dataAnalysis.pubgUserService.service;

import com.alibaba.fastjson2.JSONObject;
import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;

public interface PubgUserService {
    Result<Object> register(PubgUserEntity pubgUserEntity);

    Result<Object> login(PubgUserEntity pubgUserEntity);
    PubgUserEntity findByUserName(String userName);
}
