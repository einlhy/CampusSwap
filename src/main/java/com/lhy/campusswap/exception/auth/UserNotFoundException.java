package com.lhy.campusswap.exception.auth;

import com.lhy.campusswap.exception.BaseException;
import com.lhy.campusswap.exception.BusinessException;

/**
 * ClassName: UserNotFoundException
 * Package: com.lhy.campusswap.exception.auth
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/7 13:12
 */

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(int code, String message, Object data) {
        super(code, message, data);
    }

    public UserNotFoundException(int code, String message) {
        super(code, message);
    }
}
