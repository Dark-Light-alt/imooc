package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * monograph专刊表
 */
@Data
@TableName(value = "monograph")
public class Monograph implements Serializable {

    @TableId(value = "monograph_id")
    private String monographId; //专刊id

    private String cover; //封面

    @TableField("monograph_name")
    private String monographName; //专刊名

    private String highlights; //亮点

    @TableField("monograph_about")
    private String monographAbout; //简介

    private String author; //作者

    private BigDecimal price; //价格

    private BigDecimal discounts; //优惠

    @TableField("offer_end_time")
    private Date offerEndTime; //优惠截止日期

    @TableField("number_of_purchasers")
    private Integer numberOfPurchasers; //购买人数

    @TableField("off_shelf")
    private Integer offShelf; //是否下架 0未下架 1已下架

    @TableField("create_time")
    private Date createTime; //创建时间
}
