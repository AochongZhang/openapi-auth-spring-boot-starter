package com.zhangaochong.spring.starter.openapi;

import com.zhangaochong.spring.starter.openapi.annotation.OpenApiAuth;
import com.zhangaochong.spring.starter.openapi.enums.StatusEnum;
import com.zhangaochong.spring.starter.openapi.handler.EncryptHandler;
import com.zhangaochong.spring.starter.openapi.properties.OpenApiAuthProperties;
import com.zhangaochong.spring.starter.openapi.util.OpenApiAuthUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * 返回值加密处理
 *
 * @author AochongZhang
 */
@ControllerAdvice
public class OpenApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    @Resource
    private EncryptHandler encryptHandler;

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        StatusEnum responseEncrypt = openApiAuthProperties.getResponseEncrypt();
        if (StatusEnum.ENABLE.equals(responseEncrypt)) {
            OpenApiAuth openApiAuth = OpenApiAuthUtils.getAnnotation(methodParameter);
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
        return encryptHandler.encrypt(body);
    }
}
