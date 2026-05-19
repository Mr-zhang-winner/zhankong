package com.ruoyi.xkd.controller;

import java.util.List;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.xkd.domain.TTaskPlan;
import com.ruoyi.xkd.service.ITTaskPlanService;
import com.ruoyi.xkd.service.TaskConflictService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 任务计划Controller
 * 
 * @author ruoyi
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/task")
public class TTaskPlanController extends BaseController
{
    @Autowired
    private ITTaskPlanService tTaskPlanService;

    @Resource
    private TaskConflictService taskConflictService;

    /**
     * 查询任务计划列表
     */
    @RequiresPermissions("xkd:task:list")
    @GetMapping("/list")
    public TableDataInfo list(TTaskPlan tTaskPlan)
    {
        startPage();
        List<TTaskPlan> list = tTaskPlanService.selectTTaskPlanList(tTaskPlan);
        return getDataTable(list);
    }

    /**
     * 导出任务计划列表
     */
    @RequiresPermissions("xkd:task:export")
    @Log(title = "任务计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TTaskPlan tTaskPlan)
    {
        List<TTaskPlan> list = tTaskPlanService.selectTTaskPlanList(tTaskPlan);
        ExcelUtil<TTaskPlan> util = new ExcelUtil<TTaskPlan>(TTaskPlan.class);
        util.exportExcel(response, list, "任务计划数据");
    }

    /**
     * 获取任务计划详细信息
     */
    @RequiresPermissions("xkd:task:query")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(tTaskPlanService.selectTTaskPlanByTaskId(taskId));
    }

    /**
     * 新增任务计划
     */
    @RequiresPermissions("xkd:task:add")
    @Log(title = "任务计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTaskPlan tTaskPlan)
    {
        return toAjax(tTaskPlanService.insertTTaskPlan(tTaskPlan));
    }

    /**
     * 修改任务计划
     */
    @RequiresPermissions("xkd:task:edit")
    @Log(title = "任务计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTaskPlan tTaskPlan)
    {
        return toAjax(tTaskPlanService.updateTTaskPlan(tTaskPlan));
    }

    /**
     * 删除任务计划
     */
    @RequiresPermissions("xkd:task:remove")
    @Log(title = "任务计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(tTaskPlanService.deleteTTaskPlanByTaskIds(taskIds));
    }

    /**
     * 检测任务计划冲突
     */
    @RequiresPermissions("xkd:task:query")
    @PostMapping("/checkConflict")
    public AjaxResult checkConflict(@RequestBody TTaskPlan tTaskPlan)
    {
        taskConflictService.checkConflictOrThrow(tTaskPlan);
        return success("未发现任务冲突");
    }
}
