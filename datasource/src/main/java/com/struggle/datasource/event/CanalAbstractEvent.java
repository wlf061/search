package com.struggle.datasource.event;


import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author nancy.wang
 * @Time 2018/11/21
 */
public abstract  class CanalAbstractEvent extends ApplicationEvent{

    public CanalAbstractEvent(Entry source) {
        super(source);
    }
}
