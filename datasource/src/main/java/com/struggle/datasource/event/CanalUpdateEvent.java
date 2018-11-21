package com.struggle.datasource.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import org.springframework.stereotype.Component;

/**
 * 类的描述
 *
 * @author nancy.wang
 * @Time 2018/11/21
 */
public class CanalUpdateEvent extends CanalAbstractEvent {

    public CanalUpdateEvent(Entry source) {
        super(source);
    }
}
