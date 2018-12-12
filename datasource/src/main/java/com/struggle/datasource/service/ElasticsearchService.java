package com.struggle.datasource.service;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;


public interface ElasticsearchService {
    void updateById(String index, String type, String id, Map<String, Object> dataMap);

    void insertById(String index, String type, String id, Map<String, Object> dataMap);

    void deleteById(String index, String type, String id);

    void batchInsert(String index, String type, String column, List<Map> list);
}
