package com.zhangaochong.spring.starter.openapi.config;

import com.zhangaochong.spring.starter.openapi.DecryptRequestBodyAdvice;
import com.zhangaochong.spring.starter.openapi.EncryptResponseBodyAdvice;
import com.zhangaochong.spring.starter.openapi.properties.OpenApiAuthProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author AochongZhang
 */
@Configuration
@EnableConfigurationProperties(OpenApiAuthProperties.class)
public class OpenApiAuthAutoConfigure {
    @Bean
    @ConditionalOnMissingBean(EncryptResponseBodyAdvice.class)
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice() {
        return new EncryptResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(DecryptRequestBodyAdvice.class)
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice() {
        return new DecryptRequestBodyAdvice();
    }
}
