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
@TableName("trade_comment")
public class TradeComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评价ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 评价人
     */
    @TableField("from_user_id")
    private Long fromUserId;

    /**
     * 被评价人
     */
    @TableField("to_user_id")
    private Long toUserId;

    /**
     * 评分1~5星
     */
    @TableField("score")
    private Byte score;

    /**
     * 评价内容
     */
    @TableField("content")
    private String content;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @TableField("create_time")
    private LocalDateTime createTime;
}
