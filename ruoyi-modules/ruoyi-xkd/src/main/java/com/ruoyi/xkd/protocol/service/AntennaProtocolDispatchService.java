package com.ruoyi.xkd.protocol.service;

import org.springframework.stereotype.Service;

import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaFrame;

@Service
public class AntennaProtocolDispatchService
{
    private final AntennaAckService antennaAckService;
    private final AntennaQueryAckService antennaQueryAckService;
    private final AntennaReportService antennaReportService;
    private final AntennaFactoryAckService antennaFactoryAckService;
    private final AntennaBroadcastAckService antennaBroadcastAckService;
    private final AntennaEphemerisAckService antennaEphemerisAckService;

    public AntennaProtocolDispatchService(AntennaAckService antennaAckService,
                                          AntennaQueryAckService antennaQueryAckService,
                                          AntennaReportService antennaReportService,
                                          AntennaFactoryAckService antennaFactoryAckService,
                                          AntennaBroadcastAckService antennaBroadcastAckService,
                                          AntennaEphemerisAckService antennaEphemerisAckService)
    {
        this.antennaAckService = antennaAckService;
        this.antennaQueryAckService = antennaQueryAckService;
        this.antennaReportService = antennaReportService;
        this.antennaFactoryAckService = antennaFactoryAckService;
        this.antennaBroadcastAckService = antennaBroadcastAckService;
        this.antennaEphemerisAckService = antennaEphemerisAckService;
    }

    public void dispatch(AntennaFrame frame)
    {
        switch (frame.getCmd())
        {
            case AntennaProtocolConstants.CMD_SET_ACK:
                antennaAckService.handleSetAck(frame);
                break;
            case AntennaProtocolConstants.CMD_QUERY_ACK:
                antennaQueryAckService.handleQueryAck(frame);
                break;
            case AntennaProtocolConstants.CMD_REPORT:
                antennaReportService.handleReport(frame);
                break;
            case AntennaProtocolConstants.CMD_FAC_QUERY_ACK:
                antennaFactoryAckService.handleFactoryAck(frame);
                break;
            case AntennaProtocolConstants.CMD_BRD_SEARCH_ACK:
                antennaBroadcastAckService.handleBroadcastAck(frame);
                break;
            case AntennaProtocolConstants.CMD_EPHES_SET_ACK:
                antennaEphemerisAckService.handleEphemerisAck(frame);
                break;
            default:
                throw new IllegalArgumentException("不支持的天线协议命令：0x"
                        + Integer.toHexString(frame.getCmd()).toUpperCase());
        }
    }
}