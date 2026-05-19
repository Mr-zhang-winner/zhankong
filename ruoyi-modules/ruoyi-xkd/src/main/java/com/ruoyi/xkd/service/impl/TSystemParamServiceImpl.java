package com.ruoyi.xkd.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xkd.mapper.TSystemParamMapper;
import com.ruoyi.xkd.domain.TSystemParam;
import com.ruoyi.xkd.service.ITSystemParamService;

/**
 * 系统参数Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Service
public class TSystemParamServiceImpl implements ITSystemParamService 
{
    @Autowired
    private TSystemParamMapper tSystemParamMapper;

    /**
     * 查询系统参数
     * 
     * @param paramId 系统参数主键
     * @return 系统参数
     */
    @Override
    public TSystemParam selectTSystemParamByParamId(Long paramId)
    {
        return tSystemParamMapper.selectTSystemParamByParamId(paramId);
    }

    /**
     * 查询系统参数列表
     * 
     * @param tSystemParam 系统参数
     * @return 系统参数
     */
    @Override
    public List<TSystemParam> selectTSystemParamList(TSystemParam tSystemParam)
    {
        return tSystemParamMapper.selectTSystemParamList(tSystemParam);
    }

    /**
     * 新增系统参数
     * 
     * @param tSystemParam 系统参数
     * @return 结果
     */
    @Override
    public int insertTSystemParam(TSystemParam tSystemParam)
    {
        tSystemParam.setCreateTime(DateUtils.getNowDate());
        return tSystemParamMapper.insertTSystemParam(tSystemParam);
    }

    /**
     * 修改系统参数
     * 
     * @param tSystemParam 系统参数
     * @return 结果
     */
    @Override
    public int updateTSystemParam(TSystemParam tSystemParam)
    {
        tSystemParam.setUpdateTime(DateUtils.getNowDate());
        return tSystemParamMapper.updateTSystemParam(tSystemParam);
    }

    /**
     * 批量删除系统参数
     * 
     * @param paramIds 需要删除的系统参数主键
     * @return 结果
     */
    @Override
    public int deleteTSystemParamByParamIds(Long[] paramIds)
    {
        return tSystemParamMapper.deleteTSystemParamByParamIds(paramIds);
    }

    /**
     * 删除系统参数信息
     * 
     * @param paramId 系统参数主键
     * @return 结果
     */
    @Override
    public int deleteTSystemParamByParamId(Long paramId)
    {
        return tSystemParamMapper.deleteTSystemParamByParamId(paramId);
    }
}
