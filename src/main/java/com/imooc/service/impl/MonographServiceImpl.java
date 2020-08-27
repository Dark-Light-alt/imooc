package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.MonographDao;
import com.imooc.entity.Monograph;
import com.imooc.exception.ApiException;
import com.imooc.service.MonographService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

@Service
public class MonographServiceImpl extends ServiceImpl<MonographDao, Monograph> implements MonographService {
    /**
     * 分页查询
     *
     * @param pages
     * @return
     */
    @Override
    public Page<Monograph> findAll(Pages pages) {

        Page page = new Page(pages.getCurrentPage(), pages.getPageSize());

        return baseMapper.selectPage(page, this.wrapperFun(pages));
    }

    /**
     * 修改
     *
     * @param monograph
     * @return
     */
    @Override
    public boolean update(Monograph monograph) {
        valid(monograph);
        vaildExists(monograph, false);

        return baseMapper.updateById(monograph) != 0;
    }

    /**
     * 根据主键查询
     *
     * @param monographId
     * @return
     */
    @Override
    public Monograph findById(String monographId) {
        return baseMapper.selectById(monographId);
    }

    /**
     * 专栏下架
     *
     * @param monographId
     * @return
     */
    @Override
    public boolean soldOut(String monographId) {
        LambdaQueryWrapper<Monograph> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Monograph::getMonographId, monographId);

        Monograph monograph = new Monograph();
        monograph.setOffShelf(1);

        return baseMapper.update(monograph, wrapper) != 0;
    }

    /**
     * 添加专栏
     *
     * @param monograph
     * @return
     */
    @Override
    public boolean append(Monograph monograph) {
        //验证字段是否为空
        valid(monograph);

        vaildExists(monograph, true);

        return baseMapper.insert(monograph) != 0;
    }

    /**
     * 分页查询专栏和章节
     * @param pages
     * @return
     */
    @Override
    public Page<Monograph> pageFindMonograph(Pages pages) {
        Page<Monograph> page = new Page<>(pages.getCurrentPage(),pages.getPageSize());

        return page.setRecords(baseMapper.pageFindMonograph(page,this.wrapperFun(pages)));
    }

    /**
     * 模糊查询条件构造器
     * @param pages
     * @return
     */
    public QueryWrapper<Monograph> wrapperFun(Pages pages){
        QueryWrapper<Monograph> wrapper = new QueryWrapper<>();

        //根据关键字查询
        if(CommonUtils.isNotEmpty(pages.getSearchs().get("keyword"))){
            wrapper.like("monograph_name",pages.getSearchs().get("keyword"))
                    .or()
                    .like("highlights",pages.getSearchs().get("keyword"))
                    .or()
                    .like("monograph_about",pages.getSearchs().get("keyword"))
                    .or()
                    .like("author",pages.getSearchs().get("keyword"));
        }

        wrapper.orderByAsc("create_Time");

        return wrapper;
    }


    /**
     * 验证参数不能为空
     *
     * @param monograph
     */
    private void valid(Monograph monograph) {
        System.out.println(monograph);
        if (!CommonUtils.isNotEmpty(monograph.getMonographName())) {
            throw new ApiException(500, "专刊名不能为空");
        }
        if (!CommonUtils.isNotEmpty(monograph.getCover())) {
            throw new ApiException(500, "必须上传背景图");
        }
        if (!CommonUtils.isNotEmpty(monograph.getHighlights())) {
            throw new ApiException(500, "亮点不能为空");
        }
        if (!CommonUtils.isNotEmpty(monograph.getMonographAbout())) {
            throw new ApiException(500, "简介不能为空");
        }
    }

    /**
     * 判断字段是否重复
     *
     * @param monograph
     * @param flag
     */
    private void vaildExists(Monograph monograph, boolean flag) {
        //判断专刊名是否重复
        LambdaQueryWrapper<Monograph> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Monograph::getMonographName, monograph.getMonographName());
        Monograph one = baseMapper.selectOne(wrapper);

        if (flag) {
            if (null != one) {
                throw new ApiException(500, "专刊名已存在!");
            }
        }

        if (null != one && !one.getMonographId().equals(monograph.getMonographId())) {
            throw new ApiException(500, "专刊名已存在!");
        }

    }


}
