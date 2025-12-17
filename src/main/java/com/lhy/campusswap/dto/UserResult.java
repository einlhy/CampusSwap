package com.lhy.campusswap.dto;

import lombok.Data;

/**
 * ClassName: UserResult
 * Package: com.lhy.campusswap.dto
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/7 13:26
 */
@Data
public class UserResult {
    private Long id;
    private String phone;
    private String username;
    private String nickname;
    private String avatar;
    private String school;
}
