package com.struggle.datasource.service.impl;

import com.struggle.datasource.service.ElasticsearchService;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类的描述
 * @author nancy.wang
 * @Time 2018/11/21
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService{

    @Resource
    private TransportClient transportClient;
    @Override
    public void updateById(String index, String type, String id, Map<String, Object> dataMap) {
        transportClient.prepareIndex(index, type, id).setSource(dataMap).get();
    }
}
