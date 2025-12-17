package com.lhy.campusswap.service;

import com.lhy.campusswap.dto.AuthResult;
import com.lhy.campusswap.dto.RegisterRequest;

/**
 * ClassName: AuthService
 * Package: com.lhy.campusswap.service
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/6 0:40
 */
public interface AuthService {
    void register(RegisterRequest registerRequest);
}
