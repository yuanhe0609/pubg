package com.dataAnalysis.pubgSeasonService.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dataAnalysis.pubgSeasonService.entity.PubgPlayerNameToIdEntity;
import com.dataAnalysis.pubgSeasonService.entity.PubgSeasonDataEntity;
import com.dataAnalysis.pubgSeasonService.mapper.PubgPlayerNameToIdMapper;
import com.dataAnalysis.pubgSeasonService.mapper.PubgSeasonDataMapper;
import com.dataAnalysis.pubgSeasonService.service.PubgPlayerDataService;
import com.dataAnalysis.pubgSeasonService.service.PubgSeasonDataService;
import com.dataAnalysis.pubgSeasonService.utils.ApiResponseUtil;
import com.dataAnalysis.pubgSeasonService.utils.PostApiUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Service
public class PubgSeasonDataServiceImpl implements PubgSeasonDataService {

    @Resource
    private PubgSeasonDataMapper pubgSeasonDataMapper;


    @Override
    public PubgSeasonDataEntity getPubgSeasonData(String seasonId, String playerId, String playerName) {
        PubgSeasonDataEntity pubgSeasonDataEntity = new PubgSeasonDataEntity();
        try {
            PostApiUtil postApiUtil = new PostApiUtil();
            String url = "https://api.pubg.com/shards/steam/players/" + playerId + "/seasons/" + seasonId + "/ranked";
            String method = "GET";
            Map<String, Object> result = postApiUtil.postApi(url, method);
            JSONObject resultData = ApiResponseUtil.checkResultMap(result);
            pubgSeasonDataEntity.setSeasonId(seasonId);
            pubgSeasonDataEntity.setPlayerId(playerId);
            JSONObject data = resultData.getJSONObject("data");
            if (data == null) {
                throw new RuntimeException("JSON解析失败: data字段不存在");
            }
            JSONObject attributes = data.getJSONObject("attributes");
            if (attributes == null) {
                throw new RuntimeException("JSON解析失败: attributes字段不存在");
            }
            JSONObject rankedGameModeStats = attributes.getJSONObject("rankedGameModeStats");
            if (rankedGameModeStats == null) {
                throw new RuntimeException("JSON解析失败: rankedGameModeStats字段不存在");
            }
            JSONObject duo = rankedGameModeStats.getJSONObject("duo");
            if (duo != null && !duo.isEmpty()) {
                if(StringUtils.isNotBlank(playerName)){
                    pubgSeasonDataEntity.setPlayerName(playerName);
                }else{
                    PubgPlayerDataService pubgPlayerDataService = new PubgPlayerDataServiceImpl();
                    String apiPlayerName = pubgPlayerDataService.getPlayerNameById(playerId);
                    if(StringUtils.isNotBlank(apiPlayerName)){
                        pubgSeasonDataEntity.setPlayerName(apiPlayerName);
                    }
                }
                pubgSeasonDataEntity.setDuoKills(duo.getInteger("kills"));
                pubgSeasonDataEntity.setDuoAssists(duo.getInteger("assists"));
                pubgSeasonDataEntity.setDuoDeaths(duo.getInteger("deaths"));
                pubgSeasonDataEntity.setDuoKda(duo.getBigDecimal("kda"));
                pubgSeasonDataEntity.setDuoDamageDealt(duo.getInteger("damageDealt"));
                pubgSeasonDataEntity.setDuoTop10Ratio(duo.getBigDecimal("top10Ratio"));
                pubgSeasonDataEntity.setDuoCurrentRankPoint(duo.getInteger("currentRankPoint"));
                pubgSeasonDataEntity.setDuoBestRankPoint(duo.getInteger("bestRankPoint"));
                pubgSeasonDataEntity.setDuoRoundsPlayed(duo.getInteger("roundsPlayed"));
                pubgSeasonDataEntity.setDuoTeamKills(duo.getInteger("teamKills"));
                QueryWrapper<PubgSeasonDataEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("season_id", seasonId).eq("player_id", playerId);
                if (pubgSeasonDataMapper.selectCount(queryWrapper) > 0) {
                    pubgSeasonDataMapper.update(pubgSeasonDataEntity, queryWrapper);
                } else {
                    pubgSeasonDataMapper.insert(pubgSeasonDataEntity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("获取PUBG赛季数据失败: seasonId=" + seasonId + ", playerId=" + playerId + ", 错误信息: " + e.getMessage(), e);
        }
        return pubgSeasonDataEntity;
    }
}
