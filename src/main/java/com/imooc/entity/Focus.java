package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注
 */
@Data
@TableName("focus")
public class Focus implements Serializable {

    // 关注 id
    @TableId("focus_id")
    private String focusId;

    // 粉丝
    @TableField
    private String fans;

    // 关注者
    @TableField
    private String following;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
