package com.ruoyi.xkd.service;

import java.util.List;
import com.ruoyi.xkd.domain.TSystemParam;

/**
 * 系统参数Service接口
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public interface ITSystemParamService 
{
    /**
     * 查询系统参数
     * 
     * @param paramId 系统参数主键
     * @return 系统参数
     */
    public TSystemParam selectTSystemParamByParamId(Long paramId);

    /**
     * 查询系统参数列表
     * 
     * @param tSystemParam 系统参数
     * @return 系统参数集合
     */
    public List<TSystemParam> selectTSystemParamList(TSystemParam tSystemParam);

    /**
     * 新增系统参数
     * 
     * @param tSystemParam 系统参数
     * @return 结果
     */
    public int insertTSystemParam(TSystemParam tSystemParam);

    /**
     * 修改系统参数
     * 
     * @param tSystemParam 系统参数
     * @return 结果
     */
    public int updateTSystemParam(TSystemParam tSystemParam);

    /**
     * 批量删除系统参数
     * 
     * @param paramIds 需要删除的系统参数主键集合
     * @return 结果
     */
    public int deleteTSystemParamByParamIds(Long[] paramIds);

    /**
     * 删除系统参数信息
     * 
     * @param paramId 系统参数主键
     * @return 结果
     */
    public int deleteTSystemParamByParamId(Long paramId);
}
