package com.zhangaochong.spring.starter.openapi.util;

import com.zhangaochong.spring.starter.openapi.annotation.OpenApiAuth;
import org.springframework.core.MethodParameter;
import org.springframework.util.DigestUtils;

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

    public static void main(String[] args) {
        String s = DigestUtils.md5DigestAsHex("1234".getBytes());
        System.out.println(s);
    }
}
