package com.struggle.datasource.service.impl;

import com.struggle.datasource.service.ElasticsearchService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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
        IndexResponse response = transportClient.prepareIndex(index, "doc", id).setSource(dataMap).get();
        System.out.println(response);
    }

    @Override
    public void deleteById(String index, String type, String id) {
        DeleteResponse response = transportClient.prepareDelete(index, "doc", id).get();
        System.out.println(response);
    }

    @Override
    public void insertById(String index, String type, String id, Map<String, Object> dataMap) {
        IndexResponse response = transportClient.prepareIndex(index, "doc", id).setSource(dataMap).get();
        System.out.println(response);
    }

    @Override
    public void batchInsert(String index, String type, String column, List<Map> list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        list.forEach(data->{
            String time = df.format((Timestamp)data.get("create_time"));
            data.put("create_time", LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            bulkRequestBuilder.add(transportClient.prepareIndex(index, type, data.get(column).toString()).setSource(data));
        });

        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()){
            System.out.println(bulkResponse.buildFailureMessage());
        }
    }
}
