package com.imooc.controller;

import com.imooc.entity.Favorites;
import com.imooc.service.impl.FavoritesServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("FavoritesController")
public class FavoritesController {

    @Resource
    private FavoritesServiceImpl favoritesServiceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Favorites favorites) {

        Result result = new Result();

        favoritesServiceImpl.append(favorites);

        result.success(200, "收藏成功");

        return result;
    }

    @RequestMapping(value = "findByCustomer", method = RequestMethod.POST)
    public Result findByCustomer(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String customerId = params.get("customerId");

        Integer isfree = null;

        if (null != params.get("isfree") && !"null".equals(params.get("isfree"))) {
            isfree = Integer.valueOf(params.get("isfree"));
        }

        result.putData("favoritesList", favoritesServiceImpl.findByCustomer(customerId, isfree));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "remove/{favoritesId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String favoritesId) {

        Result result = new Result();

        favoritesServiceImpl.removeById(favoritesId);

        result.success(200, "已取消收藏");

        return result;
    }
}
