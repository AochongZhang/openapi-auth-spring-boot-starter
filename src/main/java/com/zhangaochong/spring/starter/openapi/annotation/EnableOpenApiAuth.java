package com.zhangaochong.spring.starter.openapi.annotation;

import com.zhangaochong.spring.starter.openapi.config.OpenApiAuthAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 全局启用注解
 *
 * @author AochongZhang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({OpenApiAuthAutoConfigure.class})
public @interface EnableOpenApiAuth {
}
