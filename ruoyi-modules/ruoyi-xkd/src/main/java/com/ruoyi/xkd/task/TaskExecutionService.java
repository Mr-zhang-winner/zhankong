package com.ruoyi.xkd.task;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.TDeviceConfig;
import com.ruoyi.xkd.domain.TTaskPlan;
import com.ruoyi.xkd.mapper.TDeviceConfigMapper;
import com.ruoyi.xkd.mapper.TTaskPlanMapper;
import com.ruoyi.xkd.protocol.service.AntennaProtocolSendService;
import com.ruoyi.xkd.service.TaskConflictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务执行服务
 */
@Service
public class TaskExecutionService
{
    private final TTaskPlanMapper taskPlanMapper;

    private final TDeviceConfigMapper deviceConfigMapper;

    private final TaskConflictService taskConflictService;

    private final AntennaProtocolSendService antennaProtocolSendService;

    public TaskExecutionService(TTaskPlanMapper taskPlanMapper,
                                TDeviceConfigMapper deviceConfigMapper,
                                TaskConflictService taskConflictService,
                                AntennaProtocolSendService antennaProtocolSendService)
    {
        this.taskPlanMapper = taskPlanMapper;
        this.deviceConfigMapper = deviceConfigMapper;
        this.taskConflictService = taskConflictService;
        this.antennaProtocolSendService = antennaProtocolSendService;
    }

    /**
     * 启动任务
     */
    public void startTask(TTaskPlan task)
    {
        // 1. 启动前再次做冲突检测
        taskConflictService.checkConflictOrThrow(task);

        // 2. 查询设备
        TDeviceConfig device = selectDevice(task.getDeviceId());

        // 3. 根据任务参数执行控制动作
        executeDeviceActions(task, device);

        // 4. 更新任务状态为 RUNNING
        task.setExecuteStatus("RUNNING");
        task.setUpdateTime(DateUtils.getNowDate());
        taskPlanMapper.updateTTaskPlan(task);
    }

    /**
     * 完成任务
     */
    public void finishTask(TTaskPlan task)
    {
        task.setExecuteStatus("FINISHED");
        task.setUpdateTime(DateUtils.getNowDate());
        taskPlanMapper.updateTTaskPlan(task);
    }

    private TDeviceConfig selectDevice(Long deviceId)
    {
        if (deviceId == null)
        {
            throw new RuntimeException("任务未配置设备ID");
        }

        TDeviceConfig query = new TDeviceConfig();
        query.setDeviceId(deviceId);

        List<TDeviceConfig> list = deviceConfigMapper.selectTDeviceConfigList(query);

        if (list == null || list.isEmpty())
        {
            throw new RuntimeException("未找到设备配置，deviceId=" + deviceId);
        }

        return list.get(0);
    }

    private void executeDeviceActions(TTaskPlan task, TDeviceConfig device)
    {
        String paramConfig = task.getParamConfig();

        if (paramConfig == null || paramConfig.trim().isEmpty())
        {
            // 没有任务参数时，先只做状态查询
            antennaProtocolSendService.send(
                    device.getDeviceCode(),
                    device.getIpAddress(),
                    device.getUdpPort() != null ? device.getUdpPort().intValue() : null,
                    0x82,
                    new byte[0]
            );
            return;
        }

        // 第五阶段第一版可以先不复杂解析，只记录并做一次全查询
        antennaProtocolSendService.send(
                device.getDeviceCode(),
                device.getIpAddress(),
                device.getUdpPort() != null ? device.getUdpPort().intValue() : null,
                0x82,
                new byte[0]
        );
    }
}
