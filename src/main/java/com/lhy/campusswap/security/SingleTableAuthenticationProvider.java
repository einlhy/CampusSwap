package com.lhy.campusswap.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lhy.campusswap.entity.User;
import com.lhy.campusswap.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * ClassName: MultiUserAuthenticationProvider
 * Package: com.lhy.campusswap.security
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/3 13:45
 */
@Component
@RequiredArgsConstructor
public class SingleTableAuthenticationProvider implements AuthenticationProvider {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /**
         *  返回的是 UsernamePasswordAuthenticationToken 的第一个参数，
         *  也就是构造 token 时传进去的"用户名"（在 Spring Security 里统一叫 principal）。
         */
        String identifier = authentication.getName();        // 就是 identifier
        String rawPassword = (String) authentication.getCredentials();

        // 查询状态为正常(0)或未认证(2)的用户，禁止状态为禁用(1)的用户登录
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .in(User::getStatus, 0, 2)  // 查找正常和未认证的账号
                .and(wrapper -> wrapper
                        .eq(User::getPhone, identifier)
                        .or()
                        .eq(User::getUsername, identifier))
                .last("LIMIT 1"));

        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("手机号/用户名或密码错误");
        }

        AuthUser authUser = new AuthUser(user);
        return new UsernamePasswordAuthenticationToken(
                authUser, null, authUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}