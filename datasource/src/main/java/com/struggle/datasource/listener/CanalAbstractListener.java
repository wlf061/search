package com.struggle.datasource.listener;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.struggle.datasource.event.CanalAbstractEvent;
import com.struggle.datasource.service.MappingService;
import org.springframework.context.ApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nancy.wang
 * @Time 2018/11/21
 */
public abstract class CanalAbstractListener<EVENT extends CanalAbstractEvent> implements ApplicationListener<EVENT> {

    private static final Logger logger = LoggerFactory.getLogger(CanalAbstractListener.class);

    @Resource
    private MappingService mappingService;
    /*onApplicationEvent 里面统一处理mysql 增删改的数据*/
    @Override
    public void onApplicationEvent(EVENT event) {
        Entry entry = event.getEntry();
        String database = entry.getHeader().getSchemaName();
        String table = entry.getHeader().getTableName();
        RowChange change = null;
        try {
            change = RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            logger.error("rowchange.parseForm 失败， 错误:",e.getMessage());
        }
        change.getRowDatasList().forEach(rowData->doSync(database, table,rowData));
    }
    protected Map<String, Object> parseColumnsToMap(List<Column> columns){
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        columns.forEach(column->{
            if(column == null){
                return;
            }
            fieldValueMap.put(column.getName(),column.getIsNull() ? null : mappingService.getElasticsearchTypeObject(column.getMysqlType(), column.getValue()));
        });
        return fieldValueMap;
    }

    protected abstract void doSync(String index, String type, RowData rowData);
}
