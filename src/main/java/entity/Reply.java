package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复表
 */
@Data
@TableName(value = "reply")
public class Reply implements Serializable {

    @TableId(value = "reply_id")
    private String replyId; //回复id

    @TableField("answer_id")
    private String answerId; //回答id

    private String content; //回复内容

    private String sender; //发送者

    private String receiver; //接收者

    @TableField("create_time")
    private Date createTime; //回复时间

    @TableField("reply_isenable")
    private Integer replyIsenable; //是否禁用：0启用 1禁用
}
