package com.zhangaochong.spring.starter.openapi.handler;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;

public class Base64EncryptHandler implements EncryptHandler {
    @Override
    public Object encrypt(Object o) {
        return Base64.encode(JSON.toJSONString(o));
    }
}
