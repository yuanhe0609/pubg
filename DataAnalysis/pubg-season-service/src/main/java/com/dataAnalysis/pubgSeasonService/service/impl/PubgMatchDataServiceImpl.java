package com.dataAnalysis.pubgSeasonService.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgSeasonService.entity.PubgMatchDataEntity;
import com.dataAnalysis.pubgSeasonService.mapper.PubgMatchDataMapper;
import com.dataAnalysis.pubgSeasonService.service.PubgMatchDataService;
import com.dataAnalysis.pubgSeasonService.utils.ApiResponseUtil;
import com.dataAnalysis.pubgSeasonService.utils.PostApiUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PubgMatchDataServiceImpl implements PubgMatchDataService {
    @Resource
    private PubgMatchDataMapper pubgMatchDataMapper;
    @Override
    public List<String> getPubgMatchIds(String playerName) {
        try{
            List<PubgMatchDataEntity> needInsertList = new ArrayList<>();
            List<PubgMatchDataEntity> needUpdateList = new ArrayList<>();
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
            JSONObject relationships = player.getJSONObject("relationships");
            JSONObject matches = relationships.getJSONObject("matches");
            JSONArray matchDataList = matches.getJSONArray("data");
            List<String> matchIds = new ArrayList<>();
            for (int i = 0; i < matchDataList.size(); i++) {
                JSONObject matchData = matchDataList.getJSONObject(i);
                String matchId = matchData.getString("id");
                matchIds.add(matchId);
            }
            List<PubgMatchDataEntity> existingMatches = new ArrayList<>();
            if (!matchIds.isEmpty()) {
                QueryWrapper<PubgMatchDataEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("match_id", matchIds);
                existingMatches = pubgMatchDataMapper.selectList(queryWrapper);
            }
            Set<String> existingMatchIds = existingMatches.stream()
                    .map(PubgMatchDataEntity::getMatchId)
                    .collect(Collectors.toSet());
            for (int i = 0; i < matchDataList.size(); i++) {
                PubgMatchDataEntity pubgMatchDataEntity = new PubgMatchDataEntity();
                JSONObject matchData = matchDataList.getJSONObject(i);
                String matchId = matchData.getString("id");
                pubgMatchDataEntity.setMatchId(matchId);
                pubgMatchDataEntity.setPlayerId(player.getString("id"));
                if (existingMatchIds.contains(matchId)) {
                    needUpdateList.add(pubgMatchDataEntity);
                } else {
                    needInsertList.add(pubgMatchDataEntity);
                }
            }
            if(!needUpdateList.isEmpty()){
                pubgMatchDataMapper.updateBatch(needUpdateList);
            }
            if(!needInsertList.isEmpty()){
                pubgMatchDataMapper.insertBatch(needInsertList);
            }
            return matchIds;
        }catch (Exception e){
            throw new RuntimeException("获取PUBG用户名为" + playerName + "的对局信息失败, 错误信息: " + e.getMessage(), e);
        }
    }

    @Override
    public PubgMatchDataEntity getPubgMatchData(String playerName,String matchId) {
        PostApiUtil postApiUtil = new PostApiUtil();
        String url = "https://api.pubg.com/shards/steam/matches/" + matchId;
        String method = "GET";
        Map<String, Object> result = postApiUtil.postApi(url, method);
        JSONObject resultData = ApiResponseUtil.checkResultMap(result);
        JSONArray included = resultData.getJSONArray("included");
        for (int i = 0; i < included.size(); i++) {
             JSONObject includedData = included.getJSONObject(i);
             JSONObject attributes = includedData.getJSONObject("attributes");
             JSONObject stats = attributes.getJSONObject("stats");
            if(stats != null && playerName.equals(stats.getString("name"))){
                PubgMatchDataEntity pubgMatchDataEntity = new PubgMatchDataEntity();
                JSONObject data = resultData.getJSONObject("data");
                JSONObject matchAttributes = data.getJSONObject("attributes");
                pubgMatchDataEntity.setMatchType(matchAttributes.getString("matchType"));
                pubgMatchDataEntity.setMapName(matchAttributes.getString("mapName"));
                pubgMatchDataEntity.setGameMode(matchAttributes.getString("gameMode"));
                pubgMatchDataEntity.setMatchId(matchId);
                pubgMatchDataEntity.setPlayerId(stats.getString("playerId"));
                pubgMatchDataEntity.setPlayerName(playerName);
                pubgMatchDataEntity.setKnockedNumber(stats.getInteger("DBNOs"));
                pubgMatchDataEntity.setAssist(stats.getInteger("assists"));
                pubgMatchDataEntity.setKills(stats.getInteger("kills"));
                pubgMatchDataEntity.setBoosts(stats.getInteger("boosts"));
                pubgMatchDataEntity.setDamageDealt(stats.getBigDecimal("damageDealt"));
                pubgMatchDataEntity.setDeathType(stats.getString("deathType"));
                pubgMatchDataEntity.setHeadshotKills(stats.getInteger("headshotKills"));
                pubgMatchDataEntity.setHeals(stats.getInteger("heals"));
                pubgMatchDataEntity.setRideDistance(stats.getBigDecimal("rideDistance"));
                pubgMatchDataEntity.setSwimDistance(stats.getBigDecimal("swimDistance"));
                pubgMatchDataEntity.setWalkDistance(stats.getBigDecimal("walkDistance"));
                pubgMatchDataEntity.setRoadKills(stats.getInteger("roadKills"));
                pubgMatchDataEntity.setTeamKills(stats.getInteger("teamKills"));
                pubgMatchDataEntity.setTimeSurvived(stats.getInteger("timeSurvived"));
                pubgMatchDataEntity.setWinPlace(stats.getInteger("winPlace"));
                PubgMatchDataEntity existPubgMatchDataEntity = pubgMatchDataMapper.selectOne(new QueryWrapper<PubgMatchDataEntity>().eq("match_id",matchId).eq("player_id",stats.getString("playerId")));
                if(existPubgMatchDataEntity != null){
                    pubgMatchDataMapper.updateById(pubgMatchDataEntity);
                }
                pubgMatchDataMapper.insert(pubgMatchDataEntity);
                return pubgMatchDataEntity;
            }
        }
        return null;
    }
}
