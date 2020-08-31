package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.annotation.Operation;
import com.imooc.entity.AccountNumber;
import com.imooc.service.impl.AccountNumberServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("AccountNumberController")
public class AccountNumberController {

    @Resource
    private AccountNumberServiceImpl accountNumberServiceImpl;

    @Operation("查询账号")
    @RequestMapping(value = "pagingFindAll", method = RequestMethod.POST)
    public Result pagingFindAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<AccountNumber> data = accountNumberServiceImpl.pagingFindAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());
        result.setPages(pages);

        result.putData("accountNumberList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "changeLocked/{accountNumberId}/{islocked}", method = RequestMethod.GET)
    public Result changeLocked(@PathVariable String accountNumberId, @PathVariable Integer islocked) {

        Result result = new Result();

        accountNumberServiceImpl.changeLocked(accountNumberId, islocked);

        result.success(200, islocked == 0 ? "账号解锁成功" : "账号锁定成功");

        return result;
    }

    @RequestMapping(value = "findById/{accountNumberId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String accountNumberId) {

        Result result = new Result();

        result.putData("accountNumber", accountNumberServiceImpl.findById(accountNumberId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * fxy 查询原密码
     * @return
     */
    @RequestMapping(value = "selectPasspord", method = RequestMethod.POST)
    public Result selectPasspord(@RequestBody String password){
        Result result = new Result();

        result.putData("accountNumber", accountNumberServiceImpl.selectPasspord(password));

        System.out.println("aa"+accountNumberServiceImpl.selectPasspord(password));

        result.success(200, "SUCCESS");

        return result;

    }
    /**
     * fxy 根据id查询密码
     */
    @RequestMapping(value = "selectByAccountId/{accountNumberId}", method = RequestMethod.GET)
    public Result selectByAccountId(@PathVariable String accountNumberId){
        Result result = new Result();

        result.putData("passwordById", accountNumberServiceImpl.selectByAccountId(accountNumberId));

        System.out.println("accountNumberId:"+accountNumberServiceImpl.selectByAccountId(accountNumberId));

        result.success(200, "SUCCESS");

        return result;

    }
}
