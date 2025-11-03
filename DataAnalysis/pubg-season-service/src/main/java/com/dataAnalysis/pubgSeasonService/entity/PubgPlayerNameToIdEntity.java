package com.dataAnalysis.pubgSeasonService.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pubg_player_name_to_id")
public class PubgPlayerNameToIdEntity {
    @TableField(value = "player_name")
    private String playerName;              // 用户名称
    @TableField(value = "player_id")
    private String playerId;                // 用户ID
}
