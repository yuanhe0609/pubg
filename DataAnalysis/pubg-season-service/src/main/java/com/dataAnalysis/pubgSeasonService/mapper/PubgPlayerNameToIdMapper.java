package com.dataAnalysis.pubgSeasonService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dataAnalysis.pubgSeasonService.entity.PubgPlayerNameToIdEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PubgPlayerNameToIdMapper extends BaseMapper<PubgPlayerNameToIdEntity> {
}
