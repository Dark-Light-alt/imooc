package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料表
 */
@Data
@TableName(value = "data")
public class Datas implements Serializable {

    @TableId(value = "data_id")
    private String dataId; //课程资料id

    @TableField("data_name")
    private String dataName; //课程资料名

    @TableField("data_url")
    private String dataUrl; //课程资料路径

    @TableField("course_id")
    private String courseId; //课程id

    @TableField("create_time")
    private Date createTime; //创建时间
}
