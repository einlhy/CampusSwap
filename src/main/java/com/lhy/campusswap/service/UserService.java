package com.lhy.campusswap.service;

import com.lhy.campusswap.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
public interface UserService extends IService<User> {

    User getUserInfo(Long id);

    void updateUserInfo(User user);

    List<User> listUsers();
}
