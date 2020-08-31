package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CustomerDao;
import com.imooc.entity.Customer;
import com.imooc.service.CustomerSerivce;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerSerivce {
}
