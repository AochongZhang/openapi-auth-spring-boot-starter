package com.zhangaochong.spring.starter.openapi;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangaochong.spring.starter.openapi.annotation.OpenApiAuth;
import com.zhangaochong.spring.starter.openapi.enums.StatusEnum;
import com.zhangaochong.spring.starter.openapi.handler.AuthHandler;
import com.zhangaochong.spring.starter.openapi.handler.DecryptHandler;
import com.zhangaochong.spring.starter.openapi.properties.OpenApiAuthProperties;
import com.zhangaochong.spring.starter.openapi.util.OpenApiAuthUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 请求参数解密处理
 * 仅对使用了@RquestBody注解的参数生效
 *
 * @author AochongZhang
 */
@ControllerAdvice
public class OpenApiRequestBodyAdvice extends RequestBodyAdviceAdapter {
    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    @Resource
    private AuthHandler authHandler;

    @Resource
    private DecryptHandler decryptHandler;

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
        return true;
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

        httpInputMessage = requestDecrypt(httpInputMessage, methodParameter);
        httpInputMessage = requestAuth(httpInputMessage, methodParameter);
        return httpInputMessage;
    }

    private HttpInputMessage requestDecrypt(HttpInputMessage httpInputMessage, MethodParameter methodParameter) throws IOException {
        StatusEnum requestDecrypt = openApiAuthProperties.getRequestDecrypt();
        if (StatusEnum.ENABLE.equals(requestDecrypt)) {
            OpenApiAuth openApiAuth = OpenApiAuthUtils.getAnnotation(methodParameter);
            if (openApiAuth != null) {
                if (openApiAuth.requestDecrypt()) {
                    return new OpenApiHttpInputMessage(httpInputMessage, decryptHandler.decrypt(httpInputMessage.getBody()));
                }
            }
        }
        return httpInputMessage;
    }

    private HttpInputMessage requestAuth(HttpInputMessage httpInputMessage, MethodParameter methodParameter) throws IOException {
        StatusEnum requestAuth = openApiAuthProperties.getRequestAuth();
        if (StatusEnum.ENABLE.equals(requestAuth)) {
            OpenApiAuth openApiAuth = OpenApiAuthUtils.getAnnotation(methodParameter);
            if (openApiAuth != null) {
                if (openApiAuth.requestAuth()) {
                    InputStream body = httpInputMessage.getBody();
                    String read = IoUtil.read(body, StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSON.parseObject(read);

                    authHandler.auth(jsonObject.getInnerMap(), openApiAuthProperties.getTimeUnit(),
                            openApiAuthProperties.getTimeout());

                    jsonObject.remove(AuthHandler.ACCESSKEY_PARAM_NAME);
                    jsonObject.remove(AuthHandler.TIMESTAMP_PARAM_NAME);
                    jsonObject.remove(AuthHandler.NONCE_PARAM_NAME);
                    jsonObject.remove(AuthHandler.SIGN_PARAM_NAME);
                    return new OpenApiHttpInputMessage(httpInputMessage,
                            IoUtil.toStream(jsonObject.toJSONString(), StandardCharsets.UTF_8));
                }
            }
        }
        return httpInputMessage;
    }
}
