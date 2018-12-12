package com.struggle.datasource.controller;

import com.struggle.datasource.dao.JdbcDao;
import com.struggle.datasource.schedule.SyncTableRequest;
import com.struggle.datasource.service.ElasticsearchService;
import com.struggle.datasource.service.SyncService;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinnvc on 2018/12/11.
 */

@RestController
@RequestMapping("/sync")
public class SyncTableController {

    @Resource
    SyncService syncService;

    @RequestMapping(value="search")
    @ResponseBody
    public String showSpringBootId(@RequestParam("id") String id, @RequestParam("name") String name){
        return "hello spring boot + " + id + " - " + name;

    }

    @RequestMapping("/bytable")
    @ResponseBody
    public String syncTable(@Validated SyncTableRequest syncTableRequest){
        syncService.syncDataByTable(syncTableRequest);
        return syncTableRequest.getDatabase() + "/" + syncTableRequest.getTable();
    }

    @RequestMapping("/bytableStep")
    @ResponseBody
    public String syncTableStep(@Validated SyncTableRequest syncTableRequest){
        syncService.syncDataByTableByThreadpool(syncTableRequest);
        return syncTableRequest.getDatabase() + "/" + syncTableRequest.getTable();
    }
}

