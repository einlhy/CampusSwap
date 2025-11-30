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
@TableName("favorite")
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private Long userId;

    @TableField("goods_id")
    private Long goodsId;

    /**
     * 收藏时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
