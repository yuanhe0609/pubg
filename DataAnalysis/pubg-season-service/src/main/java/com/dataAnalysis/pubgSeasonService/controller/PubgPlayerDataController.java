package com.dataAnalysis.pubgSeasonService.controller;

import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgSeasonService.service.PubgPlayerDataService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pubg/player")
@Validated
public class PubgPlayerDataController {

    @Resource
    private PubgPlayerDataService pubgPlayerDataService;

    @GetMapping(value = "/getPlayerId")
    public Result<?> getPlayerIdByName(@RequestParam @NotBlank String playerName) {
        String pubgSeasonDataEntity = pubgPlayerDataService.getPlayerIdByName(playerName);
        return Result.ok(pubgSeasonDataEntity);
    }
}
