package com.lhy.campusswap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lhy.campusswap.common.ResultCode;
import com.lhy.campusswap.dto.RegisterRequest;
import com.lhy.campusswap.entity.User;
import com.lhy.campusswap.exception.BusinessException;
import com.lhy.campusswap.mapper.UserMapper;
import com.lhy.campusswap.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ClassName: AuthServiceImpl
 * Package: com.lhy.campusswap.service.impl
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/6 0:45
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void register(RegisterRequest registerRequest) {
        String phone = registerRequest.getPhone();
        String username = registerRequest.getUsername();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone)
                .or()
                .eq(User::getUsername, username)
        );
        if (user != null) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"用户名已存在");
        }
        // 进行注册逻辑，例如保存用户到数据库
        User newUser = new User();
        newUser.setPhone(registerRequest.getPhone());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setNickname(registerRequest.getNickname());
        newUser.setSchool(registerRequest.getSchool());
        newUser.setStudentId(registerRequest.getStudentId());
        userMapper.insert(newUser);
    }
}
