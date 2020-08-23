package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 权限
 */
@Data
@TableName("permission")
public class Permission {

    // 权限 id
    @TableId("permission_id")
    private String permissionId;

    // 权限名
    @TableField("permission_name")
    private String permissionName;

    // 请求路径
    @TableField("request_url")
    private String requestUrl;

    // 前端图标
    @TableField
    private String icon;

    // 父级 id
    @TableField("parent_id")
    private String parentId;
}
