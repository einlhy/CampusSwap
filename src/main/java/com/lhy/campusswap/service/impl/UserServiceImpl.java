package com.lhy.campusswap.service.impl;

import com.lhy.campusswap.entity.User;
import com.lhy.campusswap.mapper.UserMapper;
import com.lhy.campusswap.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
