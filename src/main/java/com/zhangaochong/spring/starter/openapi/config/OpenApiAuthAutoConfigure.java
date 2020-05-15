package com.zhangaochong.spring.starter.openapi.config;

import com.zhangaochong.spring.starter.openapi.OpenApiRequestBodyAdvice;
import com.zhangaochong.spring.starter.openapi.OpenApiResponseBodyAdvice;
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
    @ConditionalOnMissingBean(OpenApiRequestBodyAdvice.class)
    public OpenApiRequestBodyAdvice openApiRequestBodyAdvice() {
        return new OpenApiRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(OpenApiResponseBodyAdvice.class)
    public OpenApiResponseBodyAdvice openApiResponseBodyAdvice() {
        return new OpenApiResponseBodyAdvice();
    }
}
