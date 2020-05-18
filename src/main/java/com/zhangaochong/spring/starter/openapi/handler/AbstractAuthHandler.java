package com.zhangaochong.spring.starter.openapi.handler;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * 处理器抽象类
 *
 * @author AochongZhang
 */
public abstract class AbstractAuthHandler implements AuthHandler {
    @Override
    public String generateSign(SortedMap<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        // 拼接字符串
        for (String key : params.keySet()) {
            stringBuilder.append(key).append("=").append(params.get(key)).append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return SecureUtil.md5(stringBuilder.toString());
    }

    @Override
    public void auth(Map<String, Object> params, TimeUnit timeUnit, long timeout) {
        String accessKey = (String) params.get(ACCESSKEY_PARAM_NAME);
        Long timestamp = (Long) params.get(TIMESTAMP_PARAM_NAME);
        String nonce = (String) params.get(NONCE_PARAM_NAME);
        String sign = (String) params.remove(SIGN_PARAM_NAME);

        // 验证必需参数
        String temp = "{}不能为空";
        Assert.notBlank(accessKey, temp, ACCESSKEY_PARAM_NAME);
        Assert.notNull(timestamp, temp, TIMESTAMP_PARAM_NAME);
        Assert.notBlank(nonce, temp, NONCE_PARAM_NAME);
        Assert.notBlank(sign, temp, SIGN_PARAM_NAME);

        String secretKey = getUserSecretKey(accessKey);
        Assert.notBlank(secretKey, temp, SECRETKEY_PARAM_NAME);

        // 验证时间
        validTime(timestamp, timeUnit, timeout);
        // 验证随机字符串
        validNonce(nonce, getUserLastNonce(accessKey));
        // 验证签名
        params.put(SECRETKEY_PARAM_NAME, secretKey);
        if (!sign.equalsIgnoreCase(generateSign(new TreeMap<>(params)))) {
            throw new IllegalArgumentException("签名验证失败");
        }
        saveUserNonce(accessKey, nonce);
    }

    /**
     * 验证时间戳
     *
     * @param orgTimestamp 请求参数中时间戳
     * @param timeUnit 请求超时时间单位
     * @param timeout 请求超时时间
     */
    private static void validTime(long orgTimestamp, TimeUnit timeUnit, long timeout) {
        timeout = timeUnit.toMillis(timeout);
        long now = System.currentTimeMillis();
        if ((now - orgTimestamp) > timeout) {
            throw new IllegalArgumentException("时间戳超时");
        }
    }

    /**
     * 验证随机数
     *
     * @param orgNonce 请求参数中随机数
     * @param lastNonce 上次请求随机数
     */
    private static void validNonce(String orgNonce, String lastNonce) {
        if (orgNonce.equals(lastNonce)) {
            throw new IllegalArgumentException("随机数重复");
        }
    }
}
