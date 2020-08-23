package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程问题表
 */
@Data
@TableName(value = "question")
public class Question implements Serializable {

    @TableId(value = "question_id")
    private String questionId; //问题id

    private String title; //标题

    private String content; //内容

    private String publisher; //发布人

    @TableField("course_id")
    private String courseId; //课程id

    @TableField("video_id")
    private String videoId; //课程视频id

    private Integer pageviews; //浏览量

    private Integer likes; //点赞量

    @TableField("create_time")
    private Date createTime; //发布时间

    @TableField("question_isenable")
    private Integer questionIsenable; //是否禁用：0启用 1禁用

    private Integer issolve; //是否解决：0未解决 1已解决
}
