package com.zhangaochong.spring.starter.openapi;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.zhangaochong.spring.starter.openapi.annotation.OpenApiAuth;
import com.zhangaochong.spring.starter.openapi.enums.StatusEnum;
import com.zhangaochong.spring.starter.openapi.properties.OpenApiAuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * 请求参数解密处理
 * 仅对使用了@RquestBody注解的参数生效
 *
 * @author AochongZhang
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {
    /** 解密策略 */
    private final UnaryOperator<InputStream> decryptStrategy;

    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    public DecryptRequestBodyAdvice() {
        // 默认Base64解码
        this((i) -> {
            byte[] decode = Base64.decode(IoUtil.readBytes(i));
            return IoUtil.toStream(decode);
        });
    }

    public DecryptRequestBodyAdvice(UnaryOperator<InputStream> decryptStrategy) {
        this.decryptStrategy = decryptStrategy;
    }

    /**
     * @param methodParameter
     * @param type
     * @param aClass
     * @return 返回false则不执行advice内业务
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        StatusEnum requestDecrypt = openApiAuthProperties.getRequestDecrypt();
        if (StatusEnum.ENABLE.equals(requestDecrypt)) {
            OpenApiAuth openApiAuth = Objects.requireNonNull(methodParameter.getMethod())
                    .getAnnotation(OpenApiAuth.class);
            if (openApiAuth == null) {
                openApiAuth = methodParameter.getContainingClass()
                        .getAnnotation(OpenApiAuth.class);
            }
            if (openApiAuth != null) {
                return openApiAuth.requestDecrypt();
            }
        }
        return false;
    }

    /**
     * 读取参数前执行
     * 执行解密等操作
     * @param httpInputMessage
     * @param methodParameter
     * @param type
     * @param aClass
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage,
                                           MethodParameter methodParameter,
                                           Type type,
                                           Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return new DecryptHttpInputMessage(httpInputMessage, decryptStrategy);
    }
}
