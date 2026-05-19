package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TDeviceControlLogMapper;
import com.ruoyi.xkd.domain.TDeviceControlLog;
import com.ruoyi.xkd.service.ITDeviceControlLogService;

/**
 * 设备控制日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
@Service
public class TDeviceControlLogServiceImpl implements ITDeviceControlLogService 
{
    @Autowired
    private TDeviceControlLogMapper tDeviceControlLogMapper;

    /**
     * 查询设备控制日志
     * 
     * @param controlId 设备控制日志主键
     * @return 设备控制日志
     */
    @Override
    public TDeviceControlLog selectTDeviceControlLogByControlId(Long controlId)
    {
        return tDeviceControlLogMapper.selectTDeviceControlLogByControlId(controlId);
    }

    /**
     * 查询设备控制日志列表
     * 
     * @param tDeviceControlLog 设备控制日志
     * @return 设备控制日志
     */
    @Override
    public List<TDeviceControlLog> selectTDeviceControlLogList(TDeviceControlLog tDeviceControlLog)
    {
        return tDeviceControlLogMapper.selectTDeviceControlLogList(tDeviceControlLog);
    }

    /**
     * 新增设备控制日志
     * 
     * @param tDeviceControlLog 设备控制日志
     * @return 结果
     */
    @Override
    public int insertTDeviceControlLog(TDeviceControlLog tDeviceControlLog)
    {
        tDeviceControlLog.setCreateTime(DateUtils.getNowDate());
        return tDeviceControlLogMapper.insertTDeviceControlLog(tDeviceControlLog);
    }

    /**
     * 修改设备控制日志
     * 
     * @param tDeviceControlLog 设备控制日志
     * @return 结果
     */
    @Override
    public int updateTDeviceControlLog(TDeviceControlLog tDeviceControlLog)
    {
        return tDeviceControlLogMapper.updateTDeviceControlLog(tDeviceControlLog);
    }

    /**
     * 批量删除设备控制日志
     * 
     * @param controlIds 需要删除的设备控制日志主键
     * @return 结果
     */
    @Override
    public int deleteTDeviceControlLogByControlIds(Long[] controlIds)
    {
        return tDeviceControlLogMapper.deleteTDeviceControlLogByControlIds(controlIds);
    }

    /**
     * 删除设备控制日志信息
     * 
     * @param controlId 设备控制日志主键
     * @return 结果
     */
    @Override
    public int deleteTDeviceControlLogByControlId(Long controlId)
    {
        return tDeviceControlLogMapper.deleteTDeviceControlLogByControlId(controlId);
    }
}
