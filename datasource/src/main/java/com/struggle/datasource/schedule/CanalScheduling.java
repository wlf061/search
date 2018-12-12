package com.struggle.datasource.schedule;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.Message;
import com.struggle.datasource.event.CanalDeleteEvent;
import com.struggle.datasource.event.CanalInsertEvent;
import com.struggle.datasource.event.CanalUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CanalScheduling implements Runnable, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(CanalScheduling.class);
    private ApplicationContext applicationContext;

    @Resource
    private CanalConnector canalConnector;

    @Scheduled(fixedDelay = 500)
    @Override
    public void run() {
        /*每隔1秒执行*/
        try {
            int batchSize = 1000;
            Message message = canalConnector.getWithoutAck(batchSize);
            long batchId = message.getId();
            logger.debug("scheduled_batchId=" + batchId);
            try {
                List<Entry> entries = message.getEntries();
                if (batchId != -1 && entries.size() > 0) {
                    entries.forEach(entry -> {
                        if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                            //TODO: send event to the listener
                            publishEvent(entry);
                        }
                    });
                }
                canalConnector.ack(batchId);
            } catch (Exception e) {
                logger.error("发送监听事件失败！batchId回滚,batchId=" + batchId, e);
                canalConnector.rollback(batchId);
            }
        } catch (Exception e) {
            logger.error("canal_scheduled异常！", e);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void publishEvent(Entry entry){
        EventType type = entry.getHeader().getEventType();
        switch (type){
            case UPDATE:
                applicationContext.publishEvent(new CanalUpdateEvent(entry));
                break;
            case INSERT:
                applicationContext.publishEvent(new CanalInsertEvent(entry));
                break;
            case DELETE:
                applicationContext.publishEvent(new CanalDeleteEvent(entry));
                break;
             default:
                 break;
        }

    }
}
