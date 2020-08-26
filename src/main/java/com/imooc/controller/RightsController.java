package com.imooc.controller;

import com.alibaba.fastjson.JSONArray;
import com.imooc.entity.PositionRightsBridge;
import com.imooc.entity.Rights;
import com.imooc.service.impl.PositionRightsBridgeServiceImpl;
import com.imooc.service.impl.RightsServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("RightsController")
public class RightsController {

    @Resource
    private RightsServiceImpl rightsServiceImpl;

    @Resource
    private PositionRightsBridgeServiceImpl positionRightsBridgeServiceImpl;

    @RequestMapping(value = "treeFindAll", method = RequestMethod.GET)
    public Result treeFindAll() {

        Result result = new Result();

        result.putData("rightsList", rightsServiceImpl.treeFindAll());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findParent", method = RequestMethod.GET)
    public Result findParent() {
        Result result = new Result();

        result.putData("rightsList", rightsServiceImpl.findParent());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Rights rights) {

        Result result = new Result();

        rightsServiceImpl.append(rights);

        result.success(200, "权限信息添加成功");

        return result;
    }

    @RequestMapping(value = "findById/{rightsId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String rightsId) {

        Result result = new Result();

        result.putData("rights", rightsServiceImpl.findById(rightsId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Rights rights) {

        Result result = new Result();

        rightsServiceImpl.update(rights);

        result.success(200, "权限信息修改成功");

        return result;
    }

    @RequestMapping(value = "findHaveThreeRights/{positionId}", method = RequestMethod.GET)
    public Result findHaveThreeRights(@PathVariable String positionId) {

        Result result = new Result();

        result.putData("haveThreeRights", rightsServiceImpl.findHaveThreeRights(positionId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "allocationRights", method = RequestMethod.PUT)
    public Result allocationRights(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String positionId = String.valueOf(params.get("positionId"));

        ArrayList<String> arrayList = JSONArray.parseObject(params.get("keys"), ArrayList.class);

        positionRightsBridgeServiceImpl.removeRightsByPositionId(positionId);

        PositionRightsBridge positionRightsBridge = new PositionRightsBridge();
        positionRightsBridge.setPositionId(positionId);

        arrayList.forEach(key -> {
            positionRightsBridge.setRightsId(key);
            positionRightsBridgeServiceImpl.append(positionRightsBridge);
        });

        result.success(200, "权限分配成功");

        return result;
    }

    /**
     * 根据职位查询出拥有的权限列表
     *
     * @param positionId
     * @return
     */
    @RequestMapping(value = "findRightsByPositionId/{positionId}", method = RequestMethod.GET)
    public Result findRightsByPositionId(@PathVariable String positionId) {

        Result result = new Result();

        result.putData("rightsList", rightsServiceImpl.findRightsByPositionId(positionId));

        result.success(200, "SUCCESS");

        return result;
    }
}
