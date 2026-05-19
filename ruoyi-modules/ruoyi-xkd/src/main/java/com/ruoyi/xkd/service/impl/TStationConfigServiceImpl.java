package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TStationConfigMapper;
import com.ruoyi.xkd.domain.TStationConfig;
import com.ruoyi.xkd.service.ITStationConfigService;

/**
 * 站址配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class TStationConfigServiceImpl implements ITStationConfigService 
{
    @Autowired
    private TStationConfigMapper tStationConfigMapper;

    /**
     * 查询站址配置
     * 
     * @param stationId 站址配置主键
     * @return 站址配置
     */
    @Override
    public TStationConfig selectTStationConfigByStationId(Long stationId)
    {
        return tStationConfigMapper.selectTStationConfigByStationId(stationId);
    }

    /**
     * 查询站址配置列表
     * 
     * @param tStationConfig 站址配置
     * @return 站址配置
     */
    @Override
    public List<TStationConfig> selectTStationConfigList(TStationConfig tStationConfig)
    {
        return tStationConfigMapper.selectTStationConfigList(tStationConfig);
    }

    /**
     * 新增站址配置
     * 
     * @param tStationConfig 站址配置
     * @return 结果
     */
    @Override
    public int insertTStationConfig(TStationConfig tStationConfig)
    {
        tStationConfig.setCreateTime(DateUtils.getNowDate());
        return tStationConfigMapper.insertTStationConfig(tStationConfig);
    }

    /**
     * 修改站址配置
     * 
     * @param tStationConfig 站址配置
     * @return 结果
     */
    @Override
    public int updateTStationConfig(TStationConfig tStationConfig)
    {
        tStationConfig.setUpdateTime(DateUtils.getNowDate());
        return tStationConfigMapper.updateTStationConfig(tStationConfig);
    }

    /**
     * 批量删除站址配置
     * 
     * @param stationIds 需要删除的站址配置主键
     * @return 结果
     */
    @Override
    public int deleteTStationConfigByStationIds(Long[] stationIds)
    {
        return tStationConfigMapper.deleteTStationConfigByStationIds(stationIds);
    }

    /**
     * 删除站址配置信息
     * 
     * @param stationId 站址配置主键
     * @return 结果
     */
    @Override
    public int deleteTStationConfigByStationId(Long stationId)
    {
        return tStationConfigMapper.deleteTStationConfigByStationId(stationId);
    }
}
