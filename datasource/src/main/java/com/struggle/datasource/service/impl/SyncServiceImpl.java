package com.struggle.datasource.service.impl;

import com.struggle.datasource.dao.JdbcDao;
import com.struggle.datasource.schedule.SyncTableRequest;
import com.struggle.datasource.service.ElasticsearchService;
import com.struggle.datasource.service.SyncService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by jinnvc on 2018/12/12.
 */
@Service
public class SyncServiceImpl implements SyncService, InitializingBean, Destroyable {
    private static final int STEP = 1000;

    @Resource
    JdbcDao jdbcDao;

    @Resource
    ElasticsearchService elasticsearchService;

    ExecutorService threadPool;

    @Override
    public void syncDataByTable(SyncTableRequest syncTableRequest) {
        String database = syncTableRequest.getDatabase();
        String table = syncTableRequest.getTable();
        List list = jdbcDao.findAll(database, table);
        elasticsearchService.batchInsert("test-search", "doc", "id", list);
//        elasticsearchService.batchInsert(database, table, "id", list);
    }

    @Override
    public void syncDataByTableByThreadpool(SyncTableRequest syncTableRequest) {
        String database = syncTableRequest.getDatabase();
        String table = syncTableRequest.getTable();
        Long minId = jdbcDao.selectMinPK("id", database, table);
        Long maxId = jdbcDao.selectMaxPK("id", database, table);


        Long tempId = minId;
        while (tempId <= maxId){
            final Long finalTempId = tempId;
            threadPool.submit(()->{
                Long value = finalTempId + STEP;
                List list = jdbcDao.findDataList(database, table, finalTempId, value);
//                elasticsearchService.batchInsert(database, table, "id", list);
                elasticsearchService.batchInsert("test-search", "doc", "id", list);
                System.out.println(Thread.currentThread().getName() + "from " + finalTempId + "to " + value);
            });
            tempId = tempId + STEP;
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.threadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
    }

    @Override
    public void destroy() throws DestroyFailedException {
        this.threadPool.shutdown();
    }
}
