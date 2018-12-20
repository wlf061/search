package com.struggle.service.service.impl;

import com.struggle.service.constant.RequestType;
import com.struggle.service.model.Response;
import com.struggle.service.model.bean.ProductInfo;
import com.struggle.service.service.ElasticsearchService;
import com.struggle.service.service.SearchRequestBuildService;
import com.struggle.service.service.SearchResponseAnalysisService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;


@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

    @Resource
    private TransportClient transportClient;
    @Resource
    private SearchRequestBuildService searchRequestBuildService;

    @Resource
    private SearchResponseAnalysisService searchResponseAnalysisService;

    @Override
    public Response<List<ProductInfo>> searchResult(int type, String content) {
        if (content != null && !content.equals("")) {
            switch (RequestType.getByCode(type)) {
                case ORDER_PRICE:
                    //TODO:parse response.
                    break;
                case ORDER_CREATETIME:
                    break;
                case ORDER_COMPLEX:
                    SearchResponse response = transportClient.prepareSearch("search_data")
                            .setQuery(searchRequestBuildService.buildFuncScoreQuery(content))
                            .setFrom(0).setSize(10)
                            .get();
                    List<ProductInfo> productInfoList = searchResponseAnalysisService.analysisResponse(response);
                    return Response.success(0,"success",productInfoList);
                case ORDER_COMPLEX_MODEL:
                    break;
                default:
                    break;

            }
            return null;

        }
        return Response.fail(-1, "query param is error", null);
    }
}
