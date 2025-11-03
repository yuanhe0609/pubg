package com.dataAnalysis.pubgSeasonService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dataAnalysis.pubgSeasonService.entity.PubgMatchDataEntity;

import java.util.List;

public interface PubgMatchDataMapper extends BaseMapper<PubgMatchDataEntity> {

    void updateBatch(List<PubgMatchDataEntity> list);

    void insertBatch(List<PubgMatchDataEntity> list);
}
