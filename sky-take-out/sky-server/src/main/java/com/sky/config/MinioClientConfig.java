package com.sky.config;

import com.sky.properties.MinioProperties;
import com.sky.utils.MinioOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Slf4j
public class MinioClientConfig {
    @Bean
    @ConditionalOnMissingBean
    public MinioOssUtil minioOssUtil(MinioProperties minioProperties){
        log.info("开始构建Minio 上传对象,{}",minioProperties);
        return new MinioOssUtil(
                minioProperties.getEndpoint(),
                minioProperties.getAccessKey(),
                minioProperties.getSecretKey(),
                minioProperties.getBucketName());
    }
}
