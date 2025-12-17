package com.lhy.campusswap.mapper;

import com.lhy.campusswap.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    void insertUser(User user);
}