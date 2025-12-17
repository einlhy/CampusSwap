package com.lhy.campusswap.controller;

import com.lhy.campusswap.common.ResponseResult;
import com.lhy.campusswap.dto.UserResult;
import com.lhy.campusswap.entity.User;
import com.lhy.campusswap.service.UserService;
import com.lhy.campusswap.utils.SensitiveDataUtils;
import jakarta.annotation.security.RolesAllowed;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    @RolesAllowed({"super_admin"})
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('super_admin')")
    public ResponseResult getUserById(@PathVariable("id") Long id) {
        // 获取指定ID的用户信息
        User user = userService.getUserInfo(id);
        UserResult userResult = new UserResult();
        userResult.setId(user.getId());
        userResult.setPhone(SensitiveDataUtils.desensitizePhone(user.getPhone()));
        userResult.setUsername(user.getUsername());
        userResult.setNickname(user.getNickname());
        userResult.setAvatar(user.getAvatar());
        userResult.setSchool(user.getSchool());
        return ResponseResult.success(userResult);
    }

    @PutMapping("/update/userinfo")
    public ResponseResult updateProfile(@RequestBody User user) {
        // 更新用户个人信息
        userService.updateUserInfo(user);
        return ResponseResult.success();
    }



    @GetMapping("/list")
    public ResponseResult listUsers() {
        // TODO 获取用户列表（管理员），支持分页和过滤
        userService.listUsers();
        return null;
    }

    @PutMapping("/update/status")
    public ResponseResult updateUserStatus() {
        // TODO 更新用户状态（管理员）
        return null;
    }
}