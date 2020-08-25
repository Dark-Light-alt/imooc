package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentDao extends BaseMapper<Department> {
}
