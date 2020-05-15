package com.zhangaochong.spring.starter.openapi.annotation;

import java.lang.annotation.*;

/**
 * 请求方法或类启用注解
 *
 * @author AochongZhang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApiAuth {
    boolean requestDecrypt() default true;
    boolean responseEncrypt() default true;
}
