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
import com.ruoyi.xkd.domain.TStationConfig;
import com.ruoyi.xkd.service.ITStationConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 站址配置Controller
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/station")
public class TStationConfigController extends BaseController
{
    @Autowired
    private ITStationConfigService tStationConfigService;

    /**
     * 查询站址配置列表
     */
    @RequiresPermissions("xkd:station:list")
    @GetMapping("/list")
    public TableDataInfo list(TStationConfig tStationConfig)
    {
        startPage();
        List<TStationConfig> list = tStationConfigService.selectTStationConfigList(tStationConfig);
        return getDataTable(list);
    }

    /**
     * 导出站址配置列表
     */
    @RequiresPermissions("xkd:station:export")
    @Log(title = "站址配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStationConfig tStationConfig)
    {
        List<TStationConfig> list = tStationConfigService.selectTStationConfigList(tStationConfig);
        ExcelUtil<TStationConfig> util = new ExcelUtil<TStationConfig>(TStationConfig.class);
        util.exportExcel(response, list, "站址配置数据");
    }

    /**
     * 获取站址配置详细信息
     */
    @RequiresPermissions("xkd:station:query")
    @GetMapping(value = "/{stationId}")
    public AjaxResult getInfo(@PathVariable("stationId") Long stationId)
    {
        return success(tStationConfigService.selectTStationConfigByStationId(stationId));
    }

    /**
     * 新增站址配置
     */
    @RequiresPermissions("xkd:station:add")
    @Log(title = "站址配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TStationConfig tStationConfig)
    {
        return toAjax(tStationConfigService.insertTStationConfig(tStationConfig));
    }

    /**
     * 修改站址配置
     */
    @RequiresPermissions("xkd:station:edit")
    @Log(title = "站址配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TStationConfig tStationConfig)
    {
        return toAjax(tStationConfigService.updateTStationConfig(tStationConfig));
    }

    /**
     * 删除站址配置
     */
    @RequiresPermissions("xkd:station:remove")
    @Log(title = "站址配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{stationIds}")
    public AjaxResult remove(@PathVariable Long[] stationIds)
    {
        return toAjax(tStationConfigService.deleteTStationConfigByStationIds(stationIds));
    }
}
