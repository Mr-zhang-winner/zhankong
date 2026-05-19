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

@Service
public class AntennaAckService
{
    private final AntennaAckCodec ackCodec;
    private final TDeviceControlLogMapper tDeviceControlLogMapper;

    public AntennaAckService(AntennaAckCodec ackCodec,
                             TDeviceControlLogMapper tDeviceControlLogMapper)
    {
        this.ackCodec = ackCodec;
        this.tDeviceControlLogMapper = tDeviceControlLogMapper;
    }

    public void handleSetAck(AntennaFrame frame)
    {
        List<AntennaAckItem> items = ackCodec.decodeSetAck(frame.getPayload());

        for (AntennaAckItem item : items)
        {
            item.setStatusName(ackStatusName(item.getStatus()));

            String commandKey = String.format("0x%02X", item.getKey());

            TDeviceControlLog log = tDeviceControlLogMapper.selectLatestControlLogByDeviceAndKey(
                    frame.getDeviceCode(),
                    commandKey
            );

            if (log == null)
            {
                continue;
            }

            log.setAckCmd(String.format("0x%02X", frame.getCmd()));
            log.setAckStatus(item.getStatusName());

            Map<String, Object> ackContent = new HashMap<>();
            ackContent.put("key", commandKey);
            ackContent.put("status", String.format("0x%02X", item.getStatus()));
            ackContent.put("statusName", item.getStatusName());

            log.setAckContent(JSON.toJSONString(ackContent));
            log.setAckTime(DateUtils.getNowDate());

            if (item.getStatus() == AntennaProtocolConstants.PARAM_SET_OK
                    || item.getStatus() == AntennaProtocolConstants.PARAM_SET_RECEIVED)
            {
                log.setSendStatus("SUCCESS");
            }
            else
            {
                log.setSendStatus("FAILED");
                log.setErrorMsg(item.getStatusName());
            }

            tDeviceControlLogMapper.updateTDeviceControlLog(log);
        }
    }

    public String ackStatusName(int status)
    {
        switch (status)
        {
            case AntennaProtocolConstants.PARAM_SET_OK:
                return "成功";
            case AntennaProtocolConstants.PARAM_SET_FAILED:
                return "失败";
            case AntennaProtocolConstants.PARAM_SET_TIMEOUT:
                return "超时";
            case AntennaProtocolConstants.PARAM_SET_UNKNOWN:
                return "未知";
            case AntennaProtocolConstants.PARAM_SET_RECEIVED:
                return "正常接收并下发";
            case AntennaProtocolConstants.PARAM_SET_INVALID:
                return "被控对象不存在";
            case AntennaProtocolConstants.PARAM_SET_ERROR:
                return "参数错误";
            case AntennaProtocolConstants.PARAM_SET_NOT_READY:
                return "条件不具备";
            case AntennaProtocolConstants.PARAM_SET_REFUSED:
                return "设备拒绝执行";
            case AntennaProtocolConstants.PARAM_FRAME_ERROR:
                return "帧格式错误";
            default:
                return "自定义或未知状态：0x" + Integer.toHexString(status).toUpperCase();
        }
    }
}