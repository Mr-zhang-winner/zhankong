package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import com.ruoyi.xkd.mapper.TTaskPlanMapper;
import com.ruoyi.xkd.domain.TTaskPlan;
import com.ruoyi.xkd.service.ITTaskPlanService;
import com.ruoyi.xkd.service.TaskConflictService;

/**
 * 任务计划Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-08
 */
@Service
public class TTaskPlanServiceImpl implements ITTaskPlanService 
{
    @Autowired
    private TTaskPlanMapper tTaskPlanMapper;

    @Resource
    private TaskConflictService taskConflictService;

    /**
     * 查询任务计划
     * 
     * @param taskId 任务计划主键
     * @return 任务计划
     */
    @Override
    public TTaskPlan selectTTaskPlanByTaskId(Long taskId)
    {
        return tTaskPlanMapper.selectTTaskPlanByTaskId(taskId);
    }

    /**
     * 查询任务计划列表
     * 
     * @param tTaskPlan 任务计划
     * @return 任务计划
     */
    @Override
    public List<TTaskPlan> selectTTaskPlanList(TTaskPlan tTaskPlan)
    {
        return tTaskPlanMapper.selectTTaskPlanList(tTaskPlan);
    }

    /**
     * 新增任务计划
     * 
     * @param tTaskPlan 任务计划
     * @return 结果
     */
    @Override
    public int insertTTaskPlan(TTaskPlan tTaskPlan)
    {
        taskConflictService.checkConflictOrThrow(tTaskPlan);
        tTaskPlan.setExecuteStatus("WAITING");
        tTaskPlan.setConflictStatus("0");
        if (tTaskPlan.getTaskType() == null || tTaskPlan.getTaskType().isEmpty())
        {
            tTaskPlan.setTaskType("MANUAL");
        }
        tTaskPlan.setCreateTime(DateUtils.getNowDate());
        return tTaskPlanMapper.insertTTaskPlan(tTaskPlan);
    }

    /**
     * 修改任务计划
     * 
     * @param tTaskPlan 任务计划
     * @return 结果
     */
    @Override
    public int updateTTaskPlan(TTaskPlan tTaskPlan)
    {
        TTaskPlan old = tTaskPlanMapper.selectTTaskPlanByTaskId(tTaskPlan.getTaskId());

        if (old != null && ("RUNNING".equals(old.getExecuteStatus()) || "FINISHED".equals(old.getExecuteStatus())))
        {
            throw new ServiceException("执行中或已完成的任务不允许修改");
        }

        taskConflictService.checkConflictOrThrow(tTaskPlan);
        tTaskPlan.setUpdateTime(DateUtils.getNowDate());
        return tTaskPlanMapper.updateTTaskPlan(tTaskPlan);
    }

    /**
     * 批量删除任务计划
     * 
     * @param taskIds 需要删除的任务计划主键
     * @return 结果
     */
    @Override
    public int deleteTTaskPlanByTaskIds(Long[] taskIds)
    {
        for (Long taskId : taskIds)
        {
            TTaskPlan task = tTaskPlanMapper.selectTTaskPlanByTaskId(taskId);
            if (task != null && ("RUNNING".equals(task.getExecuteStatus()) || "FINISHED".equals(task.getExecuteStatus())))
            {
                throw new ServiceException("执行中或已完成的任务不允许删除：" + task.getTaskName());
            }
        }
        return tTaskPlanMapper.deleteTTaskPlanByTaskIds(taskIds);
    }

    /**
     * 删除任务计划信息
     * 
     * @param taskId 任务计划主键
     * @return 结果
     */
    @Override
    public int deleteTTaskPlanByTaskId(Long taskId)
    {
        TTaskPlan task = tTaskPlanMapper.selectTTaskPlanByTaskId(taskId);
        if (task != null && ("RUNNING".equals(task.getExecuteStatus()) || "FINISHED".equals(task.getExecuteStatus())))
        {
            throw new ServiceException("执行中或已完成的任务不允许删除：" + task.getTaskName());
        }
        return tTaskPlanMapper.deleteTTaskPlanByTaskId(taskId);
    }
}
