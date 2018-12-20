package com.struggle.service.service;

import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;

/**
 * 类的描述
 *
 * @author nancy.wang
 * @Time 2018/12/14
 */
public interface  SearchRequestBuildService {
    public FunctionScoreQueryBuilder buildFuncScoreQuery(String content);
}
