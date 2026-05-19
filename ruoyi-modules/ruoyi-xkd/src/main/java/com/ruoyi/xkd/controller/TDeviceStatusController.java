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
import com.ruoyi.xkd.domain.TDeviceStatus;
import com.ruoyi.xkd.service.ITDeviceStatusService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 设备状态Controller
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/deviceStatus")
public class TDeviceStatusController extends BaseController
{
    @Autowired
    private ITDeviceStatusService tDeviceStatusService;

    /**
     * 查询设备状态列表
     */
    @RequiresPermissions("xkd:deviceStatus:list")
    @GetMapping("/list")
    public TableDataInfo list(TDeviceStatus tDeviceStatus)
    {
        startPage();
        List<TDeviceStatus> list = tDeviceStatusService.selectTDeviceStatusList(tDeviceStatus);
        return getDataTable(list);
    }

    /**
     * 导出设备状态列表
     */
    @RequiresPermissions("xkd:deviceStatus:export")
    @Log(title = "设备状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TDeviceStatus tDeviceStatus)
    {
        List<TDeviceStatus> list = tDeviceStatusService.selectTDeviceStatusList(tDeviceStatus);
        ExcelUtil<TDeviceStatus> util = new ExcelUtil<TDeviceStatus>(TDeviceStatus.class);
        util.exportExcel(response, list, "设备状态数据");
    }

    /**
     * 获取设备状态详细信息
     */
    @RequiresPermissions("xkd:deviceStatus:query")
    @GetMapping(value = "/{statusId}")
    public AjaxResult getInfo(@PathVariable("statusId") Long statusId)
    {
        return success(tDeviceStatusService.selectTDeviceStatusByStatusId(statusId));
    }

    /**
     * 新增设备状态
     */
    @RequiresPermissions("xkd:deviceStatus:add")
    @Log(title = "设备状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TDeviceStatus tDeviceStatus)
    {
        return toAjax(tDeviceStatusService.insertTDeviceStatus(tDeviceStatus));
    }

    /**
     * 修改设备状态
     */
    @RequiresPermissions("xkd:deviceStatus:edit")
    @Log(title = "设备状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TDeviceStatus tDeviceStatus)
    {
        return toAjax(tDeviceStatusService.updateTDeviceStatus(tDeviceStatus));
    }

    /**
     * 删除设备状态
     */
    @RequiresPermissions("xkd:deviceStatus:remove")
    @Log(title = "设备状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{statusIds}")
    public AjaxResult remove(@PathVariable Long[] statusIds)
    {
        return toAjax(tDeviceStatusService.deleteTDeviceStatusByStatusIds(statusIds));
    }
}
