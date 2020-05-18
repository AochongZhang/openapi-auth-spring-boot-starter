package com.zhangaochong.spring.starter.openapi.handler;

import com.zhangaochong.spring.starter.openapi.properties.UserProperties;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认权限处理器实现
 *
 * @author AochongZhang
 */
public class DefaultAuthHandler extends AbstractAuthHandler {
    /** 存储调用方随机数，生产中可放入Redis，并设置过期时间 */
    private static final Map<String, String> NONCEMAP = new ConcurrentHashMap<>();

    /** 用户唯一key及密钥，生产中可放入数据库和Redis */
    @Resource
    private UserProperties userConfig;

    @Override
    public String getUserSecretKey(String accessKey) {
        Map<String, String> secretKeyMap = userConfig.getSecretKeyMap();
        if (secretKeyMap == null) {
            throw new IllegalArgumentException("未配置用户密钥");
        }
        return secretKeyMap.get(accessKey);
    }

    @Override
    public boolean saveUserNonce(String accessKey, String nonce) {
        NONCEMAP.put(accessKey, nonce);
        return true;
    }

    @Override
    public String getUserLastNonce(String accessKey) {
        return NONCEMAP.get(accessKey);
    }
}
