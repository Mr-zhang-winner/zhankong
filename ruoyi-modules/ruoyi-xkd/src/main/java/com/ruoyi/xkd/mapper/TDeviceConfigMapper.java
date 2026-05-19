package com.ruoyi.xkd.mapper;

import java.util.List;
import com.ruoyi.xkd.domain.TDeviceConfig;

/**
 * 设备配置Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface TDeviceConfigMapper 
{
    /**
     * 查询设备配置
     * 
     * @param deviceId 设备配置主键
     * @return 设备配置
     */
    public TDeviceConfig selectTDeviceConfigByDeviceId(Long deviceId);

    /**
     * 查询设备配置列表
     * 
     * @param tDeviceConfig 设备配置
     * @return 设备配置集合
     */
    public List<TDeviceConfig> selectTDeviceConfigList(TDeviceConfig tDeviceConfig);

    /**
     * 新增设备配置
     * 
     * @param tDeviceConfig 设备配置
     * @return 结果
     */
    public int insertTDeviceConfig(TDeviceConfig tDeviceConfig);

    /**
     * 修改设备配置
     * 
     * @param tDeviceConfig 设备配置
     * @return 结果
     */
    public int updateTDeviceConfig(TDeviceConfig tDeviceConfig);

    /**
     * 删除设备配置
     * 
     * @param deviceId 设备配置主键
     * @return 结果
     */
    public int deleteTDeviceConfigByDeviceId(Long deviceId);

    /**
     * 批量删除设备配置
     * 
     * @param deviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDeviceConfigByDeviceIds(Long[] deviceIds);
}
