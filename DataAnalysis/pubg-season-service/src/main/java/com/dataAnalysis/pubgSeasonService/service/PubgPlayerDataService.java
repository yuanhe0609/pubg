package com.dataAnalysis.pubgSeasonService.service;

public interface PubgPlayerDataService {
    String getPlayerIdByName(String playerName);

    String getPlayerNameById(String playerId);
}
