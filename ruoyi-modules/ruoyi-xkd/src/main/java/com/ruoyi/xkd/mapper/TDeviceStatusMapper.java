package com.ruoyi.xkd.mapper;

import java.util.List;
import com.ruoyi.xkd.domain.TDeviceStatus;

/**
 * 设备状态Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface TDeviceStatusMapper 
{
    /**
     * 查询设备状态
     * 
     * @param statusId 设备状态主键
     * @return 设备状态
     */
    public TDeviceStatus selectTDeviceStatusByStatusId(Long statusId);

    /**
     * 查询设备状态列表
     * 
     * @param tDeviceStatus 设备状态
     * @return 设备状态集合
     */
    public List<TDeviceStatus> selectTDeviceStatusList(TDeviceStatus tDeviceStatus);

    /**
     * 新增设备状态
     * 
     * @param tDeviceStatus 设备状态
     * @return 结果
     */
    public int insertTDeviceStatus(TDeviceStatus tDeviceStatus);

    /**
     * 修改设备状态
     * 
     * @param tDeviceStatus 设备状态
     * @return 结果
     */
    public int updateTDeviceStatus(TDeviceStatus tDeviceStatus);

    /**
     * 删除设备状态
     * 
     * @param statusId 设备状态主键
     * @return 结果
     */
    public int deleteTDeviceStatusByStatusId(Long statusId);

    /**
     * 批量删除设备状态
     * 
     * @param statusIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDeviceStatusByStatusIds(Long[] statusIds);
}
