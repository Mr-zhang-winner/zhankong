package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TAntennaProtocolLogMapper;
import com.ruoyi.xkd.domain.TAntennaProtocolLog;
import com.ruoyi.xkd.service.ITAntennaProtocolLogService;

/**
 * 天线协议收发日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
@Service
public class TAntennaProtocolLogServiceImpl implements ITAntennaProtocolLogService 
{
    @Autowired
    private TAntennaProtocolLogMapper tAntennaProtocolLogMapper;

    /**
     * 查询天线协议收发日志
     * 
     * @param logId 天线协议收发日志主键
     * @return 天线协议收发日志
     */
    @Override
    public TAntennaProtocolLog selectTAntennaProtocolLogByLogId(Long logId)
    {
        return tAntennaProtocolLogMapper.selectTAntennaProtocolLogByLogId(logId);
    }

    /**
     * 查询天线协议收发日志列表
     * 
     * @param tAntennaProtocolLog 天线协议收发日志
     * @return 天线协议收发日志集合
     */
    @Override
    public List<TAntennaProtocolLog> selectTAntennaProtocolLogList(TAntennaProtocolLog tAntennaProtocolLog)
    {
        return tAntennaProtocolLogMapper.selectTAntennaProtocolLogList(tAntennaProtocolLog);
    }

    /**
     * 新增天线协议收发日志
     * 
     * @param tAntennaProtocolLog 天线协议收发日志
     * @return 结果
     */
    @Override
    public int insertTAntennaProtocolLog(TAntennaProtocolLog tAntennaProtocolLog)
    {
        tAntennaProtocolLog.setCreateTime(DateUtils.getNowDate());
        return tAntennaProtocolLogMapper.insertTAntennaProtocolLog(tAntennaProtocolLog);
    }

    /**
     * 修改天线协议收发日志
     * 
     * @param tAntennaProtocolLog 天线协议收发日志
     * @return 结果
     */
    @Override
    public int updateTAntennaProtocolLog(TAntennaProtocolLog tAntennaProtocolLog)
    {
        return tAntennaProtocolLogMapper.updateTAntennaProtocolLog(tAntennaProtocolLog);
    }

    /**
     * 批量删除天线协议收发日志
     * 
     * @param logIds 需要删除的天线协议收发日志主键集合
     * @return 结果
     */
    @Override
    public int deleteTAntennaProtocolLogByLogIds(Long[] logIds)
    {
        return tAntennaProtocolLogMapper.deleteTAntennaProtocolLogByLogIds(logIds);
    }

    /**
     * 删除天线协议收发日志信息
     * 
     * @param logId 天线协议收发日志主键
     * @return 结果
     */
    @Override
    public int deleteTAntennaProtocolLogByLogId(Long logId)
    {
        return tAntennaProtocolLogMapper.deleteTAntennaProtocolLogByLogId(logId);
    }
}
