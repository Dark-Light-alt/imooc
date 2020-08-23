package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 */
@Data
@TableName(value = "comments")
public class Comments implements Serializable {

    @TableId(value = "comment_id")
    private String commentId; //评论id

    private String content; //评论内容

    private Integer likes; //点赞量

    private String author; //发布人

    @TableField("comment_be_resource")
    private String commentBeResource; //评论源

    @TableField("create_time")
    private Date createTime; //发布时间

    @TableField("comment_status")
    private Integer commentStatus; //状态：0启用 1禁用
}
