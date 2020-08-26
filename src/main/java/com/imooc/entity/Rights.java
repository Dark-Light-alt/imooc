package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 权限
 */
@Data
@TableName("rights")
public class Rights {

    // 权限 id
    @TableId("rights_id")
    private String rightsId;

    // 权限名称
    @TableField("rights_name")
    private String rightsName;

    // 权限路径：router 名称 或者 update delete findById
    @TableField("rights_path")
    private String rightsPath;

    // 前端图标
    @TableField
    private String icon;

    // 级数 0 1 2
    @TableField("rights_level")
    private Integer rightsLevel;

    // 父级 id
    @TableField("parent_id")
    private String parentId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 子级 count
    @TableField(exist = false)
    private Integer childrenCount;

    // 子级节点
    @TableField(exist = false)
    private List<Rights> children;
}
