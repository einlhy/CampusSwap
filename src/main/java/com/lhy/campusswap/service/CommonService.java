package com.lhy.campusswap.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: CommonService
 * Package: com.lhy.campusswap.service
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/17 22:39
 */
public interface CommonService {
    String uploadFile(Long userId,MultipartFile file);
    String uploadAvatar(Long userId,MultipartFile file);
}
