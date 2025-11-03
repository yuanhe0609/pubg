package com.dataAnalysis.pubgUserService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PubgUserMapper extends BaseMapper<PubgUserEntity> {
}
