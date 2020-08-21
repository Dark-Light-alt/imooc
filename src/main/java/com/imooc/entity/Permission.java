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

    @TableId("permission_id")
    private String permissionId;

    @TableField("permission_name")
    private String permissionName;

    @TableField("request_url")
    private String requestUrl;

    @TableField
    private String icon;

    @TableField("parent_id")
    private String parentId;
}
