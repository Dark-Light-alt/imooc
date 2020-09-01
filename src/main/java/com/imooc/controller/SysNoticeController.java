package com.imooc.controller;

import com.imooc.entity.SysNotice;
import com.imooc.service.impl.SysNoticeServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("SysNoticeController")
public class SysNoticeController {

    @Resource
    private SysNoticeServiceImpl sysNoticeServiceImpl;

    /**
     * 根据用户 id 查询信息
     *
     * @param customerId
     * @return
     */
    @RequestMapping(value = "findAllByCustomerId/{customerId}", method = RequestMethod.GET)
    public Result findAllByCustomerId(@PathVariable String customerId) {

        Result result = new Result();

        List<SysNotice> sysNoticeList = sysNoticeServiceImpl.findAllByCustomerId(customerId);

        result.putData("sysNoticeList", sysNoticeList);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据用户 id 全部标为已读
     *
     * @param customerId
     * @return
     */
    @RequestMapping(value = "markReadAll/{customerId}", method = RequestMethod.GET)
    public Result markReadAll(@PathVariable String customerId) {

        Result result = new Result();

        sysNoticeServiceImpl.markReadAll(customerId);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 删除通知
     *
     * @param sysNoticeId
     * @return
     */
    @RequestMapping(value = "removeById/{sysNoticeId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String sysNoticeId) {

        Result result = new Result();

        sysNoticeServiceImpl.removeById(sysNoticeId);

        result.success(200, "SUCCESS");

        return result;
    }
}
