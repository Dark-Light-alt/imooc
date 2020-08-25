package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.DepartmentDao;
import com.imooc.entity.Department;
import com.imooc.exception.ApiException;
import com.imooc.service.DepartmentService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DepartmentService {

    @Resource
    private EmployeeInfoServiceImpl employeeInfoServiceImpl;

    @Override
    public boolean append(Department department) {

        // 验证参数是否合法
        valid(department);

        // 验证参数是否唯一
        validExist(department, true);

        return baseMapper.insert(department) != 0;
    }

    @Override
    public boolean remove(String departmentId) {

        // 查询部门下是否有员工
        if (employeeInfoServiceImpl.findByColumn("department_id", departmentId) != 0) {
            throw new ApiException(500, "此部门存在所属员工");
        }

        return baseMapper.deleteById(departmentId) != 0;
    }

    @Override
    public boolean update(Department department) {

        // 验证参数是否合法
        valid(department);

        // 验证参数是否唯一
        validExist(department, false);

        return baseMapper.updateById(department) != 0;
    }

    @Override
    public Department findById(String departmentId) {
        return baseMapper.selectById(departmentId);
    }

    @Override
    public Page<Department> pagingFindAll(Pages pages) {

        Page<Department> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        return baseMapper.selectPage(page, lambdaQueryWrapper(pages));
    }

    @Override
    public List<Department> findAll() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 查询部门信息 wrapper
     *
     * @param pages
     * @return
     */
    private LambdaQueryWrapper<Department> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("departmentName"))) {
            wrapper.like(Department::getDepartmentName, pages.getSearchs().get("departmentName"));
        }

        return wrapper;
    }

    /**
     * 验证参数是否合法
     *
     * @param department
     */
    private void valid(Department department) {

        if (!CommonUtils.isNotEmpty(department.getDepartmentName())) {
            throw new ApiException(500, "部门名称不能为空");
        }
    }

    /**
     * 验证参数是否唯一
     *
     * @param department
     */
    private void validExist(Department department, Boolean flag) {

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

    /**
     * 根据参数获取 Department
     * 用于判断是否重复
     *
     * @param column
     * @param value
     * @return
     */
    private Department findByParam(String column, Object value) {

        QueryWrapper<Department> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectOne(wrapper);
    }
}
