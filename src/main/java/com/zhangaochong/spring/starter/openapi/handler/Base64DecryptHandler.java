package com.zhangaochong.spring.starter.openapi.handler;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;

import java.io.InputStream;

public class Base64DecryptHandler implements DecryptHandler {
    @Override
    public InputStream decrypt(InputStream is) {
        byte[] decode = Base64.decode(IoUtil.readBytes(is));
        return IoUtil.toStream(decode);
    }
}
