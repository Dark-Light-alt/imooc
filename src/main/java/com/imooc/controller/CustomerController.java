package com.imooc.controller;

import com.imooc.service.impl.CustomerServiceImpl;
import com.imooc.utils.ImageVerificationCode;
import com.imooc.utils.SymmetryCryptoUtil;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("CustomerController")
public class CustomerController {

    @Resource
    private CustomerServiceImpl customerServiceImpl;

    @Resource
    private ImageVerificationCode imageVerificationCode;

    @Resource
    private SymmetryCryptoUtil symmetryCryptoUtil;

    /**
     * 生成图片验证码
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result generateImageVerification() {
        return new Result();
    }
}
