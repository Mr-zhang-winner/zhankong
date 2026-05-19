package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TAlarmLogMapper;
import com.ruoyi.xkd.domain.TAlarmLog;
import com.ruoyi.xkd.service.ITAlarmLogService;

/**
 * 告警日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class TAlarmLogServiceImpl implements ITAlarmLogService 
{
    @Autowired
    private TAlarmLogMapper tAlarmLogMapper;

    /**
     * 查询告警日志
     * 
     * @param alarmId 告警日志主键
     * @return 告警日志
     */
    @Override
    public TAlarmLog selectTAlarmLogByAlarmId(Long alarmId)
    {
        return tAlarmLogMapper.selectTAlarmLogByAlarmId(alarmId);
    }

    /**
     * 查询告警日志列表
     * 
     * @param tAlarmLog 告警日志
     * @return 告警日志
     */
    @Override
    public List<TAlarmLog> selectTAlarmLogList(TAlarmLog tAlarmLog)
    {
        return tAlarmLogMapper.selectTAlarmLogList(tAlarmLog);
    }

    /**
     * 新增告警日志
     * 
     * @param tAlarmLog 告警日志
     * @return 结果
     */
    @Override
    public int insertTAlarmLog(TAlarmLog tAlarmLog)
    {
        tAlarmLog.setCreateTime(DateUtils.getNowDate());
        return tAlarmLogMapper.insertTAlarmLog(tAlarmLog);
    }

    /**
     * 修改告警日志
     * 
     * @param tAlarmLog 告警日志
     * @return 结果
     */
    @Override
    public int updateTAlarmLog(TAlarmLog tAlarmLog)
    {
        return tAlarmLogMapper.updateTAlarmLog(tAlarmLog);
    }

    /**
     * 批量删除告警日志
     * 
     * @param alarmIds 需要删除的告警日志主键
     * @return 结果
     */
    @Override
    public int deleteTAlarmLogByAlarmIds(Long[] alarmIds)
    {
        return tAlarmLogMapper.deleteTAlarmLogByAlarmIds(alarmIds);
    }

    /**
     * 删除告警日志信息
     * 
     * @param alarmId 告警日志主键
     * @return 结果
     */
    @Override
    public int deleteTAlarmLogByAlarmId(Long alarmId)
    {
        return tAlarmLogMapper.deleteTAlarmLogByAlarmId(alarmId);
    }
}
