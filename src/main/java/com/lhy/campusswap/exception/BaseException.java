package com.lhy.campusswap.exception;

// 1. 所有自定义异常的基类（必须继承 RuntimeException）
public abstract class BaseException extends RuntimeException {
    private final int code;
    private final Object data;  // 可选：携带额外数据，如无效的字段名等

    public BaseException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public BaseException(int code, String message) {
        this(code, message, null);
    }

    public int getCode() { return code; }
    public Object getData() { return data; }
}