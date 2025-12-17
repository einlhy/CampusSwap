package com.lhy.campusswap.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhy.campusswap.common.ResponseResult;
import com.lhy.campusswap.common.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClassName: AccessDeniedHandlerImpl
 * Package: com.lhy.campusswap.handler
 * Description: 自定义权限不足处理器
 * URL 级别的访问拒绝 - 由 AccessDeniedHandler 处理
 *
 * @Author LHY
 * @Create 2025/12/6 0:16
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应内容类型为JSON
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        // 构建统一响应结果
        ResponseResult<Void> result = ResponseResult.error(ResultCode.FORBIDDEN, "权限不足，无法访问该资源");
        
        // 将结果转换为JSON并写入响应
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}