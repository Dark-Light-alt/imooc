package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.DatasDao;
import com.imooc.entity.Datas;
import com.imooc.exception.ApiException;
import com.imooc.service.DatasService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资料服务实现
 */
@Service
public class DatasServiceImpl extends ServiceImpl<DatasDao, Datas> implements DatasService {

    /**
     * 资料添加
     *
     * @param datas
     * @return
     */
    public boolean append(Datas datas) {

        vaild(datas);

        return baseMapper.insert(datas) != 0;
    }

    /**
     * 根据资料 id 进行删除
     *
     * @param datasId 资料 id
     * @return
     */
    public boolean remove(String datasId) {
        return baseMapper.deleteById(datasId) != 0;
    }

    /**
     * 根据课程 id 进行删除
     */
    public boolean removeByCourseId(String courseId) {

        LambdaQueryWrapper<Datas> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Datas::getCourseId, courseId);

        return baseMapper.delete(wrapper) != 0;
    }

    /**
     * 修改资料信息
     *
     * @param datas
     * @return
     */
    public boolean update(Datas datas) {

        vaild(datas);

        return baseMapper.updateById(datas) != 0;
    }

    /**
     * 根据资料 id 进行查询
     *
     * @param dataId
     * @return
     */
    public Datas findById(String dataId) {
        return baseMapper.selectById(dataId);
    }

    /**
     * 查询某个课程下的所有资料
     *
     * @param courseId
     * @return
     */
    @Override
    public Page<Datas> findAllByCourseId(Pages pages, String courseId) {

        Page<Datas> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Datas> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Datas::getCourseId, courseId);

        String dataName = pages.getSearchs().get("dataName");

        if (CommonUtils.isNotEmpty(dataName)) {
            wrapper.like(Datas::getDataName, dataName);
        }

        wrapper.orderByAsc(Datas::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }

    /**
     * 查询某个课程下的所有资料
     *
     * @param courseId
     * @return
     */
    @Override
    public List<Datas> findAllByCourseId(String courseId) {

        LambdaQueryWrapper<Datas> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Datas::getCourseId, courseId);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 校验
     *
     * @param datas
     */
    private void vaild(Datas datas) {
        if (!CommonUtils.isNotEmpty(datas.getDataName())) {
            throw new ApiException(500, "资料名称不能为空");
        }
        if (!CommonUtils.isNotEmpty(datas.getDataUrl())) {
            throw new ApiException(500, "资料文件必须上传");
        }
    }
}
