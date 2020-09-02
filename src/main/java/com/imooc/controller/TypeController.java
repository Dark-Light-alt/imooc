package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Type;
import com.imooc.service.impl.TypeServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("TypeController")
public class TypeController {

    @Resource
    private TypeServiceImpl typeServiceImpl;

    @RequestMapping(value = "pagingFindAll", method = RequestMethod.POST)
    public Result pagingFindAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<Type> data = typeServiceImpl.pagingFindAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.setPages(pages);

        result.putData("typeList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Type type) {

        Result result = new Result();

        typeServiceImpl.append(type);

        result.success(200, "课程类别添加成功");

        return result;
    }

    @RequestMapping(value = "findById/{typeId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String typeId) {

        Result result = new Result();

        result.putData("type", typeServiceImpl.findById(typeId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Type type) {

        Result result = new Result();

        typeServiceImpl.update(type);

        result.success(200, "课程类别修改成功");

        return result;
    }

    @RequestMapping(value = "changeEnable/{typeId}/{enable}", method = RequestMethod.GET)
    public Result changeEnable(@PathVariable String typeId, @PathVariable Integer enable) {

        Result result = new Result();

        typeServiceImpl.changeEnable(typeId, enable);

        result.success(200, enable == 0 ? "课程类别启用成功" : "课程类别禁用成功");

        return result;
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("typeList", typeServiceImpl.findAll());

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据方向 id 查询出类别
     *
     * @param directionId 方向 id
     * @return
     */
    @RequestMapping(value = "findByDirectionId/{directionId}", method = RequestMethod.GET)
    public Result findByDirectionId(@PathVariable String directionId) {

        Result result = new Result();

        result.putData("typeList", typeServiceImpl.findByDirectionId(directionId));

        result.success(200, "SUCCESS");

        return result;
    }
}
