package com.zhangaochong.spring.starter.openapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author AochongZhang
 */
@Data
@ConfigurationProperties(prefix = "openapi-auth.users")
public class UserProperties {
    /** 用户密钥 */
    private Map<String, String> secretKeyMap;
}
