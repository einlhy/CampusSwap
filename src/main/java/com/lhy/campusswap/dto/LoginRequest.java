package com.lhy.campusswap.dto;

import lombok.Data;

/**
 * ClassName: LoginRequest
 * Package: com.lhy.campusswap.dto
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/3 11:55
 */
@Data
public class LoginRequest {

    private String identifier;  // 手机号 或 用户名，二选一
    private String password;
}
