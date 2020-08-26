package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("position_rights_bridge")
public class PositionRightsBridge {

    @TableField("position_id")
    private String positionId;

    @TableField("rights_id")
    private String rightsId;
}
