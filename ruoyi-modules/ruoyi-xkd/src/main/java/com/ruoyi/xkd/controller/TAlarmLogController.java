package com.ruoyi.xkd.controller;

import java.util.List;
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
import com.ruoyi.xkd.domain.TAlarmLog;
import com.ruoyi.xkd.service.ITAlarmLogService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 告警日志Controller
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/alarm")
public class TAlarmLogController extends BaseController
{
    @Autowired
    private ITAlarmLogService tAlarmLogService;

    /**
     * 查询告警日志列表
     */
    @RequiresPermissions("xkd:alarm:list")
    @GetMapping("/list")
    public TableDataInfo list(TAlarmLog tAlarmLog)
    {
        startPage();
        List<TAlarmLog> list = tAlarmLogService.selectTAlarmLogList(tAlarmLog);
        return getDataTable(list);
    }

    /**
     * 导出告警日志列表
     */
    @RequiresPermissions("xkd:alarm:export")
    @Log(title = "告警日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TAlarmLog tAlarmLog)
    {
        List<TAlarmLog> list = tAlarmLogService.selectTAlarmLogList(tAlarmLog);
        ExcelUtil<TAlarmLog> util = new ExcelUtil<TAlarmLog>(TAlarmLog.class);
        util.exportExcel(response, list, "告警日志数据");
    }

    /**
     * 获取告警日志详细信息
     */
    @RequiresPermissions("xkd:alarm:query")
    @GetMapping(value = "/{alarmId}")
    public AjaxResult getInfo(@PathVariable("alarmId") Long alarmId)
    {
        return success(tAlarmLogService.selectTAlarmLogByAlarmId(alarmId));
    }

    /**
     * 新增告警日志
     */
    @RequiresPermissions("xkd:alarm:add")
    @Log(title = "告警日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TAlarmLog tAlarmLog)
    {
        return toAjax(tAlarmLogService.insertTAlarmLog(tAlarmLog));
    }

    /**
     * 修改告警日志
     */
    @RequiresPermissions("xkd:alarm:edit")
    @Log(title = "告警日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TAlarmLog tAlarmLog)
    {
        return toAjax(tAlarmLogService.updateTAlarmLog(tAlarmLog));
    }

    /**
     * 删除告警日志
     */
    @RequiresPermissions("xkd:alarm:remove")
    @Log(title = "告警日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alarmIds}")
    public AjaxResult remove(@PathVariable Long[] alarmIds)
    {
        return toAjax(tAlarmLogService.deleteTAlarmLogByAlarmIds(alarmIds));
    }
}
