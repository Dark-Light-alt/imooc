package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Monograph;
import com.imooc.service.impl.MonographServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("MonographController")
public class MonographController {

    @Resource
    MonographServiceImpl monographServiceImpl;

    @RequestMapping(value = "findAll",method = RequestMethod.POST)
    public Result findAll(@RequestBody Pages pages){
        Result result = new Result();

        Page<Monograph> data = monographServiceImpl.findAll(pages);

        //设置总页数和总条数
        pages.setTotal(data.getTotal());
        pages.setLastPage(data.getPages());

        System.out.println(data.getRecords());

        System.out.println(pages);

        result.setPages(pages);
        result.putData("monographList",data.getRecords());

        result.success(200,"SUCCESS");

        return result;
    }


    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result update(@RequestBody Monograph monograph){
        Result result = new Result();

        boolean update = monographServiceImpl.update(monograph);

        if(update){
            result.success(200,"操作成功");
        }
        return result;
    }


    @RequestMapping(value = "findById/{monographId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("monographId")String monographId){
        Result result = new Result();

        Monograph monograph = monographServiceImpl.findById(monographId);

        result.putData("monograph",monograph);

        result.success(200,"SUCCESS");

        return result;
    }

    @RequestMapping(value = "soldOut/{monographId}",method = RequestMethod.GET)
    public Result soldOut(@PathVariable("monographId") String monographId){
        Result result = new Result();

        boolean soldOut = monographServiceImpl.soldOut(monographId);

        if(soldOut){
            result.success(200,"操作成功");
        }

        return result;
    }

    @RequestMapping(value = "append",method = RequestMethod.PUT)
    public Result append(@RequestBody Monograph monograph){
        System.out.println(monograph);

        Result result = new Result();

        boolean append = monographServiceImpl.append(monograph);

        if(append){
            result.success(200,"操作成功");
        }

        return result;
    }
}
