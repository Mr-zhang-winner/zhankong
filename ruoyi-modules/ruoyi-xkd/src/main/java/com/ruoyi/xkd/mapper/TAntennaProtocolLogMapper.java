package com.ruoyi.xkd.mapper;

import java.util.List;
import com.ruoyi.xkd.domain.TAntennaProtocolLog;

/**
 * 天线协议收发日志Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
public interface TAntennaProtocolLogMapper 
{
    /**
     * 查询天线协议收发日志
     * 
     * @param logId 天线协议收发日志主键
     * @return 天线协议收发日志
     */
    public TAntennaProtocolLog selectTAntennaProtocolLogByLogId(Long logId);

    /**
     * 查询天线协议收发日志列表
     * 
     * @param tAntennaProtocolLog 天线协议收发日志
     * @return 天线协议收发日志集合
     */
    public List<TAntennaProtocolLog> selectTAntennaProtocolLogList(TAntennaProtocolLog tAntennaProtocolLog);

    /**
     * 新增天线协议收发日志
     * 
     * @param tAntennaProtocolLog 天线协议收发日志
     * @return 结果
     */
    public int insertTAntennaProtocolLog(TAntennaProtocolLog tAntennaProtocolLog);

    /**
     * 修改天线协议收发日志
     * 
     * @param tAntennaProtocolLog 天线协议收发日志
     * @return 结果
     */
    public int updateTAntennaProtocolLog(TAntennaProtocolLog tAntennaProtocolLog);

    /**
     * 删除天线协议收发日志
     * 
     * @param logId 天线协议收发日志主键
     * @return 结果
     */
    public int deleteTAntennaProtocolLogByLogId(Long logId);

    /**
     * 批量删除天线协议收发日志
     * 
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTAntennaProtocolLogByLogIds(Long[] logIds);
}
