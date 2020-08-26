package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.EmployeeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeInfoDao extends BaseMapper<EmployeeInfo> {

    /**
     * 根据 username 获取用户信息
     *
     * @param userName
     * @return
     */
    EmployeeInfo findByUsername(String userName);
}
