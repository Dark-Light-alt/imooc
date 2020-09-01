package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.AccountNumber;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountNumberDao extends BaseMapper<AccountNumber> {

    AccountNumber findByEmployeeId(String employeeId);
}
