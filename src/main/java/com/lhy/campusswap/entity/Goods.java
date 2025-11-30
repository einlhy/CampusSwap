package com.lhy.campusswap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@Getter
@Setter
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId("id")
    private Long id;

    /**
     * 发布者ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 价格（分）
     */
    @TableField("price")
    private Long price;

    /**
     * 图片JSON数组
     */
    @TableField("images")
    private String images;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 新旧程度 0-10
     */
    @TableField("degree")
    private Byte degree;

    /**
     * 0在售 1已售 2下架 3违规
     */
    @TableField("status")
    private Byte status;

    /**
     * 浏览量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 想要人数
     */
    @TableField("want_count")
    private Integer wantCount;

    /**
     * 是否推荐
     */
    @TableField("is_recommend")
    private Boolean isRecommend;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
