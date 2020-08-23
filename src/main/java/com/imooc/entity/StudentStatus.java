package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学籍
 */
@Data
@TableName("student_status")
public class StudentStatus implements Serializable {

    // 学籍 id
    @TableId("student_status_id")
    private String studentStatusId;

    // 姓名
    @TableField
    private String name;

    // 身份证号
    @TableField
    private String idcard;

    // 学号
    @TableField("student_id")
    private String studentId;

    // 院校名
    @TableField
    private String colleges;

    // 入学日期
    @TableField("enrollment_date")
    private Date enrollmentDate;

    // 毕业日期
    @TableField("graduation_date")
    private Date graduationDate;
}
