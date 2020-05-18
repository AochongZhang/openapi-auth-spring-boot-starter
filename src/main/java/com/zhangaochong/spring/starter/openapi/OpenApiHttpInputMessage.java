package com.zhangaochong.spring.starter.openapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.InputStream;

/**
 * @author AochongZhang
 */
public class OpenApiHttpInputMessage implements HttpInputMessage {
    private final HttpHeaders httpHeaders;
    private final InputStream body;

    public OpenApiHttpInputMessage(HttpInputMessage httpInputMessage, InputStream inputStream) {
        this.httpHeaders = httpInputMessage.getHeaders();
        this.body = inputStream;
    }

    @Override
    public InputStream getBody() {
        return this.body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }
}
