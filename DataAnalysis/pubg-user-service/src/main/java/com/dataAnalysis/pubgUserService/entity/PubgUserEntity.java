package com.dataAnalysis.pubgUserService.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PubgUserEntity {
    @TableField(value = "user_id")
    @TableId
    private String userId;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "user_pubg_name")
    private String userPubgName;
    @TableField(value = "user_pubg_id")
    private String userPubgId;
    @TableField(value = "user_pubg_level")
    private Integer userPubgLevel;
    @TableField(value = "user_password")
    private String userPassword;
}
