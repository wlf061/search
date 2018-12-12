package com.struggle.datasource.service;

import com.struggle.datasource.schedule.SyncTableRequest;

/**
 * Created by jinnvc on 2018/12/12.
 */
public interface SyncService {
    void syncDataByTable(SyncTableRequest syncTableRequest);

    void syncDataByTableByThreadpool(SyncTableRequest syncTableRequest);
}
