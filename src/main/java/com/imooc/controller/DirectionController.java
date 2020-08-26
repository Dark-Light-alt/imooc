package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Direction;
import com.imooc.service.impl.DirectionServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("DirectionController")
public class DirectionController {

    @Resource
    private DirectionServiceImpl directionServiceImpl;

    /**
     * 分页查询所有
     *
     * @param pages
     * @return
     */
    @RequestMapping(value = "pagingFindAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<Direction> data = directionServiceImpl.pagingFindAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.setPages(pages);

        result.putData("directionList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 查询所有启用的课程方向
     *
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("directionList",directionServiceImpl.findAll());

        result.success(200,"SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Direction direction) {

        Result result = new Result();

        directionServiceImpl.append(direction);

        result.success(200, "课程方向添加成功");

        return result;
    }

    @RequestMapping(value = "findById/{directionId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String directionId) {

        Result result = new Result();

        result.putData("direction", directionServiceImpl.findById(directionId));

        result.success(200, "SUCCESS");

        return result;

    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Direction direction) {

        Result result = new Result();

        directionServiceImpl.update(direction);

        result.success(200, "课程方向修改成功");

        return result;
    }

    @RequestMapping(value = "changeEnable/{directionId}/{enable}", method = RequestMethod.GET)
    public Result changeEnable(@PathVariable String directionId, @PathVariable Integer enable) {

        Result result = new Result();

        directionServiceImpl.changeEnable(directionId, enable);

        result.success(200, enable == 0 ? "启用成功" : "禁用成功");

        return result;
    }
}
