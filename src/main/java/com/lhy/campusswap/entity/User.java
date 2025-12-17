package com.lhy.campusswap.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 * // 2025 年终极正确姿势（三件套，缺一不可）
 * 1. @TableName(autoResultMap = true)          // 必须！
 * 2. @TableField(typeHandler = JacksonTypeHandler.class)  // 必须！
 * 3. 字段初始化 private List<String> xxx = new ArrayList<>(); // 推荐！
 *
 * @author LHY
 * @since 2025-11-25
 */
@Getter
@Setter
@TableName(value = "user", autoResultMap = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID（Snowflake）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码（BCrypt）
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像URL
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 学校名称（认证后）
     */
    @TableField("school")
    private String school;

    /**
     * 学号（认证后）
     */
    @TableField("student_id")
    private String studentId;

    /**
     * 信用分
     */
    @TableField("credit_score")
    private Integer creditScore;

    /**
     * 0正常 1禁用 2未认证
     */
    @TableField("status")
    private Byte status;

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

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 角色列表
     * typeHandler = JacksonTypeHandler.class 将JSON数组转换为List<String>, 反之亦然
     */
    @TableField(value = "roles",
            typeHandler = JacksonTypeHandler.class,
            fill = FieldFill.INSERT)
    private List<String> roles ;

    /**
     * 权限列表
     */
    @TableField(value = "permissions",
            typeHandler = JacksonTypeHandler.class,
            fill = FieldFill.INSERT)
    private List<String> permissions ;
}
