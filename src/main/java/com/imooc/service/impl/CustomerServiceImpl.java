package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CustomerDao;
import com.imooc.entity.Customer;
import com.imooc.service.CustomerService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {

    @Resource
    private CustomerDao customerDao;

    /**
     * 查询所有 用户职位
     *
     * @return
     */

    /**
     * 用户和职位 分页查询
     *
     * @param pages
     * @return
     */
    public Page<Customer> pagingFindAll(Pages pages) {

        Page<Customer> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        return page.setRecords(baseMapper.pagingFindAll(page, lambdaQueryWrapper(pages)));
    }

    /**
     * 查询用户信息 wrapper
     *
     * @param pages
     * @return
     */
    private LambdaQueryWrapper<Customer> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("customerNickname"))) {
            wrapper.like(Customer::getCustomerNickname, pages.getSearchs().get("customerNickname"));
        }

        return wrapper;
    }
}
