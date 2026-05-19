package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizCustomerMapper;
import com.ruoyi.system.domain.BizCustomer;
import com.ruoyi.system.service.IBizCustomerService;

/**
 * 客户Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-25
 */
@Service
public class BizCustomerServiceImpl implements IBizCustomerService 
{
    @Autowired
    private BizCustomerMapper bizCustomerMapper;

    /**
     * 查询客户
     * 
     * @param customerId 客户主键
     * @return 客户
     */
    @Override
    public BizCustomer selectBizCustomerByCustomerId(Long customerId)
    {
        return bizCustomerMapper.selectBizCustomerByCustomerId(customerId);
    }

    /**
     * 查询客户列表
     * 
     * @param bizCustomer 客户
     * @return 客户
     */
    @Override
    public List<BizCustomer> selectBizCustomerList(BizCustomer bizCustomer)
    {
        return bizCustomerMapper.selectBizCustomerList(bizCustomer);
    }

    /**
     * 新增客户
     * 
     * @param bizCustomer 客户
     * @return 结果
     */
    @Override
    public int insertBizCustomer(BizCustomer bizCustomer)
    {
        bizCustomer.setCreateTime(DateUtils.getNowDate());
        return bizCustomerMapper.insertBizCustomer(bizCustomer);
    }

    /**
     * 修改客户
     * 
     * @param bizCustomer 客户
     * @return 结果
     */
    @Override
    public int updateBizCustomer(BizCustomer bizCustomer)
    {
        bizCustomer.setUpdateTime(DateUtils.getNowDate());
        return bizCustomerMapper.updateBizCustomer(bizCustomer);
    }

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteBizCustomerByCustomerIds(Long[] customerIds)
    {
        return bizCustomerMapper.deleteBizCustomerByCustomerIds(customerIds);
    }

    /**
     * 删除客户信息
     * 
     * @param customerId 客户主键
     * @return 结果
     */
    @Override
    public int deleteBizCustomerByCustomerId(Long customerId)
    {
        return bizCustomerMapper.deleteBizCustomerByCustomerId(customerId);
    }
}
