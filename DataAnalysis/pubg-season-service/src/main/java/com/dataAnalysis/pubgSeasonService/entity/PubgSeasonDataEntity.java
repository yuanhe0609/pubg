package com.dataAnalysis.pubgSeasonService.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "pubg_season_data")
public class PubgSeasonDataEntity {
    @TableField(value = "season_id")
    private String seasonId;              // 赛季id
    @TableField(value = "player_id")
    private String playerId;              // 用户id
    @TableField(value = "player_name")
    private String playerName;            // 用户名称
    @TableField(value = "duo_kills")
    private Integer duoKills;             // 双排击杀数
    @TableField(value = "duo_assists")
    private Integer duoAssists;           // 双排助攻数
    @TableField(value = "duo_deaths")
    private Integer duoDeaths;            // 双排死亡数
    @TableField(value = "duo_kda")
    private BigDecimal duoKda;               // 双排kda
    @TableField(value = "duo_current_rank_point")
    private Integer duoCurrentRankPoint;  // 双排分
    @TableField(value = "duo_best_rank_point")
    private Integer duoBestRankPoint;     // 双排最高分
    @TableField(value = "duo_damage_dealt")
    private Integer duoDamageDealt;       // 双排伤害合计
    @TableField(value = "duo_top_10_ratio")
    private BigDecimal duoTop10Ratio;        // 双排前10率
    @TableField(value = "duo_rounds_played")
    private Integer duoRoundsPlayed;      // 双排场数
    @TableField(value = "duo_team_kills")
    private Integer duoTeamKills;         // 双排击杀队友数
    @TableField(value = "duo_kd")
    private BigDecimal duoKd;               // 双排kd
}
