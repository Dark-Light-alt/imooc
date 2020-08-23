package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 职位权限桥梁
 */
@Data
@TableName("position_permission_bridge")
public class PositionPermissionBridge {

    // 职位 id
    @TableField("position_id")
    private String positionId;

    // 权限 id
    @TableField("permission_id")
    private String permissionId;
}
