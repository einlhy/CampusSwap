package com.lhy.campusswap.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@Getter
@Setter
@TableName("report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 举报ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 举报人
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 被举报商品
     */
    @TableField("goods_id")
    private Long goodsId;

    /**
     * 举报原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 0待处理 1已处理 2忽略
     */
    @TableField("status")
    private Byte status;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @TableField("create_time")
    private LocalDateTime createTime;
}
