package com.maple.harbor.oss.minio;

import io.minio.MinioClient;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss")
@Setter
public class MinioConfig {

    /**
     * endpoint
     */
    private String endpoint;

    /**
     * 访问key
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(endpoint)
                       .credentials(accessKey, secretKey)
                       .build();
    }

}

