package com.struggle.datasource.service.impl;

import com.google.common.collect.Maps;
import com.struggle.datasource.service.MappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

@Service
@ConfigurationProperties
public class MappingServiceImpl implements MappingService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<String, Converter> mysqlTypeElasticsearchTypeMapping;

    @Override
    public Object getElasticsearchTypeObject(String mysqlType, String data) {
        Optional<Entry<String, Converter>> result = mysqlTypeElasticsearchTypeMapping.entrySet().parallelStream().filter(entry -> mysqlType.toLowerCase().contains(entry.getKey())).findFirst();
        return (result.isPresent() ? result.get().getValue() : (Converter) data1 -> data1).convert(data);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet");
        mysqlTypeElasticsearchTypeMapping = Maps.newHashMap();
        mysqlTypeElasticsearchTypeMapping.put("char", data -> data);
        mysqlTypeElasticsearchTypeMapping.put("text", data -> data);
        mysqlTypeElasticsearchTypeMapping.put("blob", data -> data);
        mysqlTypeElasticsearchTypeMapping.put("int", Long::valueOf);
        mysqlTypeElasticsearchTypeMapping.put("date", data -> LocalDateTime.parse(data, formatter));
        mysqlTypeElasticsearchTypeMapping.put("time", data -> LocalDateTime.parse(data, formatter));
        mysqlTypeElasticsearchTypeMapping.put("float", Double::valueOf);
        mysqlTypeElasticsearchTypeMapping.put("double", Double::valueOf);
        mysqlTypeElasticsearchTypeMapping.put("decimal", Double::valueOf);
    }

    private interface Converter {
        Object convert(String data);
    }
}
