package com.zhangaochong.spring.starter.openapi.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.util.Base64Utils;

public class Base64EncryptHandler implements EncryptHandler {
    @Override
    public Object encrypt(Object o) {
        return Base64Utils.encodeToString(JSON.toJSONString(o).getBytes());
    }
}
