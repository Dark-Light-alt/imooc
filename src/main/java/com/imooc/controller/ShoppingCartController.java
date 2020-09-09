package com.imooc.controller;

import com.imooc.entity.ShoppingCart;
import com.imooc.service.impl.ShoppingCartServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("ShoppingCartController")
public class ShoppingCartController {

    @Resource
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody ShoppingCart shoppingCart) {

        Result result = new Result();

        shoppingCartServiceImpl.append(shoppingCart);

        result.success(200, "商品已添加至购物车，请注意查看");

        return result;
    }

    @RequestMapping(value = "remove/{shoppingCartId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String shoppingCartId) {

        Result result = new Result();

        shoppingCartServiceImpl.removeById(shoppingCartId);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findAll/{customerId}", method = RequestMethod.GET)
    public Result findAll(@PathVariable String customerId) {

        Result result = new Result();

        result.putData("shoppingList", shoppingCartServiceImpl.findAllByCustomer(customerId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 查询购物车商品数量
     *
     * @return
     */
    @RequestMapping(value = "findShoppingCount", method = RequestMethod.GET)
    public Result findShoppingCount() {
        Result result = new Result();

        result.putData("count", shoppingCartServiceImpl.count());

        result.success(200, "SUCCESS");

        return result;
    }
}
