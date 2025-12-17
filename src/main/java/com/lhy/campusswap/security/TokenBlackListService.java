package com.lhy.campusswap.security;

import com.lhy.campusswap.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: TokenBlackListService
 * Package: com.lhy.campusswap.security
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/5 0:18
 */
@Service
public class TokenBlackListService {

    @Autowired
    private RedisCache redisCache;

    //将token加入黑名单，expiration单位为秒
    public void addToBlacklist(String token, long expiration) {
        redisCache.set("blacklist:" + token, "invalid", expiration, TimeUnit.MILLISECONDS);
    }

    //检查token是否在黑名单中
    public boolean isTokenBlacklisted(String token) {
        return redisCache.hasKey("blacklist:" + token) ;
    }
}
