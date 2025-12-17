package com.lhy.campusswap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AuthResult
 * Package: com.lhy.campusswap.dto
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/2 0:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private Long userId;
    private String nickname;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

}