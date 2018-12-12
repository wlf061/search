package com.struggle.datasource.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * Created by jinnvc on 2018/12/3.
 */
public class CanalInsertEvent extends CanalAbstractEvent {

    public CanalInsertEvent(CanalEntry.Entry source) {
        super(source);
    }
}
