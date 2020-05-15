package com.zhangaochong.spring.starter.openapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.UnaryOperator;

public class DecryptHttpInputMessage implements HttpInputMessage {
    private HttpHeaders httpHeaders;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage httpInputMessage,
                                   UnaryOperator<InputStream> decryptStrategy) throws IOException {
        this.httpHeaders = httpInputMessage.getHeaders();
        this.body = decryptStrategy.apply(httpInputMessage.getBody());
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
