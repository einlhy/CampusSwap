package com.lhy.campusswap.exception;

// 2. 业务异常（最常用，返回 200 + 自定义 code）
public class BusinessException extends BaseException {
    public BusinessException(int code, String message) {
        super(code, message);
    }
    public BusinessException(int code, String message, Object data) {
        super(code, message, data);
    }
}