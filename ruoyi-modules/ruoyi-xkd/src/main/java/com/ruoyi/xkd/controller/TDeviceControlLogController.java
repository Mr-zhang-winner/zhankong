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
import com.ruoyi.xkd.domain.TDeviceControlLog;
import com.ruoyi.xkd.service.ITDeviceControlLogService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 设备控制日志Controller
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
@RestController
@RequestMapping("/deviceControlLog")
public class TDeviceControlLogController extends BaseController
{
    @Autowired
    private ITDeviceControlLogService tDeviceControlLogService;

    /**
     * 查询设备控制日志列表
     */
    @RequiresPermissions("xkd:deviceControlLog:list")
    @GetMapping("/list")
    public TableDataInfo list(TDeviceControlLog tDeviceControlLog)
    {
        startPage();
        List<TDeviceControlLog> list = tDeviceControlLogService.selectTDeviceControlLogList(tDeviceControlLog);
        return getDataTable(list);
    }

    /**
     * 导出设备控制日志列表
     */
    @RequiresPermissions("xkd:deviceControlLog:export")
    @Log(title = "设备控制日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TDeviceControlLog tDeviceControlLog)
    {
        List<TDeviceControlLog> list = tDeviceControlLogService.selectTDeviceControlLogList(tDeviceControlLog);
        ExcelUtil<TDeviceControlLog> util = new ExcelUtil<TDeviceControlLog>(TDeviceControlLog.class);
        util.exportExcel(response, list, "设备控制日志数据");
    }

    /**
     * 获取设备控制日志详细信息
     */
    @RequiresPermissions("xkd:deviceControlLog:query")
    @GetMapping(value = "/{controlId}")
    public AjaxResult getInfo(@PathVariable("controlId") Long controlId)
    {
        return success(tDeviceControlLogService.selectTDeviceControlLogByControlId(controlId));
    }

    /**
     * 新增设备控制日志
     */
    @RequiresPermissions("xkd:deviceControlLog:add")
    @Log(title = "设备控制日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TDeviceControlLog tDeviceControlLog)
    {
        return toAjax(tDeviceControlLogService.insertTDeviceControlLog(tDeviceControlLog));
    }

    /**
     * 修改设备控制日志
     */
    @RequiresPermissions("xkd:deviceControlLog:edit")
    @Log(title = "设备控制日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TDeviceControlLog tDeviceControlLog)
    {
        return toAjax(tDeviceControlLogService.updateTDeviceControlLog(tDeviceControlLog));
    }

    /**
     * 删除设备控制日志
     */
    @RequiresPermissions("xkd:deviceControlLog:remove")
    @Log(title = "设备控制日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{controlIds}")
    public AjaxResult remove(@PathVariable Long[] controlIds)
    {
        return toAjax(tDeviceControlLogService.deleteTDeviceControlLogByControlIds(controlIds));
    }
}
