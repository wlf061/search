package com.struggle.datasource.listener;


import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.struggle.datasource.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author nancy.wang
 * @Time 2018/11/21
 */
@Component
public class CanalUpdateListener extends CanalAbstractListener {
    private static final Logger logger = LoggerFactory.getLogger(CanalUpdateListener.class);

    @Resource
    private ElasticsearchService elasticsearchService;
    @Override
    public void doSync(String index, String type, RowData rowData) {
        List<CanalEntry.Column> columns = rowData.getAfterColumnsList();
        // set id is primary key, find the value
        CanalEntry.Column idColumn = columns.stream().filter(column -> column.getName().equals("id")).findFirst().orElse(null);
        if(idColumn == null){
            logger.debug("update_column_id_info update主键id,database=" + index + ",table=" + type + ",id=" + idColumn.getValue());
            return;
        }
        Map<String, Object> dataMap = parseColumnsToMap(columns);
        elasticsearchService.updateById(index, type, idColumn.getValue(), dataMap);
        logger.debug("update_es_info 同步es插入操作成功！database=" + index + ",table=" + type + ",data=" + dataMap);
    }
}
