package com.ruoyi.xkd.mapper;

import java.util.List;
import com.ruoyi.xkd.domain.TAlarmLog;

/**
 * 告警日志Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface TAlarmLogMapper 
{
    /**
     * 查询告警日志
     * 
     * @param alarmId 告警日志主键
     * @return 告警日志
     */
    public TAlarmLog selectTAlarmLogByAlarmId(Long alarmId);

    /**
     * 查询告警日志列表
     * 
     * @param tAlarmLog 告警日志
     * @return 告警日志集合
     */
    public List<TAlarmLog> selectTAlarmLogList(TAlarmLog tAlarmLog);

    /**
     * 新增告警日志
     * 
     * @param tAlarmLog 告警日志
     * @return 结果
     */
    public int insertTAlarmLog(TAlarmLog tAlarmLog);

    /**
     * 修改告警日志
     * 
     * @param tAlarmLog 告警日志
     * @return 结果
     */
    public int updateTAlarmLog(TAlarmLog tAlarmLog);

    /**
     * 删除告警日志
     * 
     * @param alarmId 告警日志主键
     * @return 结果
     */
    public int deleteTAlarmLogByAlarmId(Long alarmId);

    /**
     * 批量删除告警日志
     * 
     * @param alarmIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTAlarmLogByAlarmIds(Long[] alarmIds);
}
