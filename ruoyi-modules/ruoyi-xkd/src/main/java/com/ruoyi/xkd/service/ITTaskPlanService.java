package com.ruoyi.xkd.service;

import java.util.List;
import com.ruoyi.xkd.domain.TTaskPlan;

/**
 * 任务计划Service接口
 * 
 * @author ruoyi
 * @date 2026-05-08
 */
public interface ITTaskPlanService 
{
    /**
     * 查询任务计划
     * 
     * @param taskId 任务计划主键
     * @return 任务计划
     */
    public TTaskPlan selectTTaskPlanByTaskId(Long taskId);

    /**
     * 查询任务计划列表
     * 
     * @param tTaskPlan 任务计划
     * @return 任务计划集合
     */
    public List<TTaskPlan> selectTTaskPlanList(TTaskPlan tTaskPlan);

    /**
     * 新增任务计划
     * 
     * @param tTaskPlan 任务计划
     * @return 结果
     */
    public int insertTTaskPlan(TTaskPlan tTaskPlan);

    /**
     * 修改任务计划
     * 
     * @param tTaskPlan 任务计划
     * @return 结果
     */
    public int updateTTaskPlan(TTaskPlan tTaskPlan);

    /**
     * 批量删除任务计划
     * 
     * @param taskIds 需要删除的任务计划主键集合
     * @return 结果
     */
    public int deleteTTaskPlanByTaskIds(Long[] taskIds);

    /**
     * 删除任务计划信息
     * 
     * @param taskId 任务计划主键
     * @return 结果
     */
    public int deleteTTaskPlanByTaskId(Long taskId);


}
