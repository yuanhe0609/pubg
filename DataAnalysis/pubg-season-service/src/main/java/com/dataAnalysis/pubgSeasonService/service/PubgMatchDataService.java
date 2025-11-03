package com.dataAnalysis.pubgSeasonService.service;

import com.dataAnalysis.pubgSeasonService.entity.PubgMatchDataEntity;

import java.util.List;

public interface PubgMatchDataService {

    List<String> getPubgMatchIds(String playerName);

    PubgMatchDataEntity getPubgMatchData(String playerName,String matchId);

}
