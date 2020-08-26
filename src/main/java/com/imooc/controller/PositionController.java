package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Position;
import com.imooc.service.impl.PositionServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("PositionController")
public class PositionController {

    @Resource
    private PositionServiceImpl positionServiceImpl;

    @RequestMapping(value = "pagingFindAll", method = RequestMethod.POST)
    public Result pagingFindAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<Position> data = positionServiceImpl.pagingFindAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.setPages(pages);

        result.putData("positionList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("positionList", positionServiceImpl.findAll());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Position position) {

        Result result = new Result();

        positionServiceImpl.append(position);

        result.success(200, "职位信息添加成功");

        return result;
    }

    @RequestMapping(value = "findById/{positionId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String positionId) {

        Result result = new Result();

        result.putData("position", positionServiceImpl.findById(positionId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Position position) {

        Result result = new Result();

        positionServiceImpl.update(position);

        result.success(200, "职位信息修改成功");

        return result;
    }

    @RequestMapping(value = "remove/{positionId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String positionId) {

        Result result = new Result();

        positionServiceImpl.remove(positionId);

        result.success(200, "职位信息删除成功");

        return result;
    }
}
