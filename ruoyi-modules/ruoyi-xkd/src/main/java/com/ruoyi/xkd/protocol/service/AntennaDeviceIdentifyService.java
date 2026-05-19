package com.ruoyi.xkd.protocol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.xkd.domain.TDeviceConfig;
import com.ruoyi.xkd.mapper.TDeviceConfigMapper;

@Service
public class AntennaDeviceIdentifyService
{
    private final TDeviceConfigMapper tDeviceConfigMapper;

    public AntennaDeviceIdentifyService(TDeviceConfigMapper tDeviceConfigMapper)
    {
        this.tDeviceConfigMapper = tDeviceConfigMapper;
    }

    public TDeviceConfig identify(String remoteIp, Integer remotePort)
    {
        if (remoteIp == null || remoteIp.isEmpty())
        {
            throw new ServiceException("无法识别设备：远端IP为空");
        }

        TDeviceConfig query = new TDeviceConfig();
        query.setIpAddress(remoteIp);
        query.setUdpPort(remotePort != null ? remotePort.longValue() : null);

        List<TDeviceConfig> list = tDeviceConfigMapper.selectTDeviceConfigList(query);

        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }

        TDeviceConfig ipQuery = new TDeviceConfig();
        ipQuery.setIpAddress(remoteIp);

        List<TDeviceConfig> ipList = tDeviceConfigMapper.selectTDeviceConfigList(ipQuery);

        if (ipList != null && ipList.size() == 1)
        {
            return ipList.get(0);
        }

        throw new ServiceException("无法识别设备，remoteIp=" + remoteIp + ", remotePort=" + remotePort);
    }
}