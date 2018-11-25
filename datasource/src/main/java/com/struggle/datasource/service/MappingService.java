package com.struggle.datasource.service;

public interface MappingService {

    /**
     * 获取Elasticsearch的数据转换后类型
     *
     * @param mysqlType mysql数据类型
     * @param data      具体数据
     * @return Elasticsearch对应的数据类型
     */
    Object getElasticsearchTypeObject(String mysqlType, String data);
}
