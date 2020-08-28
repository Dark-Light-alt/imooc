package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Monograph;
import com.imooc.service.impl.MonographServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("MonographController")
public class MonographController {

    @Resource
    MonographServiceImpl monographServiceImpl;

    /**
     * 修改
     * @param monograph
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result update(@RequestBody Monograph monograph){
        Result result = new Result();

        boolean update = monographServiceImpl.update(monograph);

        if(update){
            result.success(200,"操作成功");
        }
        return result;
    }


    /**
     * 根据monographId查询
     * @param monographId
     * @return
     */
    @RequestMapping(value = "findById/{monographId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("monographId")String monographId){
        Result result = new Result();

        Monograph monograph = monographServiceImpl.findById(monographId);

        result.putData("monograph",monograph);

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 修改专栏状态
     * @param map
     * @return
     */
    @RequestMapping(value = "updateOffShelf",method = RequestMethod.POST)
    public Result soldOut(@RequestBody Map map){
        String monographId = map.get("monographId").toString();

        int offShelf = Integer.parseInt(map.get("offShelf").toString());
        Result result = new Result();

        boolean update = monographServiceImpl.updateOffShelf(monographId,offShelf);

        if(update){
            result.success(200,"操作成功");
        }

        return result;
    }

    /**
     * 添加
     * @param monograph
     * @return
     */
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


    /**
     * 分页关联查询专栏和作者
     * @param map
     * @return
     */
    @RequestMapping(value = "pageFindMonographAuthor",method = RequestMethod.POST)
    public Result pageFindMonographAuthor(@RequestBody Map map){
        Result result = new Result();

        Pages pages =  JSONObject.parseObject(map.get("pages").toString(),Pages.class);

        Page<Monograph> data = null;

        if(null != map.get("employeeId")){
            //如果员工编号不为null
            //查询员工的专刊
            String employeeId = map.get("employeeId").toString();
            data = monographServiceImpl.findAllByEmployeeId(pages,employeeId);
        }else
        {
            //根据完成状态分页查询
            data = monographServiceImpl.pageFindMonographAuthor(pages);
        }

        //设置总页数和总条数
        pages.setTotal(data.getTotal());
        pages.setLastPage(data.getPages());

        result.setPages(pages);
        result.putData("monographList",data.getRecords());

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 删除专栏
     * @param monographId
     * @return
     */
    @RequestMapping(value = "delete/{monographId}",method = RequestMethod.GET)
    public Result delete(@PathVariable("monographId") String monographId){
        Result result = new Result();

        int i = monographServiceImpl.delete(monographId);

        if(i>0){
            result.success(200,"操作成功");
        }

        return result;
    }


}
