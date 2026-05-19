package com.ruoyi.xkd.service;

import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.xkd.domain.TTaskPlan;
import com.ruoyi.xkd.mapper.TTaskPlanMapper;

@Service
public class TaskConflictService
{

    @Resource
    private TTaskPlanMapper taskPlanMapper;

    public void checkConflictOrThrow(TTaskPlan taskPlan)
    {
        if (taskPlan.getStartTime() == null || taskPlan.getEndTime() == null)
        {
            throw new ServiceException("任务开始时间和结束时间不能为空");
        }

        if (!taskPlan.getStartTime().before(taskPlan.getEndTime()))
        {
            throw new ServiceException("任务结束时间必须晚于开始时间");
        }

        List<TTaskPlan> conflicts = taskPlanMapper.selectConflictTaskList(taskPlan);

        if (conflicts != null && !conflicts.isEmpty())
        {
            TTaskPlan conflict = conflicts.get(0);
            throw new ServiceException("任务时间冲突，冲突任务：" + conflict.getTaskName());
        }
    }
}
