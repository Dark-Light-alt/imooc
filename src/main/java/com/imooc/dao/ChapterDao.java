package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterDao extends BaseMapper<Chapter> {

}
