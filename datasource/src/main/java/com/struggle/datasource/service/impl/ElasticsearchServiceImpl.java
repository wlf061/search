package com.struggle.datasource.service.impl;

import com.struggle.datasource.service.ElasticsearchService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 类的描述
 *
 * @author nancy.wang
 * @Time 2018/11/21
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService{
    @Override
    public void updateById(String index, String type, String id, Map<String, Object> dataMap) {

    }
}
