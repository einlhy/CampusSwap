package com.lhy.campusswap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableField("user1_id")
    private Long user1Id;

    /**
     * 较大的用户ID
     */
    @TableField("user2_id")
    private Long user2Id;

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
     * 针对user2的未读数
     */
    @TableField("unread_count")
    private Integer unreadCount;
}
