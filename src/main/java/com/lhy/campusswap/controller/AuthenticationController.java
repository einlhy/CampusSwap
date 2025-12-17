package com.lhy.campusswap.controller;

import com.lhy.campusswap.common.ResponseResult;
import com.lhy.campusswap.dto.AuthResult;
import com.lhy.campusswap.dto.LoginRequest;
import com.lhy.campusswap.dto.RegisterRequest;
import com.lhy.campusswap.security.AuthUser;
import com.lhy.campusswap.security.TokenBlackListService;
import com.lhy.campusswap.service.impl.AuthServiceImpl;
import com.lhy.campusswap.utils.JwtUtils;
import com.lhy.campusswap.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: LoginController
 * Package: com.lhy.campusswap.controller
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/1 12:38
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor//省去写@Autowired,会自动注入final修饰的属性
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RedisCache redisCache;
    private final TokenBlackListService tokenBlackListService;
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest) {

        // 直接用 identifier 当 username，Spring Security 会自动调用 Provider
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword())
        );

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        //  组装 claims
        Map<String, Object> claims = Map.of(
                "userId", authUser.getUserId(),
                "nickname", authUser.getNickname(),
                "roles", authUser.getRoles(),
                "permissions", authUser.getPermissions()
        );

        //  生成 token（必须有 sub！）
        String token = jwtUtils.generateToken(claims, String.valueOf(authUser.getUserId()));

        // 6. 存 Redis（统一 key + 过期时间）
        redisCache.set("login:" + authUser.getUserId(), authUser, 1000*60*60*2, TimeUnit.MILLISECONDS);

        // 7. 返回统一结构
        AuthResult result = AuthResult.builder()
                .token(token)
                .userId(authUser.getUserId())
                .nickname(authUser.getNickname())
                .roles(authUser.getRoles())
                .permissions(authUser.getPermissions())
                .build();
        return ResponseResult.success("登录成功",result);
    }

    @PostMapping("/logout")
    //@AuthenticationPrincipal用来快速从当前 SecurityContext 中拿到已登录的用户对象。
    public ResponseResult logout(@AuthenticationPrincipal AuthUser authUser, @RequestHeader("Authorization") String token) {
        Long userId = authUser.getUserId();
        // 删除 Redis 中的用户信息
        redisCache.delete("login:" + userId);
        // 将 token 加入黑名单
        token = token.substring(7); // 去掉 "Bearer " 前缀
        long expiration = jwtUtils.getExpirationDateFromToken(token).getTime() - System.currentTimeMillis();
        tokenBlackListService.addToBlacklist(token,expiration);
        return ResponseResult.success("退出成功");
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseResult.success("注册成功");
    }

    @PostMapping("/refresh")
    public ResponseResult refresh(@RequestHeader("Authorization") String token) {
        //TODO 实现刷新token功能
        return null;
    }
}
