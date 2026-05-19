package com.ruoyi.xkd.mapper;

import java.util.List;
import com.ruoyi.xkd.domain.TSatelliteConfig;

/**
 * 卫星配置Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface TSatelliteConfigMapper 
{
    /**
     * 查询卫星配置
     * 
     * @param satelliteId 卫星配置主键
     * @return 卫星配置
     */
    public TSatelliteConfig selectTSatelliteConfigBySatelliteId(Long satelliteId);

    /**
     * 查询卫星配置列表
     * 
     * @param tSatelliteConfig 卫星配置
     * @return 卫星配置集合
     */
    public List<TSatelliteConfig> selectTSatelliteConfigList(TSatelliteConfig tSatelliteConfig);

    /**
     * 新增卫星配置
     * 
     * @param tSatelliteConfig 卫星配置
     * @return 结果
     */
    public int insertTSatelliteConfig(TSatelliteConfig tSatelliteConfig);

    /**
     * 修改卫星配置
     * 
     * @param tSatelliteConfig 卫星配置
     * @return 结果
     */
    public int updateTSatelliteConfig(TSatelliteConfig tSatelliteConfig);

    /**
     * 删除卫星配置
     * 
     * @param satelliteId 卫星配置主键
     * @return 结果
     */
    public int deleteTSatelliteConfigBySatelliteId(Long satelliteId);

    /**
     * 批量删除卫星配置
     * 
     * @param satelliteIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSatelliteConfigBySatelliteIds(Long[] satelliteIds);
}
