package com.struggle.datasource.listener;


import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author nancy.wang
 * @Time 2018/11/21
 */
@Component
public class CanalUpdateListener extends CanalAbstractListener {

    @Override
    public void doSync(String index, String type, CanalEntry.RowData rowData) {
        //TODO: call elasticsearchService to update es's data
    }
}
