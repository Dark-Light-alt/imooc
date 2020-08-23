package com.imooc.entity;

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
@TableName("video")
public class Video implements Serializable {

    // 视频 id
    @TableId("video_id")
    private String videoId;

    // 视频名
    @TableField("video_name")
    private String videoName;

    // 视频路径
    @TableField("video_url")
    private String videoUrl;

    // 是否试看 0否 1是
    @TableField("try_and_see")
    private Integer tryAndSee;

    // 视频时长
    private Integer duration;

    // 所属章节
    @TableField("chapter_id")
    private String chapterId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 是否禁用：0正常 1视频被禁用，比如举报通过
    @TableField("video_isenable")
    private Integer videoIsenable;
}
