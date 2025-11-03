package com.dataAnalysis.pubgSeasonService.controller;


import com.dataAnalysis.pubgSeasonService.entity.PubgSeasonDataEntity;
import com.dataAnalysis.pubgSeasonService.service.PubgPlayerDataService;
import com.dataAnalysis.pubgSeasonService.service.PubgSeasonDataService;
import com.dataAnalysis.pubgSeasonService.vo.Result;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pubg/season")
public class PubgSeasonDataController {

    @Resource
    private PubgSeasonDataService pubgSeasonDataService;
    @Resource
    private PubgPlayerDataService pubgPlayerDataService;

    @GetMapping(value = "/getData")
    public Result<?> getSeaSonData(@RequestParam @NotBlank String seasonId, @RequestParam @NotBlank String playerId) {
        PubgSeasonDataEntity pubgSeasonDataEntity = pubgSeasonDataService.getPubgSeasonData(seasonId, playerId,null);
        return Result.ok(pubgSeasonDataEntity);
    }

    @GetMapping(value = "/getDataByPlayerName")
    public Result<?> getSeaSonDataByPlayerName(@RequestParam @NotBlank String seasonId, @RequestParam @NotBlank String playerName) {
        String playerId = pubgPlayerDataService.getPlayerIdByName(playerName);
        PubgSeasonDataEntity pubgSeasonDataEntity = pubgSeasonDataService.getPubgSeasonData(seasonId, playerId, playerName);
        return Result.ok(pubgSeasonDataEntity);
    }
}
