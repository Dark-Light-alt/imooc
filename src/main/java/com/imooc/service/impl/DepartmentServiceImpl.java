package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.DepartmentDao;
import com.imooc.entity.Department;
import com.imooc.entity.EmployeeInfo;
import com.imooc.exception.ApiException;
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


    /*@Override
    public List<Department> selectAll() {
        return baseMapper.selectList(null);
    }


    */

    @Override
    public Page<Department> findAll(Pages pages) {
        Page page = new Page(pages.getCurrentPage(),pages.getPageSize());
        return baseMapper.selectPage(page,this.lambdaQueryWrapper(pages));
    }

    @Override
    public boolean insert(Department department) {

        valid(department);

        validExist(department,true);
        return baseMapper.insert(department) != 0;
    }

    private void valid(Department department) {

        if (!CommonUtils.isNotEmpty(department.getDepartmentName())) {
            throw new ApiException(500, "部门名称不能为空");
        }

    }

    private void validExist(Department department,Boolean flag) {
        Department departmentName = findByParam("department_name", department.getDepartmentName());

        if (flag) {
            if (null != departmentName) {
                throw new ApiException(500, "部门已存在");
            }
        }

        if (null != departmentName && !departmentName.getDepartmentId().equals(department.getDepartmentId())) {
            throw new ApiException(500, "部门已存在");
        }

    }


    private Department findByParam(String column, Object value) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectOne(wrapper);
    }
}
