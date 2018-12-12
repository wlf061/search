package com.struggle.datasource.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.struggle.datasource.event.CanalInsertEvent;
import com.struggle.datasource.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jinnvc on 2018/12/6.
 */

@Component
public class CanalInsertListener extends CanalAbstractListener<CanalInsertEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CanalInsertListener.class);

    @Resource
    ElasticsearchService elasticsearchService;

    @Override
    protected void doSync(String index, String type, CanalEntry.RowData rowData) {
        List<CanalEntry.Column> columns = rowData.getAfterColumnsList();
        CanalEntry.Column idColumn = columns.stream().filter(column -> column.getName().equals("id")).findFirst().orElse(null);
        if(idColumn == null){
            logger.debug("insert_column_id_info update主键id,database=" + index + ",table=" + type + ",id=" + idColumn.getValue());
            return;
        }
        Map<String, Object> dataMap = parseColumnsToMap(columns);
        elasticsearchService.updateById(index, type, idColumn.getValue(), dataMap);
        logger.debug("insert_es_info 同步es插入操作成功！database=" + index + ",table=" + type + ",data=" + dataMap);
    }
}
