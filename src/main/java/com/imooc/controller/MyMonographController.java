package com.imooc.controller;

import com.imooc.entity.MyMonograph;
import com.imooc.service.impl.MyMonographServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("MyMonographController")
public class MyMonographController {

    @Resource
    MyMonographServiceImpl myMonographServiceImpl;

    /**
     * 查询用户是否购买过指定专刊
     * @return
     */
    @RequestMapping(value = "isBuyMonograph",method = RequestMethod.POST)
    public Result isBuyMonograph(@RequestBody Map map){
        Result result = new Result();

        String customerId = map.get("customerId").toString();
        String monographId = map.get("monographId").toString();

        MyMonograph myMonograph = myMonographServiceImpl.isBuyMonograph(customerId, monographId);

        if(null != myMonograph){
            //购买了此专刊
            result.putData("hasMonograph",1);
        }else
        {
            //未购买此专刊
            result.putData("hasMonograph",0);
        }

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 查询用户的专刊
     * @param customerId
     * @return
     */
    @RequestMapping(value = "findMonographByCustomerId/{customerId}",method = RequestMethod.GET)
    public Result findMonographByCustomerId(@PathVariable("customerId") String customerId){
        Result result = new Result();

        List<MyMonograph> myMonographs = myMonographServiceImpl.findMonographByCustomerId(customerId);

        result.putData("myMonographs",myMonographs);

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 添加我的专刊
     * @param myMonograph
     * @return
     */
    @RequestMapping(value = "append",method = RequestMethod.POST)
    public Result append(@RequestBody MyMonograph myMonograph){
        Result result = new Result();

        //查询是否存在
        MyMonograph obj = myMonographServiceImpl.findByCustomerIdAndMonographId(myMonograph.getCustomerId(), myMonograph.getMonographId());

        //不存在
        if(null==obj){
            //添加
            myMonographServiceImpl.append(myMonograph);
        }

        result.success(200,"SUCCESS");

        return result;
    }
}
