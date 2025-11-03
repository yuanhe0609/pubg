package com.dataAnalysis.pubgWeaponService.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("pubg_weapon_data")
public class PubgWeaponEntity {
    @TableField(value = "weapon_id")
    private String weaponId;
    @TableField(value = "weapon_name")
    private String weaponName;
    @TableField(value = "weapon_type")
    private String weaponType;
    @TableField(value = "weapon_bullet_type")
    private String weaponBulletType;
    @TableField(value = "weapon_fire_mode")
    private String weaponFireMode;
    @TableField(value = "weapon_damage")
    private BigDecimal weaponDamage;
    @TableField(value = "weapon_reload_time")
    private BigDecimal weaponReloadTime;
    @TableField(value = "weapon_bullet_speed")
    private BigDecimal weaponBulletSpeed;
    @TableField(value = "weapon_fire_rate")
    private BigDecimal weaponFireRate;


}
