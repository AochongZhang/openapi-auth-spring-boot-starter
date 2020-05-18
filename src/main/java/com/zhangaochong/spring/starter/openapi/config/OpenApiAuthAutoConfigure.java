package com.zhangaochong.spring.starter.openapi.config;

import com.zhangaochong.spring.starter.openapi.OpenApiRequestBodyAdvice;
import com.zhangaochong.spring.starter.openapi.OpenApiResponseBodyAdvice;
import com.zhangaochong.spring.starter.openapi.handler.AuthHandler;
import com.zhangaochong.spring.starter.openapi.handler.Base64DecryptHandler;
import com.zhangaochong.spring.starter.openapi.handler.Base64EncryptHandler;
import com.zhangaochong.spring.starter.openapi.handler.DefaultAuthHandler;
import com.zhangaochong.spring.starter.openapi.properties.OpenApiAuthProperties;
import com.zhangaochong.spring.starter.openapi.properties.UserProperties;
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
@EnableConfigurationProperties({OpenApiAuthProperties.class, UserProperties.class})
public class OpenApiAuthAutoConfigure {
    @Bean
    @ConditionalOnMissingBean(OpenApiRequestBodyAdvice.class)
    public OpenApiRequestBodyAdvice openApiRequestBodyAdvice() {
        return new OpenApiRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(OpenApiResponseBodyAdvice.class)
    public OpenApiResponseBodyAdvice openApiResponseBodyAdvice() {
        return new OpenApiResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(AuthHandler.class)
    public AuthHandler authHandler() {
        return new DefaultAuthHandler();
    }

    @Bean
    @ConditionalOnMissingBean(Base64EncryptHandler.class)
    public Base64EncryptHandler base64EncryptHandler() {
        return new Base64EncryptHandler();
    }

    @Bean
    @ConditionalOnMissingBean(Base64DecryptHandler.class)
    public Base64DecryptHandler base64DecryptHandler() {
        return new Base64DecryptHandler();
    }
}
