package com.struggle.datasource.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by jinnvc on 2018/12/11.
 */
@Mapper
public interface JdbcDao {
    @Select("select * from datasearch.${table} where id = ${id}")
    Map<String, Object> findById(@Param("table") String table, @Param("id") String id);

    @Select("select * from ${database}.${table}")
    List<Map> findAll(@Param("database") String database, @Param("table") String table);

    @Select("select max(${key}) from ${database}.${table}")
    Long selectMaxPK(@Param("key") String key, @Param("database") String databaseName, @Param("table") String tableName);

    @Select("select min(${key}) from ${database}.${table}")
    Long selectMinPK(@Param("key") String key, @Param("database") String databaseName, @Param("table") String tableName);

    @Select("select * from ${database}.${table} where id between ${start} and ${end}")
    List<Map> findDataList(@Param("database") String database, @Param("table") String table, @Param("start") Long start, @Param("end") Long end);

}
