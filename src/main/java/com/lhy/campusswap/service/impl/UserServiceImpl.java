package com.lhy.campusswap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.campusswap.common.ResultCode;
import com.lhy.campusswap.entity.User;
import com.lhy.campusswap.exception.BusinessException;
import com.lhy.campusswap.exception.auth.UserNotFoundException;
import com.lhy.campusswap.mapper.UserMapper;
import com.lhy.campusswap.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhy.campusswap.utils.SensitiveDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfo(Long id) {
        User user = userMapper.selectById(id);
        if (Objects.isNull(user) || user.getIsDeleted() == 1) {
            throw new UserNotFoundException(ResultCode.USER_NOT_FOUND.getCode(),"用户不存在");
        }
        return user;
    }

    @Override
    public void updateUserInfo(User user) {
        int isUpdate = userMapper.updateById(user);
        if (isUpdate <= 0) {
            throw new BusinessException(ResultCode.UPDATE_FAILED.getCode(),"更新失败");
        }
    }

    @Override
    public List<User> listUsers() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getIsDeleted, 0); // 使用Lambda表达式
        List<User> users = userMapper.selectList(queryWrapper);
        if (users != null && users.size() > 0) {
            for(User user : users) {
                user.setPassword(null); // 清除密码字段
                user.setPhone(SensitiveDataUtils.desensitizePhone(user.getPhone()));
            }
        }
        return users;
    }


}
