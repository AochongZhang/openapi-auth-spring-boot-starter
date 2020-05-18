package com.zhangaochong.spring.starter.openapi.util;

import com.zhangaochong.spring.starter.openapi.annotation.OpenApiAuth;
import org.springframework.core.MethodParameter;

import java.util.Objects;

public class OpenApiAuthUtils {
    public static OpenApiAuth getAnnotation(MethodParameter parameter) {
        OpenApiAuth openApiAuth = Objects.requireNonNull(parameter.getMethod())
                .getAnnotation(OpenApiAuth.class);
        if (openApiAuth == null) {
            openApiAuth = parameter.getContainingClass().getAnnotation(OpenApiAuth.class);
        }
        return openApiAuth;
    }
}
