package com.ruoyi.xkd.service;

import java.util.List;
import com.ruoyi.xkd.domain.TDeviceControlLog;

/**
 * 设备控制日志Service接口
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
public interface ITDeviceControlLogService 
{
    /**
     * 查询设备控制日志
     * 
     * @param controlId 设备控制日志主键
     * @return 设备控制日志
     */
    public TDeviceControlLog selectTDeviceControlLogByControlId(Long controlId);

    /**
     * 查询设备控制日志列表
     * 
     * @param tDeviceControlLog 设备控制日志
     * @return 设备控制日志集合
     */
    public List<TDeviceControlLog> selectTDeviceControlLogList(TDeviceControlLog tDeviceControlLog);

    /**
     * 新增设备控制日志
     * 
     * @param tDeviceControlLog 设备控制日志
     * @return 结果
     */
    public int insertTDeviceControlLog(TDeviceControlLog tDeviceControlLog);

    /**
     * 修改设备控制日志
     * 
     * @param tDeviceControlLog 设备控制日志
     * @return 结果
     */
    public int updateTDeviceControlLog(TDeviceControlLog tDeviceControlLog);

    /**
     * 批量删除设备控制日志
     * 
     * @param controlIds 需要删除的设备控制日志主键集合
     * @return 结果
     */
    public int deleteTDeviceControlLogByControlIds(Long[] controlIds);

    /**
     * 删除设备控制日志信息
     * 
     * @param controlId 设备控制日志主键
     * @return 结果
     */
    public int deleteTDeviceControlLogByControlId(Long controlId);
}
