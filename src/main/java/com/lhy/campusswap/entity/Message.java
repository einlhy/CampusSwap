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
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @TableId("id")
    private Long id;

    /**
     * 发送者
     */
    @TableField("from_user_id")
    private Long fromUserId;

    /**
     * 接收者
     */
    @TableField("to_user_id")
    private Long toUserId;

    @TableField("goods_id")
    private Long goodsId;

    /**
     * 文字或图片URL
     */
    @TableField("content")
    private String content;

    /**
     * 0文字 1图片 2系统消息
     */
    @TableField("type")
    private Byte type;

    /**
     * 0未读 1已读
     */
    @TableField("is_read")
    private Boolean isRead;

    @TableField("create_time")
    private LocalDateTime createTime;
}
