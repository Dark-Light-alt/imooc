package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.PositionDao;
import com.imooc.entity.Position;
import com.imooc.exception.ApiException;
import com.imooc.service.PositionService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionDao, Position> implements PositionService {

    @Resource
    private EmployeeInfoServiceImpl employeeInfoServiceImpl;

    @Override
    public boolean append(Position position) {

        // 验证参数是否合法
        valid(position);

        // 验证参数是否唯一
        validExist(position, true);

        return baseMapper.insert(position) != 0;
    }

    @Override
    public boolean remove(String positionId) {

        if (employeeInfoServiceImpl.findByColumn("position_id", positionId) != 0) {
            throw new ApiException(500, "存在所属员工");
        }

        return baseMapper.deleteById(positionId) != 0;
    }

    @Override
    public boolean update(Position position) {

        // 验证参数是否合法
        valid(position);

        // 验证参数是否唯一
        validExist(position, false);

        return baseMapper.updateById(position) != 0;
    }

    @Override
    public Position findById(String positionId) {
        return baseMapper.selectById(positionId);
    }

    @Override
    public Page<Position> pagingFindAll(Pages pages) {

        Page page = new Page(pages.getCurrentPage(), pages.getPageSize());

        return baseMapper.selectPage(page, lambdaQueryWrapper(pages));
    }

    @Override
    public List<Position> findAll() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 查询职位信息 wrapper
     *
     * @param pages
     * @return
     */
    private LambdaQueryWrapper<Position> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<Position> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("positionName"))) {
            wrapper.like(Position::getPositionName, pages.getSearchs().get("positionName"));
        }

        return wrapper;
    }

    /**
     * 验证参数是否合法
     *
     * @param position
     */
    private void valid(Position position) {

        if (!CommonUtils.isNotEmpty(position.getPositionName())) {
            throw new ApiException(500, "职位名称不能为空");
        }
    }

    /**
     * 验证参数是否唯一
     *
     * @param position
     */
    private void validExist(Position position, Boolean flag) {

        Position positionName = findByParam("position_name", position.getPositionName());

        if (flag) {
            if (null != positionName) {
                throw new ApiException(500, "职位名称已存在");
            }
        }

        if (null != positionName && !positionName.getPositionId().equals(position.getPositionId())) {
            throw new ApiException(500, "职位名称已存在");
        }
    }

    /**
     * 根据参数获取 Position
     * 用于判断是否重复
     *
     * @param column
     * @param value
     * @return
     */
    private Position findByParam(String column, Object value) {

        QueryWrapper<Position> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectOne(wrapper);
    }
}
