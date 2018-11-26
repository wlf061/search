package com.struggle.datasource.service.impl;

import com.google.common.collect.Maps;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchServiceImplTest {
    @Resource
    private TransportClient transportClient;
    @Test
    public void insertById() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 111);
        map.put("name", "name");
        map.put("date", LocalDateTime.now());
        IndexResponse response = transportClient.prepareIndex("test", "test", "888").setSource(map).get();
        System.out.println(response);
    }


}