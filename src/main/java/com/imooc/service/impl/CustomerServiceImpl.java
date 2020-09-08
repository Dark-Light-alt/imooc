package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
     * 查询邮箱数量
     *
     * @param email 邮箱
     * @return
     */
    @Override
    public int findEmailCount(String email) {

        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getCustomerEmail, email);

        return baseMapper.selectCount(wrapper);
    }

    /**
     * 绑定邮箱
     *
     * @param customer 用户 id
     * @param email    邮箱地址
     * @return
     */
    @Override
    public int bindEmail(String customer, String email) {

        LambdaUpdateWrapper<Customer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Customer::getCustomerEmail, email);
        wrapper.eq(Customer::getCustomerId, customer);

        return baseMapper.update(null, wrapper);
    }

    /**
     * 更换手机号
     *
     * @param customer 用户 id
     * @param phone    手机号
     * @return
     */
    @Override
    public int updatePhone(String customer, String phone) {

        LambdaUpdateWrapper<Customer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Customer::getCustomerPhone, phone);
        wrapper.eq(Customer::getCustomerId, customer);

        return baseMapper.update(null, wrapper);
    }
}
