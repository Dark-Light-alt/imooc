package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频表
 */
@Data
@TableName(value = "video")
public class Video implements Serializable {

    @TableId(value = "video_id")
    private String videoId; //视频id

    @TableField("video_name")
    private String videoName; //视频名

    @TableField("video_url")
    private String videoUrl; //视频路径

    @TableField("try_and_see")
    private Integer tryAndSee; //是否试看 0否 1是

    private Integer duration; //视频时长

    @TableField("chapter_id")
    private String chapterId; //所属章节q

    @TableField("create_time")
    private Date createTime; //创建时间

    @TableField("video_isenable")
    private Integer videoIsenable; //是否禁用：0正常 1视频被禁用，比如举报通过
}
