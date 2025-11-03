package com.dataAnalysis.pubgSeasonService.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("pubg_match_data")
public class PubgMatchDataEntity {
    @TableField(value = "match_id")
    @TableId
    private String matchId;
    @TableField(value = "player_id")
    private String playerId;
    @TableField(value = "player_name")
    private String playerName;
    @TableField(value = " knocked_number")
    private Integer knockedNumber;
    @TableField(value = "assist")
    private Integer assist;
    @TableField(value = "kills")
    private Integer kills;
    @TableField(value = "boosts")
    private Integer boosts;
    @TableField(value = "damage_dealt")
    private BigDecimal damageDealt;
    @TableField(value = "death_type")
    private String deathType;
    @TableField(value = "headshot_kills")
    private Integer headshotKills;
    @TableField(value = "heals")
    private Integer heals;
    @TableField(value = "ride_distance")
    private BigDecimal rideDistance;
    @TableField(value = "swim_distance")
    private BigDecimal swimDistance;
    @TableField(value = "walk_distance")
    private BigDecimal walkDistance;
    @TableField(value = "road_kills")
    private Integer roadKills;
    @TableField(value = "team_kills")
    private Integer teamKills;
    @TableField(value = "time_survived")
    private Integer timeSurvived;
    @TableField(value = "win_place")
    private Integer winPlace;
    @TableField(value = "map_name")
    private String mapName;
    @TableField(value = "match_type")
    private String matchType;
    @TableField(value = "game_mode")
    private String gameMode;
    @TableField(value = "battle_score")
    private BigDecimal battleScore;
    @TableField(value = "collaboration_score")
    private BigDecimal collaborationScore;
    @TableField(value = "survival_score")
    private BigDecimal survivalScore;
}
