package com.ruoyi.xkd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.xkd.domain.TTaskPlan;


/**
 * 任务计划Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-08
 */
public interface TTaskPlanMapper 
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
     * 删除任务计划
     * 
     * @param taskId 任务计划主键
     * @return 结果
     */
    public int deleteTTaskPlanByTaskId(Long taskId);

    /**
     * 批量删除任务计划
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTTaskPlanByTaskIds(Long[] taskIds);

    /**
     * 查询冲突的任务计划列表
     * 
     * @param taskPlan 任务计划
     * @return 冲突的任务计划集合
     */
    public List<TTaskPlan> selectConflictTaskList(TTaskPlan taskPlan);

    public List<TTaskPlan> selectWaitingTasks(@Param("nowTime") java.util.Date nowTime);

    public List<TTaskPlan> selectRunningTasks(@Param("nowTime") java.util.Date nowTime);

}
