package com.lhy.campusswap.controller;

import com.lhy.campusswap.common.ResponseResult;
import com.lhy.campusswap.common.ResultCode;
import com.lhy.campusswap.security.AuthUser;
import com.lhy.campusswap.service.impl.CommonServiceImpl;
import com.lhy.campusswap.utils.aliyun.oss.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * ClassName: CommonController
 * Package: com.lhy.campusswap.controller
 * Description:
 *  通用接口：其它模块也可能用到文件上传
 * @Author LHY
 * @Create 2025/12/17 22:11
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Autowired
    private CommonServiceImpl commonServiceImpl;

    // 允许的文件类型
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
    // 最大大小 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;


    /**
     * 文件上传 这个方法暂时保留，后续可能会用到
     * @param file
     * @return
     */
    @PostMapping("/upload")
    /**
     * 为什么泛型指定为String类型？？？
     * 由接口文档可知，返回的Result中data属性值是 文件上传到阿里云后的url地址  类型为String类型
     *
     * 通过MultipartFile 类型来接收上传的文件，要求方法的形参名和表单项的name属性的值/前端提交的参数名（
     * 由接口文档可知参数名为file）一致,如果不一致使用@requesrparam注解进行参数绑定
     */
    public ResponseResult<String> uploadFile(Authentication authentication, MultipartFile file) {
       return ResponseResult.success();

    }

    // 专门的头像上传
    @PostMapping("/avatar/upload")
    public ResponseResult<String> uploadAvatar(Authentication authentication,MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseResult.error("文件不能为空");
        }
        /**
         * ContentType（内容类型）是 HTTP 协议中的一个标准，也称为 MIME 类型（Multipurpose Internet Mail Extensions）。
         * 它用于标识文件的媒体类型，让浏览器和其他客户端知道应该如何处理接收到的数据。
         */
        // 文件类型校验
        // file.getContentType() 返回的就是类似 "image/jpeg" 这样的字符串
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            return ResponseResult.error("不支持的文件类型，仅支持 JPG、PNG、GIF 格式");
        }

        // 文件大小校验
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseResult.error("文件大小不能超过5MB");
        }
        // 头像上传逻辑，可能会有一些特殊处理
        if (authentication != null && authentication.getPrincipal() instanceof AuthUser) {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            Long userId = authUser.getUserId();
            commonServiceImpl.uploadAvatar(userId, file);
            log.info("用户 {} 上传头像：{}", authUser.getUsername(), file);
        } else {
            return ResponseResult.error(ResultCode.UNAUTHORIZED.getCode(), "未认证的用户");
        }
        return ResponseResult.success();
    }

}