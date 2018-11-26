package com.struggle.datasource.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class ElasticsearchClient implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchClient.class);
    private TransportClient transportClient;

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private String port;

    @Bean
    public TransportClient getTransportClient() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", clusterName)
/*                .put("client.transport.sniff", true)*/
                .build();
        transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
        logger.info("elasticsearch transportClient 连接成功");
        return transportClient;
    }

    @Override
    public void destroy() throws Exception {
        if (transportClient != null) {
            transportClient.close();
        }
    }
}
