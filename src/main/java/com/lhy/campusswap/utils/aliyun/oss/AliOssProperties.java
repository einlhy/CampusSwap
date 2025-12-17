package com.lhy.campusswap.utils.aliyun.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: AliyunOssProperties
 * Package: com.lhy.campusswap.utils
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/17 22:03
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
