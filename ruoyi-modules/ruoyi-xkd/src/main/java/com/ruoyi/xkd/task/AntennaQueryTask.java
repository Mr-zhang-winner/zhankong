package com.ruoyi.xkd.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ruoyi.xkd.udp.AntennaUdpServer;

/**
 * 天线状态主动查询定时任务
 */
@Component
public class AntennaQueryTask
{
    private static final Logger log = LoggerFactory.getLogger(AntennaQueryTask.class);

    private static final String TARGET_IP = "192.168.0.7";
    private static final int TARGET_PORT = 4001;

    private final AntennaUdpServer antennaUdpServer;

    private volatile boolean running = true;

    public AntennaQueryTask(AntennaUdpServer antennaUdpServer)
    {
        this.antennaUdpServer = antennaUdpServer;
    }

    public void start()
    {
        this.running = true;
        log.info("定时任务已启动");
    }

    public void stop()
    {
        this.running = false;
        log.info("定时任务已停止");
    }

    public boolean isRunning()
    {
        return running;
    }

    @Scheduled(fixedRate = 30000)
    public void sendQueryCommand()
    {
        if (!running)
        {
            return;
        }

        try
        {
            byte[] cmd = new byte[]{
                (byte)0x7E, (byte)0x00, (byte)0x01, (byte)0x82, (byte)0x82, (byte)0x7E
            };

            antennaUdpServer.sendToDevice(TARGET_IP, TARGET_PORT, cmd);

        }
        catch (Exception e)
        {
            log.error("❌ 发送天线查询指令异常: {}", e.getMessage());
        }
    }
}