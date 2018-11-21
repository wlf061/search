package com.struggle.datasource.service;

import java.util.Map;


public interface ElasticsearchService {
    void updateById(String index, String type, String id, Map<String, Object> dataMap);
}
