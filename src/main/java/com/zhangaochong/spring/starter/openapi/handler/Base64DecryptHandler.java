package com.zhangaochong.spring.starter.openapi.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64DecryptHandler implements DecryptHandler {
    @Override
    public InputStream decrypt(InputStream is) {
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            byte[] decode = Base64Utils.decode(bytes);
            return new ByteArrayInputStream(decode);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
