package com.ruoyi.xkd.protocol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.TDeviceControlLog;
import com.ruoyi.xkd.mapper.TDeviceControlLogMapper;
import com.ruoyi.xkd.protocol.codec.AntennaAckCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaAckItem;
import com.ruoyi.xkd.protocol.model.AntennaFrame;

/**
 * CMD_SET_ACK 0x81 设置响应处理服务
 * 负责解析天线返回的设置响应数据（ACK）
 * 并更新设备控制日志表中的执行结果
 */
@Service
public class AntennaAckService
{
    /** ACK数据解码器 */
    private final AntennaAckCodec ackCodec;
    
    /** 设备控制日志数据访问层 */
    private final TDeviceControlLogMapper tDeviceControlLogMapper;

    /**
     * 构造函数，注入依赖
     * @param ackCodec ACK数据解码器
     * @param tDeviceControlLogMapper 设备控制日志Mapper
     */
    public AntennaAckService(AntennaAckCodec ackCodec,
                             TDeviceControlLogMapper tDeviceControlLogMapper)
    {
        this.ackCodec = ackCodec;
        this.tDeviceControlLogMapper = tDeviceControlLogMapper;
    }

    /**
     * 处理参数设置响应（CMD_SET_ACK 0x81）
     * 解析天线返回的ACK数据，更新对应的设备控制日志记录
     * 
     * @param frame 协议帧，包含响应数据和设备信息
     */
    public void handleSetAck(AntennaFrame frame)
    {
        // 解码ACK payload，获取每个参数的设置结果
        List<AntennaAckItem> items = ackCodec.decodeSetAck(frame.getPayload());

        // 遍历每个ACK项，更新对应的控制日志
        for (AntennaAckItem item : items)
        {
            // 将状态码转换为可读的状态名称
            item.setStatusName(ackStatusName(item.getStatus()));

            // 获取参数KEY（如 0x02、0x04）
            String commandKey = String.format("0x%02X", item.getKey());

            // 根据设备编号和命令KEY查找最近的待更新控制日志
            TDeviceControlLog log = tDeviceControlLogMapper.selectLatestControlLogByDeviceAndKey(
                    frame.getDeviceCode(),
                    commandKey
            );

            // 如果未找到对应的控制日志，跳过该项
            if (log == null)
            {
                continue;
            }

            // 更新ACK相关信息
            log.setAckCmd(String.format("0x%02X", frame.getCmd()));  // ACK命令字
            log.setAckStatus(item.getStatusName());                  // ACK状态名称

            // 构建ACK内容JSON
            Map<String, Object> ackContent = new HashMap<>();
            ackContent.put("key", commandKey);                       // 参数KEY
            ackContent.put("status", String.format("0x%02X", item.getStatus()));  // 状态码
            ackContent.put("statusName", item.getStatusName());      // 状态名称

            log.setAckContent(JSON.toJSONString(ackContent));  // 保存ACK内容为JSON字符串
            log.setAckTime(DateUtils.getNowDate());            // 记录ACK时间

            // 根据ACK状态更新发送状态
            if (item.getStatus() == AntennaProtocolConstants.PARAM_SET_OK
                    || item.getStatus() == AntennaProtocolConstants.PARAM_SET_RECEIVED)
            {
                // 设置成功或正常接收
                log.setSendStatus("SUCCESS");
            }
            else
            {
                // 设置失败，记录错误信息
                log.setSendStatus("FAILED");
                log.setErrorMsg(item.getStatusName());
            }

            // 更新数据库记录
            tDeviceControlLogMapper.updateTDeviceControlLog(log);
        }
    }

    /**
     * 将ACK状态码转换为中文描述
     * 根据协议定义的ACK状态码返回对应的状态名称
     * 
     * @param status ACK状态码
     * @return 状态名称（中文）
     */
    public String ackStatusName(int status)
    {
        switch (status)
        {
            case AntennaProtocolConstants.PARAM_SET_OK:
                return "成功";                                    // 0x20
            case AntennaProtocolConstants.PARAM_SET_FAILED:
                return "失败";                                    // 0x21
            case AntennaProtocolConstants.PARAM_SET_TIMEOUT:
                return "超时";                                    // 0x22
            case AntennaProtocolConstants.PARAM_SET_UNKNOWN:
                return "未知";                                    // 0x23
            case AntennaProtocolConstants.PARAM_SET_RECEIVED:
                return "正常接收并下发";                          // 0x24
            case AntennaProtocolConstants.PARAM_SET_INVALID:
                return "被控对象不存在";                          // 0x25
            case AntennaProtocolConstants.PARAM_SET_ERROR:
                return "参数错误";                                // 0x26
            case AntennaProtocolConstants.PARAM_SET_NOT_READY:
                return "条件不具备";                              // 0x27
            case AntennaProtocolConstants.PARAM_SET_REFUSED:
                return "设备拒绝执行";                            // 0x28
            case AntennaProtocolConstants.PARAM_FRAME_ERROR:
                return "帧格式错误";                              // 0x29
            default:
                return "自定义或未知状态：0x" + Integer.toHexString(status).toUpperCase();
        }
    }
}