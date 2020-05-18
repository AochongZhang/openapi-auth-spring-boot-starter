package com.zhangaochong.spring.starter.openapi.handler;

import java.io.InputStream;

@FunctionalInterface
public interface DecryptHandler {
    InputStream decrypt(InputStream is);
}
