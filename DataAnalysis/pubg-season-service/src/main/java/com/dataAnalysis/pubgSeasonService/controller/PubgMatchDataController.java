package com.dataAnalysis.pubgSeasonService.controller;

import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgSeasonService.service.PubgMatchDataService;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pubg/match")
@Validated
public class PubgMatchDataController {

    @Resource
    private PubgMatchDataService pubgMatchDataService;

    @GetMapping("/getMatchIds")
    public Result<?> getMatchIds(@RequestParam @NotBlank String playerName) {
        return Result.ok(pubgMatchDataService.getPubgMatchIds(playerName));
    }

    @GetMapping("/getMatchData")
    public Result<?> getMatchData(@RequestParam @NotBlank String playerName,
                                  @RequestParam @NotBlank String matchId) {
        return Result.ok(pubgMatchDataService.getPubgMatchData(playerName, matchId));
    }
}
