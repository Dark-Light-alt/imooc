package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 回答表
 */
@Data
@TableName(value = "answer")
public class Answer implements Serializable {

    @TableId(value = "answer_id")
    private String answerId; //回答id

    private String content; //内容

    private String publisher; //发布人

    @TableField("question_id")
    private String questionId; //问题id

    @TableField("create_time")
    private Date createTime; //回答时间

    private Integer likes; //点赞量

    @TableField("answer_isenable")
    private Integer answerIsenable; //是否禁用：0启用 1禁用

    private Integer isbest; //是否是最佳回答：0否 1是
}
