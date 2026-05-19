package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TDeviceStatusMapper;
import com.ruoyi.xkd.domain.TDeviceStatus;
import com.ruoyi.xkd.service.ITDeviceStatusService;

/**
 * 设备状态Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class TDeviceStatusServiceImpl implements ITDeviceStatusService 
{
    @Autowired
    private TDeviceStatusMapper tDeviceStatusMapper;

    /**
     * 查询设备状态
     * 
     * @param statusId 设备状态主键
     * @return 设备状态
     */
    @Override
    public TDeviceStatus selectTDeviceStatusByStatusId(Long statusId)
    {
        return tDeviceStatusMapper.selectTDeviceStatusByStatusId(statusId);
    }

    /**
     * 查询设备状态列表
     * 
     * @param tDeviceStatus 设备状态
     * @return 设备状态
     */
    @Override
    public List<TDeviceStatus> selectTDeviceStatusList(TDeviceStatus tDeviceStatus)
    {
        return tDeviceStatusMapper.selectTDeviceStatusList(tDeviceStatus);
    }

    /**
     * 新增设备状态
     * 
     * @param tDeviceStatus 设备状态
     * @return 结果
     */
    @Override
    public int insertTDeviceStatus(TDeviceStatus tDeviceStatus)
    {
        tDeviceStatus.setCreateTime(DateUtils.getNowDate());
        return tDeviceStatusMapper.insertTDeviceStatus(tDeviceStatus);
    }

    /**
     * 修改设备状态
     * 
     * @param tDeviceStatus 设备状态
     * @return 结果
     */
    @Override
    public int updateTDeviceStatus(TDeviceStatus tDeviceStatus)
    {
        return tDeviceStatusMapper.updateTDeviceStatus(tDeviceStatus);
    }

    /**
     * 批量删除设备状态
     * 
     * @param statusIds 需要删除的设备状态主键
     * @return 结果
     */
    @Override
    public int deleteTDeviceStatusByStatusIds(Long[] statusIds)
    {
        return tDeviceStatusMapper.deleteTDeviceStatusByStatusIds(statusIds);
    }

    /**
     * 删除设备状态信息
     * 
     * @param statusId 设备状态主键
     * @return 结果
     */
    @Override
    public int deleteTDeviceStatusByStatusId(Long statusId)
    {
        return tDeviceStatusMapper.deleteTDeviceStatusByStatusId(statusId);
    }
}
