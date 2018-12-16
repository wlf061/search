package com.struggle.service.service.impl;

import com.struggle.service.service.SearchRequestBuildService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.fieldValueFactorFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.weightFactorFunction;

/**
 * 类的描述
 *
 * @author nancy.wang
 * @Time 2018/12/14
 */
@Service
public class SearchRequestBuildServiceImpl implements SearchRequestBuildService{

    @Override
    public FunctionScoreQueryBuilder buildFuncScoreQuery(String keyWord) {
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        termQuery("prod_name", keyWord),
                        weightFactorFunction(4)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        termQuery("prod_desc", keyWord),
                        weightFactorFunction(2)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        termQuery("prod_merchant", keyWord),
                        weightFactorFunction(1)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(fieldValueFactorFunction("prod_star"))
        };
       return  QueryBuilders.functionScoreQuery(QueryBuilders.boolQuery()
                .should(termQuery("prod_name", keyWord))
                .should(termQuery("prod_desc", keyWord))
                .should(termQuery("prod_merchant", keyWord))
                .minimumShouldMatch(1), functions);
    }
}
