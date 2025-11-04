package com.dataAnalysis.pubgUserService.service;

import com.alibaba.fastjson2.JSONObject;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;

public interface PubgUserService {
    JSONObject register(PubgUserEntity pubgUserEntity);

    JSONObject login(PubgUserEntity pubgUserEntity);
    PubgUserEntity findByUserName(String userName);
}
