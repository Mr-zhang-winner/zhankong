package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TSatelliteConfigMapper;
import com.ruoyi.xkd.domain.TSatelliteConfig;
import com.ruoyi.xkd.service.ITSatelliteConfigService;

/**
 * 卫星配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class TSatelliteConfigServiceImpl implements ITSatelliteConfigService 
{
    @Autowired
    private TSatelliteConfigMapper tSatelliteConfigMapper;

    /**
     * 查询卫星配置
     * 
     * @param satelliteId 卫星配置主键
     * @return 卫星配置
     */
    @Override
    public TSatelliteConfig selectTSatelliteConfigBySatelliteId(Long satelliteId)
    {
        return tSatelliteConfigMapper.selectTSatelliteConfigBySatelliteId(satelliteId);
    }

    /**
     * 查询卫星配置列表
     * 
     * @param tSatelliteConfig 卫星配置
     * @return 卫星配置
     */
    @Override
    public List<TSatelliteConfig> selectTSatelliteConfigList(TSatelliteConfig tSatelliteConfig)
    {
        return tSatelliteConfigMapper.selectTSatelliteConfigList(tSatelliteConfig);
    }

    /**
     * 新增卫星配置
     * 
     * @param tSatelliteConfig 卫星配置
     * @return 结果
     */
    @Override
    public int insertTSatelliteConfig(TSatelliteConfig tSatelliteConfig)
    {
        tSatelliteConfig.setCreateTime(DateUtils.getNowDate());
        return tSatelliteConfigMapper.insertTSatelliteConfig(tSatelliteConfig);
    }

    /**
     * 修改卫星配置
     * 
     * @param tSatelliteConfig 卫星配置
     * @return 结果
     */
    @Override
    public int updateTSatelliteConfig(TSatelliteConfig tSatelliteConfig)
    {
        tSatelliteConfig.setUpdateTime(DateUtils.getNowDate());
        return tSatelliteConfigMapper.updateTSatelliteConfig(tSatelliteConfig);
    }

    /**
     * 批量删除卫星配置
     * 
     * @param satelliteIds 需要删除的卫星配置主键
     * @return 结果
     */
    @Override
    public int deleteTSatelliteConfigBySatelliteIds(Long[] satelliteIds)
    {
        return tSatelliteConfigMapper.deleteTSatelliteConfigBySatelliteIds(satelliteIds);
    }

    /**
     * 删除卫星配置信息
     * 
     * @param satelliteId 卫星配置主键
     * @return 结果
     */
    @Override
    public int deleteTSatelliteConfigBySatelliteId(Long satelliteId)
    {
        return tSatelliteConfigMapper.deleteTSatelliteConfigBySatelliteId(satelliteId);
    }
}
