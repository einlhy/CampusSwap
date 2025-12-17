package com.lhy.campusswap.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * ClassName: RegisterRequest
 * Package: com.lhy.campusswap.dto
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/4 22:50
 */
@Data
public class RegisterRequest {
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "Invalid phone number format")
    private String phone;
    private String username;
    private String password;
    private String nickname;
    private String school;
    private String studentId;

}