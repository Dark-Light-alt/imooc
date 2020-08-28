package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ArticleDao;
import com.imooc.dao.ChapterDao;
import com.imooc.dao.MonographDao;
import com.imooc.entity.Article;
import com.imooc.entity.Chapter;
import com.imooc.entity.Monograph;
import com.imooc.exception.ApiException;
import com.imooc.service.MonographService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MonographServiceImpl extends ServiceImpl<MonographDao, Monograph> implements MonographService {
    @Resource
    ChapterDao chapterDao;

    @Resource
    ArticleDao articleDao;

    /**
     * 分页查询用户的所有专刊
     *
     * @param pages
     * @return
     */
    @Override
    public Page<Monograph> findAllByEmployeeId(Pages pages,String employeeId) {

        Page page = new Page(pages.getCurrentPage(), pages.getPageSize());

        QueryWrapper<Monograph> wrapper = new QueryWrapper<>();

        //根据作者(用户id)查询
        wrapper.eq("author",employeeId);

        //根据关键字查询
        if(CommonUtils.isNotEmpty(pages.getSearchs().get("keyword"))){
            wrapper.and(
                w -> w.like("monograph_name",pages.getSearchs().get("keyword"))
                        .or()
                        .like("highlights",pages.getSearchs().get("keyword"))
                        .or()
                        .like("monograph_about",pages.getSearchs().get("keyword"))
                        .or()
                        .like("author",pages.getSearchs().get("keyword"))
            );
        }

        wrapper.orderByAsc("create_Time");

        return baseMapper.selectPage(page, wrapper);
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
     * 修改专栏状态
     * @param monographId
     * @param status
     * @return
     */
    @Override
    public boolean updateOffShelf(String monographId,Integer status) {
        LambdaQueryWrapper<Monograph> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Monograph::getMonographId, monographId);

        Monograph monograph = new Monograph();
        monograph.setOffShelf(status);

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
     * 根据状态分页关联查询专栏和作者
     * @param pages
     * @return
     */
    @Override
    public Page<Monograph> pageFindMonographAuthor(Pages pages) {
        Page<Monograph> page = new Page<>(pages.getCurrentPage(),pages.getPageSize());

        QueryWrapper<Monograph> wrapper = new QueryWrapper<>();

        wrapper.eq("off_shelf",1)
                .or()
                .eq("off_shelf",2)
                .or()
                .eq("off_shelf",3);
        //根据关键字查询
        if(CommonUtils.isNotEmpty(pages.getSearchs().get("keyword"))){
            wrapper.and(
                w->
                    wrapper.like("monograph_name",pages.getSearchs().get("keyword"))
                            .or()
                            .like("highlights",pages.getSearchs().get("keyword"))
                            .or()
                            .like("monograph_about",pages.getSearchs().get("keyword"))
                            .or()
                            .like("author",pages.getSearchs().get("keyword"))
                );
        }

        wrapper.orderByAsc("create_Time");

        return page.setRecords(baseMapper.pageFindMonographAuthor(page,wrapper));
    }

    /**
     * 删除专栏
     * @param monographId
     * @return
     */
    @Override
    public int delete(String monographId) {
        //查询专栏下是否有章节
        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getChapterResource,monographId);

        List<Chapter> chapters = chapterDao.selectList(wrapper);

        //删除专栏条数
        int num=0;

        //有章节 先删除章节
        if(chapters.size()>0){

            LambdaQueryWrapper<Article> wrapper2 = new LambdaQueryWrapper<>();
            //有章节
            //循环章节
            for(Chapter chapter:chapters){
                //查询章节下是否有文章
                wrapper2.eq(Article::getChapterId, chapter.getChapterId());

                List<Article> articles = articleDao.selectList(wrapper2);

                if(articles.size()>0){
                    //有文章先删除文章
                   articleDao.delete(wrapper2);
                }
                //删除章节
                chapterDao.delete(wrapper);
            }

        }

        //再删除专栏
        num = baseMapper.deleteById(monographId);
        return num;
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
