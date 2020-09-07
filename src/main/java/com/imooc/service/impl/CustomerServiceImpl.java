package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CustomerDao;
import com.imooc.entity.Customer;
import com.imooc.service.CustomerSerivce;
import com.imooc.utils.SymmetryCryptoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerSerivce {
    @Resource
    private SymmetryCryptoUtil symmetryCryptoUtil;

    @Resource
    private CustomerDao customerDao;

    @Override
    public boolean append(Customer customer) {

        // 对密码进行加密
        customer.setCustomerPassword(symmetryCryptoUtil.encode(customer.getCustomerPassword()));

        return baseMapper.insert(customer) != 0;
    }

    /**
     * 查询手机号数量
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public Integer findPhoneCount(String phone) {

        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getCustomerPhone, phone);

        return baseMapper.selectCount(wrapper);
    }

    @Override
    public Customer findById(String customerId) {
        return baseMapper.selectById(customerId);
    }

    /**
     * 根据手机号或邮箱进行登录
     *
     * @param username 邮箱或者手机号
     * @param password 密码
     * @return
     */
    public Customer login(String username, String password) {

        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Customer::getCustomerPhone, username);
        wrapper.or();
        wrapper.eq(Customer::getCustomerEmail, username);
        wrapper.eq(Customer::getCustomerPassword, symmetryCryptoUtil.encode(password));

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 根据id 查询用户信息
     * @param customerId
     * @return
     */
    public Customer findByCustomerId(String customerId){
        return customerDao.findByCustomerId(customerId);
    }

    /**
     * 修改
     * @param customer
     * @return
     */
    public boolean update(Customer customer) {
        return baseMapper.updateById(customer) != 0;
    }

    public Customer findPosition(String customerId){
        return customerDao.findPosition(customerId);
    }


}
