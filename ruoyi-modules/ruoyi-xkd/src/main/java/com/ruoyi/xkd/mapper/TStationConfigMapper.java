package com.ruoyi.xkd.mapper;

import java.util.List;
import com.ruoyi.xkd.domain.TStationConfig;

/**
 * 站址配置Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface TStationConfigMapper 
{
    /**
     * 查询站址配置
     * 
     * @param stationId 站址配置主键
     * @return 站址配置
     */
    public TStationConfig selectTStationConfigByStationId(Long stationId);

    /**
     * 查询站址配置列表
     * 
     * @param tStationConfig 站址配置
     * @return 站址配置集合
     */
    public List<TStationConfig> selectTStationConfigList(TStationConfig tStationConfig);

    /**
     * 新增站址配置
     * 
     * @param tStationConfig 站址配置
     * @return 结果
     */
    public int insertTStationConfig(TStationConfig tStationConfig);

    /**
     * 修改站址配置
     * 
     * @param tStationConfig 站址配置
     * @return 结果
     */
    public int updateTStationConfig(TStationConfig tStationConfig);

    /**
     * 删除站址配置
     * 
     * @param stationId 站址配置主键
     * @return 结果
     */
    public int deleteTStationConfigByStationId(Long stationId);

    /**
     * 批量删除站址配置
     * 
     * @param stationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTStationConfigByStationIds(Long[] stationIds);
}
