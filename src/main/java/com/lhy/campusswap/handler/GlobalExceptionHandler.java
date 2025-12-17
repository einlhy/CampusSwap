package com.lhy.campusswap.handler;

import com.lhy.campusswap.common.ResponseResult;
import com.lhy.campusswap.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 * 全局异常处理器就是为了避免到处写 try-catch，所以应该让异常自然抛出，然后在这里进行统一处理和包装响应。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 自定义业务异常（推荐自己定义）
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseResult<String>> handleBusinessException(BusinessException e) {
        ResponseResult<String> response = ResponseResult.error(e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response); // 业务异常一般返回200 + 自定义code
    }

    // 参数校验异常（@Valid + Bean Validation）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Map<String, String>>> handleValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        ResponseResult<Map<String, String>> response = ResponseResult.error(400, "参数校验失败", errors);
        return ResponseEntity.badRequest().body(response);
    }

    // @Validated + Controller 方法参数校验（路径变量、请求参数）
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ResponseResult<String>> handleConstraintViolation(ConstraintViolationException e) {
//        String message = e.getConstraintViolations().stream()
//                .map(ConstraintViolation::getMessage)
//                .collect(Collectors.joining("; "));
//        ResponseResult<String> response = ResponseResult.error(400, message);
//        return ResponseEntity.badRequest().body(response);
//    }

    // 空指针等运行时异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseResult<String>> handleRuntimeException(RuntimeException e) {
        // 实际项目中建议记录日志
        e.printStackTrace();
        ResponseResult<String> response = ResponseResult.error(500, "系统异常，请稍后再试");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 其他未捕获异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<String>> handleException(Exception e) {
        e.printStackTrace();
        ResponseResult<String> response = ResponseResult.error(500, "未知异常");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}