package com.ruoyi.xkd.task;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.TTaskPlan;
import com.ruoyi.xkd.mapper.TTaskPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 任务计划定时扫描器
 */
@Component
public class TaskPlanScheduler
{
    private static final Logger log = LoggerFactory.getLogger(TaskPlanScheduler.class);

    private final TTaskPlanMapper taskPlanMapper;

    private final TaskExecutionService taskExecutionService;

    public TaskPlanScheduler(TTaskPlanMapper taskPlanMapper,
                             TaskExecutionService taskExecutionService)
    {
        this.taskPlanMapper = taskPlanMapper;
        this.taskExecutionService = taskExecutionService;
    }

    /**
     * 每 5 秒扫描一次待执行任务
     */
    @Scheduled(fixedDelay = 5000)
    public void scanWaitingTasks()
    {
        Date now = DateUtils.getNowDate();

        List<TTaskPlan> tasks = taskPlanMapper.selectWaitingTasks(now);

        if (tasks == null || tasks.isEmpty())
        {
            return;
        }

        for (TTaskPlan task : tasks)
        {
            try
            {
                log.info("开始执行任务，taskId={}, taskName={}", task.getTaskId(), task.getTaskName());
                taskExecutionService.startTask(task);
            }
            catch (Exception e)
            {
                log.error("任务启动失败，taskId={}, error={}", task.getTaskId(), e.getMessage(), e);

                task.setExecuteStatus("FAILED");
                task.setConflictReason(e.getMessage());
                task.setUpdateTime(DateUtils.getNowDate());
                taskPlanMapper.updateTTaskPlan(task);
            }
        }
    }

    /**
     * 每 5 秒扫描一次需要结束的任务
     */
    @Scheduled(fixedDelay = 5000)
    public void scanRunningTasks()
    {
        Date now = DateUtils.getNowDate();

        List<TTaskPlan> tasks = taskPlanMapper.selectRunningTasks(now);

        if (tasks == null || tasks.isEmpty())
        {
            return;
        }

        for (TTaskPlan task : tasks)
        {
            try
            {
                log.info("结束任务，taskId={}, taskName={}", task.getTaskId(), task.getTaskName());
                taskExecutionService.finishTask(task);
            }
            catch (Exception e)
            {
                log.error("任务结束失败，taskId={}, error={}", task.getTaskId(), e.getMessage(), e);
            }
        }
    }
}
