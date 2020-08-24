package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.DepartmentDao;
import com.imooc.entity.Department;
import com.imooc.entity.EmployeeInfo;
import com.imooc.service.DepartmentService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DepartmentService {

    /**
     * 查询部门 wrapper
     */
    private LambdaQueryWrapper<Department> lambdaQueryWrapper(Pages pages){
        LambdaQueryWrapper<Department> wrapper= new LambdaQueryWrapper<>();

        //获取属性名                封装模糊查询
        if (CommonUtils.isNotEmpty(pages.getSearchs().get("name"))) {
            wrapper.like(Department::getDepartmentName, pages.getSearchs().get("name"));
        }

        return wrapper;
    }


    @Override
    public List<Department> selectAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public int insert(Department department) {
        return baseMapper.insert(department);
    }
}
