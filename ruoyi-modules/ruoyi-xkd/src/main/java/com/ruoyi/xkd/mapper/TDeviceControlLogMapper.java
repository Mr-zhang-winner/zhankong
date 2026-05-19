package com.ruoyi.xkd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.xkd.domain.TDeviceControlLog;

/**
 * 设备控制日志Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
public interface TDeviceControlLogMapper 
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
     * 删除设备控制日志
     * 
     * @param controlId 设备控制日志主键
     * @return 结果
     */
    public int deleteTDeviceControlLogByControlId(Long controlId);

    /**
     * 批量删除设备控制日志
     * 
     * @param controlIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDeviceControlLogByControlIds(Long[] controlIds);

    public TDeviceControlLog selectLatestControlLogByDeviceAndKey(
            @Param("deviceCode") String deviceCode,
            @Param("commandKey") String commandKey
    );
}
