package com.lhy.campusswap.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 基于JJWT 0.11.5版本
 */
@Component
public class JwtUtils {

    // 从配置文件中读取密钥和过期时间
    @Value("${jwt.secret:defaultSecretKey2024SpringBoot3Application}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 默认24小时
    private Long expiration;

    // 生成安全的密钥
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成JWT token
     * @param claims 自定义声明
     * @param subject 主题（通常是用户ID或用户名）
     * @return JWT token字符串
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims) // 自定义声明
                .setSubject(subject) // 主题
                .setIssuedAt(now) // 签发时间
                .setExpiration(expiryDate) // 过期时间
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 签名算法和密钥
                .compact();
    }

    /**
     * 生成JWT token（简化版）
     * @param subject 主题
     * @return JWT token字符串
     */
    public String generateToken(String subject) {
        return generateToken(new HashMap<>(), subject);
    }

    /**
     * 从token中解析声明
     * @param token JWT token
     * @return 声明集合
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从token中获取主题（用户信息）
     * @param token JWT token
     * @return 主题内容
     */
    public String getSubjectFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 从token中获取过期时间
     * @param token JWT token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 验证token是否有效
     * @param token JWT token
     * @param subject 要验证的主题
     * @return 是否有效
     */
    public boolean validateToken(String token, String subject) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject().equals(subject) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证token是否有效
     * @param token JWT token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            // token过期
            return false;
        } catch (UnsupportedJwtException e) {
            // 不支持的token
            return false;
        } catch (MalformedJwtException e) {
            // token格式错误
            return false;
        } catch (SecurityException e) {
            // 签名验证失败
            return false;
        } catch (IllegalArgumentException e) {
            // 参数错误
            return false;
        }
    }

    /**
     * 检查token是否过期
     * @param token JWT token
     * @return 是否过期
     */
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 刷新token（更新过期时间）
     * @param token 原token
     * @return 新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return generateToken(claims, claims.getSubject());
    }
}