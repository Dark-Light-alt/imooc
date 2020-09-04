package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ShoppingCartDao;
import com.imooc.entity.ShoppingCart;
import com.imooc.exception.ApiException;
import com.imooc.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 购物车服务实现
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    /**
     * 添加购物车信息
     *
     * @param shoppingCart
     * @return
     */
    public boolean append(ShoppingCart shoppingCart) {

        int count = verifyExist(shoppingCart.getCustomerId(), shoppingCart.getCourseId());

        if (count != 0) {
            throw new ApiException(500, "商品已经在购物车内");
        }

        return baseMapper.insert(shoppingCart) != 0;
    }

    /**
     * 根据用户查询
     *
     * @param customerId
     * @return
     */
    public List<Map<String, Object>> findAllByCustomer(String customerId) {

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getCustomerId, customerId);
        wrapper.orderByDesc(ShoppingCart::getCreateTime);

        return baseMapper.findAllByCustomer(wrapper);
    }


    /**
     * 验证用户购物车里是否已经存在某课程
     *
     * @param customerId 用户 id
     * @param courseId   课程 id
     * @return
     */
    public int verifyExist(String customerId, String courseId) {

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(ShoppingCart::getCustomerId, customerId);
        wrapper.eq(ShoppingCart::getCourseId, courseId);

        return baseMapper.selectCount(wrapper);
    }
}
