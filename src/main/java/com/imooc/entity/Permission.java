package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 *权限
 */
@Data
@TableName(value = "permission ")
public class Permission {
    @TableId
    @TableField("permission_id")
    private String permissionId;

    @TableField("permission_name")
    private String permissionName;

    @TableField("request_url")
    private String requestUrl;

    @TableField("icon")
    private String icon;

    @TableField("permission_id")
    private String parentId;
}
