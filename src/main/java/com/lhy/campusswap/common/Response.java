package com.lhy.campusswap.common;

/**
 * ClassName: Response
 * Package: com.lhy.campusswap.common
 * Description:
 *
 * @Author LHY
 * @Create 2025/11/27 23:24
 */
import java.io.Serializable;

/**
 * 统一API响应结果封装
 * @param <T> 数据类型
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    // 核心字段
    private Integer code;
    private String message;
    private T data;

    // 扩展字段（用于监控和调试）
    private Long timestamp;
    private String path;
    private String traceId;

    // 构造方法
    public Response() {
        this.timestamp = System.currentTimeMillis();
    }

    public Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ========== 成功响应方法 ==========

    public static <T> Response<T> success() {
        return new Response<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    // ========== 失败响应方法 ==========

    public static <T> Response<T> error() {
        return error(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Response<T> error(ResultCode resultCode) {
        return error(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> Response<T> error(String message) {
        return error(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static <T> Response<T> error(Integer code, String message) {
        return new Response<>(code, message, null);
    }

    public static <T> Response<T> error(ResultCode resultCode, String customMessage) {
        return error(resultCode.getCode(), customMessage);
    }

    // ========== 链式调用方法 ==========

    public Response<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Response<T> message(String message) {
        this.message = message;
        return this;
    }

    public Response<T> data(T data) {
        this.data = data;
        return this;
    }

    public Response<T> path(String path) {
        this.path = path;
        return this;
    }

    public Response<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    // ========== 判断方法 ==========

    public boolean isSuccess() {
        return this.code != null && this.code.equals(ResultCode.SUCCESS.getCode());
    }

    // ========== Getter 和 Setter ==========

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                ", path='" + path + '\'' +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
