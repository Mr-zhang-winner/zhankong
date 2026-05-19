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
import com.ruoyi.xkd.domain.TSatelliteConfig;
import com.ruoyi.xkd.service.ITSatelliteConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 卫星配置Controller
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/staellite")
public class TSatelliteConfigController extends BaseController
{
    @Autowired
    private ITSatelliteConfigService tSatelliteConfigService;

    /**
     * 查询卫星配置列表
     */
    @RequiresPermissions("xkd:staellite:list")
    @GetMapping("/list")
    public TableDataInfo list(TSatelliteConfig tSatelliteConfig)
    {
        startPage();
        List<TSatelliteConfig> list = tSatelliteConfigService.selectTSatelliteConfigList(tSatelliteConfig);
        return getDataTable(list);
    }

    /**
     * 导出卫星配置列表
     */
    @RequiresPermissions("xkd:staellite:export")
    @Log(title = "卫星配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSatelliteConfig tSatelliteConfig)
    {
        List<TSatelliteConfig> list = tSatelliteConfigService.selectTSatelliteConfigList(tSatelliteConfig);
        ExcelUtil<TSatelliteConfig> util = new ExcelUtil<TSatelliteConfig>(TSatelliteConfig.class);
        util.exportExcel(response, list, "卫星配置数据");
    }

    /**
     * 获取卫星配置详细信息
     */
    @RequiresPermissions("xkd:staellite:query")
    @GetMapping(value = "/{satelliteId}")
    public AjaxResult getInfo(@PathVariable("satelliteId") Long satelliteId)
    {
        return success(tSatelliteConfigService.selectTSatelliteConfigBySatelliteId(satelliteId));
    }

    /**
     * 新增卫星配置
     */
    @RequiresPermissions("xkd:staellite:add")
    @Log(title = "卫星配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSatelliteConfig tSatelliteConfig)
    {
        return toAjax(tSatelliteConfigService.insertTSatelliteConfig(tSatelliteConfig));
    }

    /**
     * 修改卫星配置
     */
    @RequiresPermissions("xkd:staellite:edit")
    @Log(title = "卫星配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSatelliteConfig tSatelliteConfig)
    {
        return toAjax(tSatelliteConfigService.updateTSatelliteConfig(tSatelliteConfig));
    }

    /**
     * 删除卫星配置
     */
    @RequiresPermissions("xkd:staellite:remove")
    @Log(title = "卫星配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{satelliteIds}")
    public AjaxResult remove(@PathVariable Long[] satelliteIds)
    {
        return toAjax(tSatelliteConfigService.deleteTSatelliteConfigBySatelliteIds(satelliteIds));
    }
}
