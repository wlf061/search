package com.struggle.service.util.json;


import java.util.List;

public interface JsonOperator {
    <T> T toObject(String json, Class<T> objClass) throws JsonException;

    <T> List<T> toListObject(String json, Class<T> objClass) throws JsonException;

    String toJson(Object obj) throws JsonException;
}
