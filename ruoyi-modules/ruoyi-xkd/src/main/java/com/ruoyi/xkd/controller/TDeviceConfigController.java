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
import com.ruoyi.xkd.domain.TDeviceConfig;
import com.ruoyi.xkd.service.ITDeviceConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 设备配置Controller
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/device")
public class TDeviceConfigController extends BaseController
{
    @Autowired
    private ITDeviceConfigService tDeviceConfigService;

    /**
     * 查询设备配置列表
     */
    @RequiresPermissions("xkd:device:list")
    @GetMapping("/list")
    public TableDataInfo list(TDeviceConfig tDeviceConfig)
    {
        startPage();
        List<TDeviceConfig> list = tDeviceConfigService.selectTDeviceConfigList(tDeviceConfig);
        return getDataTable(list);
    }

    /**
     * 导出设备配置列表
     */
    @RequiresPermissions("xkd:device:export")
    @Log(title = "设备配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TDeviceConfig tDeviceConfig)
    {
        List<TDeviceConfig> list = tDeviceConfigService.selectTDeviceConfigList(tDeviceConfig);
        ExcelUtil<TDeviceConfig> util = new ExcelUtil<TDeviceConfig>(TDeviceConfig.class);
        util.exportExcel(response, list, "设备配置数据");
    }

    /**
     * 获取设备配置详细信息
     */
    @RequiresPermissions("xkd:device:query")
    @GetMapping(value = "/{deviceId}")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId)
    {
        return success(tDeviceConfigService.selectTDeviceConfigByDeviceId(deviceId));
    }

    /**
     * 新增设备配置
     */
    @RequiresPermissions("xkd:device:add")
    @Log(title = "设备配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TDeviceConfig tDeviceConfig)
    {
        return toAjax(tDeviceConfigService.insertTDeviceConfig(tDeviceConfig));
    }

    /**
     * 修改设备配置
     */
    @RequiresPermissions("xkd:device:edit")
    @Log(title = "设备配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TDeviceConfig tDeviceConfig)
    {
        return toAjax(tDeviceConfigService.updateTDeviceConfig(tDeviceConfig));
    }

    /**
     * 删除设备配置
     */
    @RequiresPermissions("xkd:device:remove")
    @Log(title = "设备配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deviceIds}")
    public AjaxResult remove(@PathVariable Long[] deviceIds)
    {
        return toAjax(tDeviceConfigService.deleteTDeviceConfigByDeviceIds(deviceIds));
    }
}
