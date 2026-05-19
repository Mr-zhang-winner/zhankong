package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizCustomer;

/**
 * 客户Service接口
 * 
 * @author ruoyi
 * @date 2026-04-25
 */
public interface IBizCustomerService 
{
    /**
     * 查询客户
     * 
     * @param customerId 客户主键
     * @return 客户
     */
    public BizCustomer selectBizCustomerByCustomerId(Long customerId);

    /**
     * 查询客户列表
     * 
     * @param bizCustomer 客户
     * @return 客户集合
     */
    public List<BizCustomer> selectBizCustomerList(BizCustomer bizCustomer);

    /**
     * 新增客户
     * 
     * @param bizCustomer 客户
     * @return 结果
     */
    public int insertBizCustomer(BizCustomer bizCustomer);

    /**
     * 修改客户
     * 
     * @param bizCustomer 客户
     * @return 结果
     */
    public int updateBizCustomer(BizCustomer bizCustomer);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户主键集合
     * @return 结果
     */
    public int deleteBizCustomerByCustomerIds(Long[] customerIds);

    /**
     * 删除客户信息
     * 
     * @param customerId 客户主键
     * @return 结果
     */
    public int deleteBizCustomerByCustomerId(Long customerId);
}
