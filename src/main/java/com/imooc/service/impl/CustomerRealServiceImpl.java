package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CustomerRealDao;
import com.imooc.entity.CustomerReal;
import com.imooc.exception.ApiException;
import com.imooc.service.CustomerRealService;
import com.imooc.utils.aliyun.ocr.IdcardOCRService;
import com.imooc.utils.aliyun.ocr.IdcardOCRServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户实名服务实现
 */
@Service
public class CustomerRealServiceImpl extends ServiceImpl<CustomerRealDao, CustomerReal> implements CustomerRealService {

    @Resource
    private IdcardOCRServiceImpl idcardOCRServiceImpl;

    /**
     * 根据用户 id 获取到实名信息
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerReal findByCustomer(String customerId) {

        LambdaQueryWrapper<CustomerReal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerReal::getCustomerId, customerId);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 认证用户的身份证信息
     *
     * @param customerId 用户id
     * @param idcardUrl  身份证存储路径
     * @return
     */
    @Override
    public int authentication(String customerId, String idcardUrl) {

        Map<String, String> identity = idcardOCRServiceImpl.identity(idcardUrl);

        if (null == identity || identity.size() == 0) {
            throw new ApiException(500, "身份证认证失败");
        }

        CustomerReal customerReal = new CustomerReal();

        // 真实姓名
        customerReal.setActualName(identity.get(IdcardOCRService.NAME));
        // 性别
        Integer sex = identity.get(IdcardOCRService.GENDER) == "男" ? 0 : 1;
        customerReal.setSex(sex);
        // 民族
        customerReal.setNation(identity.get(IdcardOCRService.NATIONALITY));
        // 住址
        customerReal.setAddress(identity.get(IdcardOCRService.ADDRESS));
        // 出生日期
        customerReal.setDateOfBirth(identity.get(IdcardOCRService.BIRTH_DATE));
        // 身份证号
        customerReal.setIdcard(identity.get(IdcardOCRService.ID_NUMBER));

        if (findIdcardCount(identity.get(IdcardOCRService.ID_NUMBER)) != 0) {
            throw new ApiException(500, "认证失败，身份证已被绑定");
        }

        customerReal.setUrl(idcardUrl);
        customerReal.setCustomerId(customerId);

        return baseMapper.insert(customerReal);
    }

    /**
     * 查询身份号数量
     *
     * @param idcard
     * @return
     */
    private int findIdcardCount(String idcard) {

        LambdaQueryWrapper<CustomerReal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerReal::getIdcard, idcard);

        return baseMapper.selectCount(wrapper);
    }
}
