package com.lhy.campusswap.service.impl;

import com.lhy.campusswap.common.ResponseResult;
import com.lhy.campusswap.common.ResultCode;
import com.lhy.campusswap.entity.User;
import com.lhy.campusswap.exception.BusinessException;
import com.lhy.campusswap.service.CommonService;
import com.lhy.campusswap.service.UserService;
import com.lhy.campusswap.utils.aliyun.oss.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * ClassName: CommonServiceImpl
 * Package: com.lhy.campusswap.service.impl
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/17 22:39
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private UserService userService;
    @Override
    public String uploadFile(Long userId, MultipartFile file) {
        return "";
    }

    @Override
    public String uploadAvatar(Long userId,MultipartFile file) {
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称：防止上传到阿里云的文件，因为名字重复导致覆盖的问题
            String objectName = UUID.randomUUID() + extension;

            //文件的请求路径
            //参数：  byte数组，文件对象转成的数组     传上去的图片在阿里云存储空间里面的名字
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            User user = new User();
            user.setId(userId);
            user.setAvatar(filePath);
            //更新用户的头像信息
            userService.updateUserInfo(user);
            return filePath;
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
            throw new BusinessException(ResultCode.UPLOAD_FAILED.getCode(), "文件上传失败");
        }
    }
}
