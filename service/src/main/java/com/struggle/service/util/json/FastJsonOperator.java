package com.struggle.service.util.json;

import com.alibaba.fastjson.JSON;
import java.util.List;

public class FastJsonOperator implements JsonOperator {

    static {
        JsonUtils.registerOperator(new FastJsonOperator());
    }

    @Override
    public <T> T toObject(String json, Class<T> objClass) throws JsonException {
        return JSON.parseObject(json, objClass);
    }

    @Override
    public <T> List<T> toListObject(String json, Class<T> objClass) throws JsonException {
        return JSON.parseArray(json, objClass);
    }

    @Override
    public String toJson(Object obj) throws JsonException {
        return JSON.toJSONString(obj);
    }
}