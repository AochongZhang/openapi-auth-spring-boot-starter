package com.zhangaochong.spring.starter.openapi.properties;

import com.zhangaochong.spring.starter.openapi.enums.StatusEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * 配置
 *
 * @author AochongZhang
 */
@Data
@ConfigurationProperties(prefix = "openapi-auth")
public class OpenApiAuthProperties {
    /** 请求参数是否添加验证 */
    private StatusEnum requestAuth = StatusEnum.ENABLE;
    /** 请求参数是否解密 */
    private StatusEnum requestDecrypt = StatusEnum.DISABLE;
    /** 返回值是否加密 */
    private StatusEnum responseEncrypt = StatusEnum.DISABLE;
    /** 请求超时时间单位 */
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    /** 请求超时时间 */
    private Long timeout = 5L;
}
