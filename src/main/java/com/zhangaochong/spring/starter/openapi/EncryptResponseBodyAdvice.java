package com.zhangaochong.spring.starter.openapi;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.zhangaochong.spring.starter.openapi.annotation.OpenApiAuth;
import com.zhangaochong.spring.starter.openapi.enums.StatusEnum;
import com.zhangaochong.spring.starter.openapi.properties.OpenApiAuthProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * 返回值加密处理
 *
 * @author AochongZhang
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    /** 加密策略 */
    private final UnaryOperator<Object> encryptStrategy;

    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    public EncryptResponseBodyAdvice() {
        // 默认Base64编码
        this(o -> Base64.encode(JSON.toJSONString(o)));
    }

    public EncryptResponseBodyAdvice(UnaryOperator<Object> encryptStrategy) {
        this.encryptStrategy = encryptStrategy;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        StatusEnum responseEncrypt = openApiAuthProperties.getResponseEncrypt();
        if (StatusEnum.ENABLE.equals(responseEncrypt)) {
            OpenApiAuth openApiAuth = Objects.requireNonNull(methodParameter.getMethod())
                    .getAnnotation(OpenApiAuth.class);
            if (openApiAuth == null) {
                openApiAuth = methodParameter.getContainingClass()
                        .getAnnotation(OpenApiAuth.class);
            }
            if (openApiAuth != null) {
                return openApiAuth.responseEncrypt();
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return encryptStrategy.apply(body);
    }
}
