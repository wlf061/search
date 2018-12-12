package com.struggle.datasource.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * Created by jinnvc on 2018/12/6.
 */
public class CanalDeleteEvent extends  CanalAbstractEvent{

    public CanalDeleteEvent(CanalEntry.Entry source) {
        super(source);
    }
}
