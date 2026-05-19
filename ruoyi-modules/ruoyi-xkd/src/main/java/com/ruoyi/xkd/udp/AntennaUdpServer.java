package com.ruoyi.xkd.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ruoyi.xkd.domain.TAntennaProtocolLog;
import com.ruoyi.xkd.domain.TDeviceConfig;
import com.ruoyi.xkd.protocol.codec.AntennaFrameCodec;
import com.ruoyi.xkd.protocol.codec.ByteCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaFrame;
import com.ruoyi.xkd.protocol.service.AntennaDeviceIdentifyService;
import com.ruoyi.xkd.protocol.service.AntennaProtocolDispatchService;
import com.ruoyi.xkd.service.ITAntennaProtocolLogService;
import com.ruoyi.common.core.utils.DateUtils;

@Component
public class AntennaUdpServer
{
    private static final Logger log = LoggerFactory.getLogger(AntennaUdpServer.class);

    private final AntennaFrameCodec frameCodec;

    private final AntennaProtocolDispatchService dispatchService;

    private final AntennaDeviceIdentifyService deviceIdentifyService;

    private final ITAntennaProtocolLogService antennaProtocolLogService;

    private volatile boolean running = true;

    private DatagramSocket socket;

    private int listenPort = 8500;

    public AntennaUdpServer(AntennaFrameCodec frameCodec,
                           AntennaProtocolDispatchService dispatchService,
                           AntennaDeviceIdentifyService deviceIdentifyService,
                           ITAntennaProtocolLogService antennaProtocolLogService)
    {
        this.frameCodec = frameCodec;
        this.dispatchService = dispatchService;
        this.deviceIdentifyService = deviceIdentifyService;
        this.antennaProtocolLogService = antennaProtocolLogService;
    }

    @PostConstruct
    public void start()
    {
        Thread thread = new Thread(this::listen, "antenna-udp-server");
        thread.setDaemon(true);
        thread.start();
    }

    private void listen()
    {
        try
        {
            socket = new DatagramSocket(listenPort);
            log.info("天线 UDP 接收服务启动成功，端口：{}", listenPort);

            byte[] buffer = new byte[2048];

            while (running)
            {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                try
                {
                    String remoteIp = packet.getAddress().getHostAddress();
                    Integer remotePort = packet.getPort();

                    AntennaFrame frame = frameCodec.decode(packet.getData(), packet.getLength());
                    frame.setRemoteIp(remoteIp);
                    frame.setRemotePort(remotePort);

                    TDeviceConfig deviceConfig = deviceIdentifyService.identify(remoteIp, remotePort);

                    frame.setDeviceId(deviceConfig.getDeviceId());
                    frame.setDeviceCode(deviceConfig.getDeviceCode());
                    frame.setDeviceName(deviceConfig.getDeviceName());

                    writeReceiveProtocolLog(frame, deviceConfig);

                    dispatchService.dispatch(frame);
                }
                catch (Exception e)
                {
                    // 解码失败时写入错误日志
                    writeDecodeFailureProtocolLog(packet, e);
                    
                    log.error("处理天线 UDP 数据失败", e);
                }
            }
        }
        catch (Exception e)
        {
            log.error("天线 UDP 接收服务异常", e);
        }
    }

    @PreDestroy
    public void stop()
    {
        running = false;

        if (socket != null)
        {
            socket.close();
        }
    }

    private void writeDecodeFailureProtocolLog(DatagramPacket packet, Exception error)
    {
        try
        {
            TAntennaProtocolLog log = new TAntennaProtocolLog();

            log.setDeviceCode(null); // 解码失败时无法识别设备
            log.setDirection("RECEIVE");
            log.setRemoteIp(packet.getAddress().getHostAddress());
            log.setRemotePort((long) packet.getPort());

            byte[] rawBytes = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), 0, rawBytes, 0, packet.getLength());

            log.setCmdCode("UNKNOWN");
            log.setCmdName("UNKNOWN");
            log.setFrameHex(ByteCodec.toHex(rawBytes));
            log.setPayloadHex("");
            log.setCheckStatus("FAILED");
            log.setResultStatus("FAILED");
            log.setErrorMsg(error.getMessage());
            log.setCreateTime(DateUtils.getNowDate());

            antennaProtocolLogService.insertTAntennaProtocolLog(log);
        }
        catch (Exception e)
        {
            log.error("写入解码失败协议日志失败", e);
        }
    }

    private void writeReceiveProtocolLog(AntennaFrame frame, TDeviceConfig deviceConfig)
    {
        try
        {
            TAntennaProtocolLog log = new TAntennaProtocolLog();

            if (deviceConfig != null)
            {
                log.setDeviceCode(deviceConfig.getDeviceCode());
            }
            else
            {
                log.setDeviceCode(frame.getDeviceCode());
            }

            log.setDirection("RECEIVE");
            log.setRemoteIp(frame.getRemoteIp());
            log.setRemotePort(frame.getRemotePort() != null ? frame.getRemotePort().longValue() : null);
            log.setCmdCode(String.format("0x%02X", frame.getCmd()));
            log.setCmdName(cmdName(frame.getCmd()));
            log.setFrameHex(ByteCodec.toHex(frame.getRawFrame()));
            log.setPayloadHex(ByteCodec.toHex(frame.getPayload()));
            log.setCheckStatus("OK");
            log.setResultStatus("SUCCESS");
            log.setCreateTime(DateUtils.getNowDate());

            antennaProtocolLogService.insertTAntennaProtocolLog(log);
        }
        catch (Exception e)
        {
            log.error("写入接收协议日志失败", e);
        }
    }

    private String toHex(int value)
    {
        return String.format("0x%02X", value);
    }

    private String cmdName(int cmd)
    {
        if (cmd == AntennaProtocolConstants.CMD_SET_ACK)
        {
            return "CMD_SET_ACK";
        }
        if (cmd == AntennaProtocolConstants.CMD_QUERY_ACK)
        {
            return "CMD_QUERY_ACK";
        }
        if (cmd == AntennaProtocolConstants.CMD_REPORT)
        {
            return "CMD_REPORT";
        }
        if (cmd == AntennaProtocolConstants.CMD_REPORT_ACK)
        {
            return "CMD_REPORT_ACK";
        }
        if (cmd == AntennaProtocolConstants.CMD_FAC_QUERY_ACK)
        {
            return "CMD_FAC_QUERY_ACK";
        }
        if (cmd == AntennaProtocolConstants.CMD_BRD_SEARCH_ACK)
        {
            return "CMD_BRD_SEARCH_ACK";
        }
        if (cmd == AntennaProtocolConstants.CMD_EPHES_SET_ACK)
        {
            return "CMD_EPHES_SET_ACK";
        }

        return "CMD_" + toHex(cmd);
    }
}