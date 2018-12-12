package com.struggle.datasource.schedule;

import javax.validation.constraints.NotBlank;

/**
 * Created by jinnvc on 2018/12/12.
 */
public class SyncTableRequest {

    @NotBlank
    private String database;

    @NotBlank
    private String table;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
