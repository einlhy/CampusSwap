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
@TableName("conversation")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 较小的用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 较大的用户ID
     */
    @TableField("target_user_id")
    private Long targetUserId;

    /**
     * 关联商品
     */
    @TableField("goods_id")
    private Long goodsId;

    /**
     * 最后一条消息预览
     */
    @TableField("last_message")
    private String lastMessage;

    /**
     * 最后消息时间
     */
    @TableField("last_time")
    private LocalDateTime lastTime;

    /**
     * 未读数
     */
    @TableField("unread_count")
    private Integer unreadCount;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 版本号
     */
    @TableField("version")
    @Version
    private Integer version;
}
