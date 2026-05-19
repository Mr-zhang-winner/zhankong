package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TDeviceConfigMapper;
import com.ruoyi.xkd.domain.TDeviceConfig;
import com.ruoyi.xkd.service.ITDeviceConfigService;

/**
 * 设备配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class TDeviceConfigServiceImpl implements ITDeviceConfigService 
{
    @Autowired
    private TDeviceConfigMapper tDeviceConfigMapper;

    /**
     * 查询设备配置
     * 
     * @param deviceId 设备配置主键
     * @return 设备配置
     */
    @Override
    public TDeviceConfig selectTDeviceConfigByDeviceId(Long deviceId)
    {
        return tDeviceConfigMapper.selectTDeviceConfigByDeviceId(deviceId);
    }

    /**
     * 查询设备配置列表
     * 
     * @param tDeviceConfig 设备配置
     * @return 设备配置
     */
    @Override
    public List<TDeviceConfig> selectTDeviceConfigList(TDeviceConfig tDeviceConfig)
    {
        return tDeviceConfigMapper.selectTDeviceConfigList(tDeviceConfig);
    }

    /**
     * 新增设备配置
     * 
     * @param tDeviceConfig 设备配置
     * @return 结果
     */
    @Override
    public int insertTDeviceConfig(TDeviceConfig tDeviceConfig)
    {
        tDeviceConfig.setCreateTime(DateUtils.getNowDate());
        return tDeviceConfigMapper.insertTDeviceConfig(tDeviceConfig);
    }

    /**
     * 修改设备配置
     * 
     * @param tDeviceConfig 设备配置
     * @return 结果
     */
    @Override
    public int updateTDeviceConfig(TDeviceConfig tDeviceConfig)
    {
        tDeviceConfig.setUpdateTime(DateUtils.getNowDate());
        return tDeviceConfigMapper.updateTDeviceConfig(tDeviceConfig);
    }

    /**
     * 批量删除设备配置
     * 
     * @param deviceIds 需要删除的设备配置主键
     * @return 结果
     */
    @Override
    public int deleteTDeviceConfigByDeviceIds(Long[] deviceIds)
    {
        return tDeviceConfigMapper.deleteTDeviceConfigByDeviceIds(deviceIds);
    }

    /**
     * 删除设备配置信息
     * 
     * @param deviceId 设备配置主键
     * @return 结果
     */
    @Override
    public int deleteTDeviceConfigByDeviceId(Long deviceId)
    {
        return tDeviceConfigMapper.deleteTDeviceConfigByDeviceId(deviceId);
    }
}
