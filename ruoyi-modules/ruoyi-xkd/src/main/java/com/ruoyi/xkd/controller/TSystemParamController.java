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
import com.ruoyi.xkd.domain.TSystemParam;
import com.ruoyi.xkd.service.ITSystemParamService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 系统参数Controller
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@RestController
@RequestMapping("/param")
public class TSystemParamController extends BaseController
{
    @Autowired
    private ITSystemParamService tSystemParamService;

    /**
     * 查询系统参数列表
     */
    @RequiresPermissions("xkd:param:list")
    @GetMapping("/list")
    public TableDataInfo list(TSystemParam tSystemParam)
    {
        startPage();
        List<TSystemParam> list = tSystemParamService.selectTSystemParamList(tSystemParam);
        return getDataTable(list);
    }

    /**
     * 导出系统参数列表
     */
    @RequiresPermissions("xkd:param:export")
    @Log(title = "系统参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSystemParam tSystemParam)
    {
        List<TSystemParam> list = tSystemParamService.selectTSystemParamList(tSystemParam);
        ExcelUtil<TSystemParam> util = new ExcelUtil<TSystemParam>(TSystemParam.class);
        util.exportExcel(response, list, "系统参数数据");
    }

    /**
     * 获取系统参数详细信息
     */
    @RequiresPermissions("xkd:param:query")
    @GetMapping(value = "/{paramId}")
    public AjaxResult getInfo(@PathVariable("paramId") Long paramId)
    {
        return success(tSystemParamService.selectTSystemParamByParamId(paramId));
    }

    /**
     * 新增系统参数
     */
    @RequiresPermissions("xkd:param:add")
    @Log(title = "系统参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSystemParam tSystemParam)
    {
        return toAjax(tSystemParamService.insertTSystemParam(tSystemParam));
    }

    /**
     * 修改系统参数
     */
    @RequiresPermissions("xkd:param:edit")
    @Log(title = "系统参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSystemParam tSystemParam)
    {
        return toAjax(tSystemParamService.updateTSystemParam(tSystemParam));
    }

    /**
     * 删除系统参数
     */
    @RequiresPermissions("xkd:param:remove")
    @Log(title = "系统参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{paramIds}")
    public AjaxResult remove(@PathVariable Long[] paramIds)
    {
        return toAjax(tSystemParamService.deleteTSystemParamByParamIds(paramIds));
    }
}
