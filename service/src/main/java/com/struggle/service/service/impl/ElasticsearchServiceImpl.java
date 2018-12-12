package com.struggle.service.service.impl;

import com.struggle.service.constant.RequestType;
import com.struggle.service.model.Response;
import com.struggle.service.model.bean.ProductInfo;
import com.struggle.service.service.ElasticsearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

    @Resource
    private TransportClient transportClient;

    @Override
    public Response<ProductInfo> searchResult(int type, String content) {
        if (content != null && !content.equals("")) {
            switch (RequestType.getByCode(type)){
                case ORDER_PRICE:
                    logger.info("test");
                    SearchResponse response = transportClient.prepareSearch("search_data")
                            .setQuery(QueryBuilders.termQuery("prod_name", content))
                            .setFrom(0).setSize(60).setExplain(true)
                            .get();
                    logger.info(response.toString());
                    //TODO:parse response.
                    break;
                case ORDER_CREATETIME:
                    logger.info("test");
                    break;
                default:
                    break;

            }
            return null;

        }
        return Response.fail(-1, "query param is error", null);
    }
}
