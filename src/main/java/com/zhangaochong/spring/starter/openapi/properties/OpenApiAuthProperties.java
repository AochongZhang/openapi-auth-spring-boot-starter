package com.zhangaochong.spring.starter.openapi.properties;

import com.zhangaochong.spring.starter.openapi.enums.StatusEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置
 *
 * @author AochongZhang
 */
@Data
@ConfigurationProperties(prefix = "openapi-auth")
public class OpenApiAuthProperties {
    /** 返回值是否加密 */
    private StatusEnum responseEncrypt;
}
