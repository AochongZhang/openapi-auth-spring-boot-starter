package com.zhangaochong.spring.starter.openapi.handler;

import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * 权限处理器接口
 *
 * @author AochongZhang
 */
public interface AuthHandler {
    /** 调用方唯一key参数名 */
    String ACCESSKEY_PARAM_NAME = "accessKey";
    /** 调用方密钥参数名 */
    String SECRETKEY_PARAM_NAME = "secretKey";
    /** 调用方时间戳参数名 */
    String TIMESTAMP_PARAM_NAME = "timestamp";
    /** 调用方随机数参数名 */
    String NONCE_PARAM_NAME = "nonce";
    /** 调用方签名参数名 */
    String SIGN_PARAM_NAME = "sign";

    /**
     * 根据用户唯一key获取密钥
     *
     * @param accessKey 用户唯一key
     * @return 密钥
     */
    String getUserSecretKey(String accessKey);

    /**
     * 保存用户请求随机数
     *
     * @param accessKey 用户唯一key
     * @param nonce 随机数
     * @return
     */
    boolean saveUserNonce(String accessKey, String nonce);

    /**
     * 获取用户上次请求随机数
     *
     * @param accessKey 用户唯一key
     * @return
     */
    String getUserLastNonce(String accessKey);

    /**
     * 根据请求参数生成签名
     *
     * @param params 请求参数
     * @return 签名
     */
    String generateSign(SortedMap<String, Object> params);

    /**
     * 验证签名
     *
     * @param params 请求参数
     * @param timeUnit 请求超时时间单位
     * @param timeout 请求超时时间
     */
    void auth(Map<String, Object> params, TimeUnit timeUnit, long timeout);
}
