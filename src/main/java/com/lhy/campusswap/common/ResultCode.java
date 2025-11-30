package com.lhy.campusswap.common;

/**
 * ClassName: ResultCode
 * Package: com.lhy.campusswap.common
 * Description:
 *
 * @Author LHY
 * @Create 2025/11/27 23:23
 */
/**
 * 响应状态码枚举
 */
public enum ResultCode {
    // 成功状态码
    SUCCESS(200, "操作成功"),

    // 客户端错误状态码
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权或token已过期"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // 服务器错误状态码
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    // 业务状态码（可根据业务需求扩展）
    BUSINESS_ERROR(1000, "业务异常"),
    VALIDATION_ERROR(1001, "参数校验失败"),
    DUPLICATE_ERROR(1002, "数据重复");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
