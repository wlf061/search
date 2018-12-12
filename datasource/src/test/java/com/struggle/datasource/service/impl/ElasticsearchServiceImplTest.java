package com.struggle.datasource.service.impl;

import com.google.common.collect.Maps;
import com.struggle.datasource.dao.BaseDao;
import com.struggle.datasource.dao.JdbcDao;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.struggle.datasource.dao")
public class ElasticsearchServiceImplTest {
    @Resource
    private TransportClient transportClient;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private JdbcDao jdbcDao;

    @Test
    public void insertById() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 112);
        map.put("name", "hello");
        map.put("date", LocalDateTime.now());
        IndexResponse response = transportClient.prepareIndex("hello", "hello", map.get("id").toString()).setSource(map).get();
        System.out.println(response);
    }

    @Test
    public void delete() throws Exception{
        DeleteResponse response = transportClient.prepareDelete("hello", "hello", "222").get();
        System.out.println(response);
    }

    @Test
    public void deleteByQuery() throws Exception{
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
                .filter(QueryBuilders.matchQuery("id", 112))
                .source("hello")
                .get();
        long deleted = response.getDeleted();
        System.out.println(response);
    }

    @Test
    public void deleteByQueryAsc() throws  Exception{
        DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
                .filter(QueryBuilders.matchQuery("gender", "male"))
                .source("hello")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });
    }

    @Test
    public void batchInsert() throws Exception{
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        ArrayList<Map> list = new ArrayList<Map>();
        Map map = new HashMap<>();
        map.put("id", 113);
        map.put("name", "113-update");
        map.put("date", LocalDateTime.now());
        list.add(map);
        Map map2 = new HashMap<>();
        map2.put("id", 114);
        map2.put("name", "114-update");
        map2.put("date", LocalDateTime.now());
        list.add(map2);
        for (int i=0; i<list.size(); ++i){
            Map data = (Map)list.get(i);
            bulkRequestBuilder.add(transportClient.prepareIndex("hello", "hello", data.get("id").toString()).setSource(data));
        }

        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()){
            System.out.println(bulkResponse.buildFailureMessage());
        }

    }

    @Test
    public void getDataByIbats() throws Exception{
        HashMap map = (HashMap)baseDao.selectByPK("id", "1", "datasearch", "prod_info");
        System.out.println(map.get("name"));
    }

    @Test
    public void getJdbcDataByIbats() throws Exception{
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        List<Map> list = jdbcDao.findAll("datasearch", "prod_info");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.forEach(map -> {
            String time = df.format((Timestamp)map.get("create_time"));
            map.put("create_time", LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            bulkRequestBuilder.add(transportClient.prepareIndex("test-search", "doc", map.get("id").toString()).setSource(map));
        });
        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()){
            System.out.println(bulkResponse.buildFailureMessage());
        }
    }



}