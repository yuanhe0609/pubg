package com.dataAnalysis.pubgSeasonService.service;

import com.dataAnalysis.pubgSeasonService.entity.PubgSeasonDataEntity;

public interface PubgSeasonDataService {
    PubgSeasonDataEntity getPubgSeasonData(String seasonId, String playerId,String playerName);
}
