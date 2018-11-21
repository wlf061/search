package com.struggle.datasource.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.struggle.datasource.event.CanalAbstractEvent;
import org.springframework.context.ApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nancy.wang
 * @Time 2018/11/21
 */
public abstract class CanalAbstractListener<EVENT extends CanalAbstractEvent> implements ApplicationListener<EVENT> {

    private static final Logger log = LoggerFactory.getLogger(CanalAbstractListener.class);

    /*onApplicationEvent 里面统一处理mysql 增删改的数据*/
    @Override
    public void onApplicationEvent(EVENT event) {
        //TODO:call the different listener's doSync
    }

    public abstract void doSync( String index, String type, CanalEntry.RowData rowData);
}
