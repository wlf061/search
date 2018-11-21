package com.struggle.datasource.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


@Component
public class CanalClient implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CanalClient.class);
    private CanalConnector canalConnector;

    @Value("${canal.host}")
    private String canalHost;
    @Value("${canal.port}")
    private String canalPort;
    @Value("${canal.destination}")
    private String canalDestination;
    @Value("${canal.username}")
    private String canalUsername;
    @Value("${canal.password}")
    private String canalPassword;
    @Value("${canal.filter}")
    private String canalFilter;

    @Bean
    public CanalConnector getCanalConnector() {
        canalConnector = CanalConnectors.newClusterConnector(Lists.newArrayList(new InetSocketAddress(canalHost, Integer.valueOf(canalPort))), canalDestination, canalUsername, canalPassword);
        canalConnector.connect();
        canalConnector.subscribe(canalFilter);
        // 回滚寻找上次中断的位置
        canalConnector.rollback();
        logger.info("canal客户端启动成功");
        return canalConnector;
    }

    @Override
    public void destroy() throws Exception {
        if (canalConnector != null) {
            canalConnector.disconnect();
        }
    }
}
