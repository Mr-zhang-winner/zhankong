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
import com.ruoyi.xkd.domain.TAntennaProtocolLog;
import com.ruoyi.xkd.service.ITAntennaProtocolLogService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 天线协议收发日志Controller
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
@RestController
@RequestMapping("/TAntennaProtocolLog")
public class TAntennaProtocolLogController extends BaseController
{
    @Autowired
    private ITAntennaProtocolLogService tAntennaProtocolLogService;

    /**
     * 查询天线协议收发日志列表
     */
    @RequiresPermissions("xkd:TAntennaProtocolLog:list")
    @GetMapping("/list")
    public TableDataInfo list(TAntennaProtocolLog tAntennaProtocolLog)
    {
        startPage();
        List<TAntennaProtocolLog> list = tAntennaProtocolLogService.selectTAntennaProtocolLogList(tAntennaProtocolLog);
        return getDataTable(list);
    }

    /**
     * 导出天线协议收发日志列表
     */
    @RequiresPermissions("xkd:TAntennaProtocolLog:export")
    @Log(title = "天线协议收发日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TAntennaProtocolLog tAntennaProtocolLog)
    {
        List<TAntennaProtocolLog> list = tAntennaProtocolLogService.selectTAntennaProtocolLogList(tAntennaProtocolLog);
        ExcelUtil<TAntennaProtocolLog> util = new ExcelUtil<TAntennaProtocolLog>(TAntennaProtocolLog.class);
        util.exportExcel(response, list, "天线协议收发日志数据");
    }

    /**
     * 获取天线协议收发日志详细信息
     */
    @RequiresPermissions("xkd:TAntennaProtocolLog:query")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId)
    {
        return success(tAntennaProtocolLogService.selectTAntennaProtocolLogByLogId(logId));
    }

    /**
     * 新增天线协议收发日志
     */
    @RequiresPermissions("xkd:TAntennaProtocolLog:add")
    @Log(title = "天线协议收发日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TAntennaProtocolLog tAntennaProtocolLog)
    {
        return toAjax(tAntennaProtocolLogService.insertTAntennaProtocolLog(tAntennaProtocolLog));
    }

    /**
     * 修改天线协议收发日志
     */
    @RequiresPermissions("xkd:TAntennaProtocolLog:edit")
    @Log(title = "天线协议收发日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TAntennaProtocolLog tAntennaProtocolLog)
    {
        return toAjax(tAntennaProtocolLogService.updateTAntennaProtocolLog(tAntennaProtocolLog));
    }

    /**
     * 删除天线协议收发日志
     */
    @RequiresPermissions("xkd:TAntennaProtocolLog:remove")
    @Log(title = "天线协议收发日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable Long[] logIds)
    {
        return toAjax(tAntennaProtocolLogService.deleteTAntennaProtocolLogByLogIds(logIds));
    }
}
