package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Direction;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.DirectionServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("DirectionController")
public class DirectionController {

    @Resource
    DirectionServiceImpl directionServiceImpl;

    /**
     * 分页查询所有
     * @param pages
     * @return
     */
    @RequestMapping(value = "findAll",method = RequestMethod.POST)
    public Result findAll(@RequestBody Pages pages){
        Result result = new Result();

        try{
            Page<Direction> data = directionServiceImpl.findAll(pages);

            pages.setLastPage(data.getPages());
            pages.setTotal(data.getTotal());

            result.setPages(pages);
            result.putData("directionList",data.getRecords());

            result.success(200,"SUCCESS");
        }catch(ApiException e){
            result.error(500,e.getMessage());
        }catch (Exception e){
            result.error(500,"服务器开小差");
        }
        return result;
    }
}
