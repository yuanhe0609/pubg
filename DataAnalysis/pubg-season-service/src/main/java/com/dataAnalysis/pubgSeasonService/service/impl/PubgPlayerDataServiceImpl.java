package com.dataAnalysis.pubgSeasonService.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgSeasonService.entity.PubgPlayerNameToIdEntity;
import com.dataAnalysis.pubgSeasonService.mapper.PubgPlayerNameToIdMapper;
import com.dataAnalysis.pubgSeasonService.service.PubgPlayerDataService;
import com.dataAnalysis.pubgSeasonService.utils.ApiResponseUtil;
import com.dataAnalysis.pubgSeasonService.utils.PostApiUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PubgPlayerDataServiceImpl implements PubgPlayerDataService {

    @Resource
    private PubgPlayerNameToIdMapper pubgPlayerNameToIdMapper;

    @Override
    public String getPlayerIdByName(String playerName) {
        QueryWrapper<PubgPlayerNameToIdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_name", playerName);
        PubgPlayerNameToIdEntity dbPubgPlayerNameToIdEntity = pubgPlayerNameToIdMapper.selectOne(queryWrapper);
        if(dbPubgPlayerNameToIdEntity != null){
            return dbPubgPlayerNameToIdEntity.getPlayerId();
        }
        try{
            PostApiUtil postApiUtil = new PostApiUtil();
            String url = "https://api.pubg.com/shards/steam/players?filter[playerNames]=" + playerName;
            String method = "GET";
            Map<String, Object> result = postApiUtil.postApi(url, method);
            JSONObject resultData = ApiResponseUtil.checkResultMap(result);
            JSONArray data = resultData.getJSONArray("data");
            if (data == null || data.isEmpty()) {
                throw new RuntimeException("JSON解析失败: data字段不存在");
            }
            JSONObject player = data.getJSONObject(0);
            String playerId = player.getString("id");
            if(StringUtils.isNotBlank(playerId)){
                PubgPlayerNameToIdEntity pubgPlayerNameToIdEntity = new PubgPlayerNameToIdEntity();
                pubgPlayerNameToIdEntity.setPlayerName(playerName);
                pubgPlayerNameToIdEntity.setPlayerId(playerId);
                pubgPlayerNameToIdMapper.insert(pubgPlayerNameToIdEntity);
            }
            return playerId;
        }catch (Exception e){
            throw new RuntimeException("获取PUBG用户ID失败: playerName=" + playerName + ", 错误信息: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPlayerNameById(String playerId) {
        QueryWrapper<PubgPlayerNameToIdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId);
        PubgPlayerNameToIdEntity dbPubgPlayerNameToIdEntity = pubgPlayerNameToIdMapper.selectOne(queryWrapper);
        if(dbPubgPlayerNameToIdEntity != null){
            return dbPubgPlayerNameToIdEntity.getPlayerName();
        }
        try{
            PostApiUtil postApiUtil = new PostApiUtil();
            String url = "https://api.pubg.com/shards/steam/players?filter[playerIds]=" + playerId;
            String method = "GET";
            Map<String, Object> result = postApiUtil.postApi(url, method);
            JSONObject resultData = ApiResponseUtil.checkResultMap(result);
            JSONArray data = resultData.getJSONArray("data");
            if (data == null || data.isEmpty()) {
                throw new RuntimeException("JSON解析失败: data字段不存在");
            }
            JSONObject player = data.getJSONObject(0);
            JSONObject attributes = player.getJSONObject("attributes");
            if (attributes == null) {
                throw new RuntimeException("JSON解析失败: attributes字段不存在");
            }
            String playerName = attributes.getString("name");
            if(StringUtils.isNotBlank(playerName)){
                PubgPlayerNameToIdEntity pubgPlayerNameToIdEntity = new PubgPlayerNameToIdEntity();
                pubgPlayerNameToIdEntity.setPlayerName(playerName);
                pubgPlayerNameToIdEntity.setPlayerId(playerId);
                pubgPlayerNameToIdMapper.insert(pubgPlayerNameToIdEntity);
            }
            return playerName;
        }catch (Exception e){
            throw new RuntimeException("获取PUBG用户ID失败: playerId=" + playerId + ", 错误信息: " + e.getMessage(), e);
        }
    }
}
