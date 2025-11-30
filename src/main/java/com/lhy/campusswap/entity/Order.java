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
 * 订单表
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@Getter
@Setter
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID（Snowflake）
     */
    @TableId("id")
    private Long id;

    @TableField("goods_id")
    private Long goodsId;

    /**
     * 买家ID
     */
    @TableField("buyer_id")
    private Long buyerId;

    @TableField("seller_id")
    private Long sellerId;

    /**
     * 成交价（分）
     */
    @TableField("price")
    private Long price;

    /**
     * 0待支付 1已支付 2已发货 3已完成 4已取消 5退款中 6已退款
     */
    @TableField("status")
    private Byte status;

    /**
     * 1微信 2支付宝
     */
    @TableField("pay_type")
    private Byte payType;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 交易完成时间
     */
    @TableField("finish_time")
    private LocalDateTime finishTime;

    @TableField("create_time")
    private LocalDateTime createTime;
}
