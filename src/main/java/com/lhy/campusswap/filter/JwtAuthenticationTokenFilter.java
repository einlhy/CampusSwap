package com.lhy.campusswap.filter;

import com.lhy.campusswap.security.AuthUser;
import com.lhy.campusswap.security.TokenBlackListService;
import com.lhy.campusswap.utils.JwtUtils;
import com.lhy.campusswap.utils.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ClassName: JwtAuthenticationFilter
 * Package: com.lhy.campusswap.common.filter
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/2 16:02
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TokenBlackListService tokenBlackListService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("Authorization");
        
        // 验证token是否存在且以Bearer开头
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7);
                if (tokenBlackListService.isTokenBlacklisted(token)) {
                    throw new RuntimeException("Token在黑名单中");
                }
                // 验证token的有效性
                if (jwtUtils.validateToken(token)) {
                    // 从token中获取subject（用户ID）
                    String subject = jwtUtils.getSubjectFromToken(token);
                    // 从Redis中获取用户信息
                    String redisKey = "login:" + subject;
                    AuthUser authUser = redisCache.get(redisKey);
                    
                    // 即使token有效，如果Redis中没有用户信息，也应该允许继续执行
                    // 这样未登录用户仍然可以访问公开接口
                    if (authUser == null) {
                        throw new RuntimeException("用户未登录");
                    }
                    if (authUser != null) {
                        // 构建Authentication对象
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                authUser, null, authUser.getAuthorities());
                        
                        // 设置到SecurityContextHolder中
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                // 出现异常时继续执行过滤器链（匿名访问）
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 执行过滤器链
        filterChain.doFilter(request, response);
    }
}