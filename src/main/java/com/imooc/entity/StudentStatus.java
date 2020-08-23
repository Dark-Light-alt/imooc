package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学籍表
 */
@Data
@TableName("student_status")
public class StudentStatus implements Serializable {

    @TableId("student_status_id")
    private String studentStatusId;	//主键	学籍id

    private String name;                //姓名

    private String idcard;              //身份证号

    @TableField("student_id")
    private String studentId;          //学号

    private String colleges;		    //院校名

    @TableField("enrollment_date")
    private Date enrollmentDate;       //入学日期

    @TableField("graduation_date")
    private Date graduationDate;       //毕业日期


}
