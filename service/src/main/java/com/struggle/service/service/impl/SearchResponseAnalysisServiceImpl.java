package com.struggle.service.service.impl;

import com.struggle.service.model.bean.ProductInfo;
import com.struggle.service.service.SearchResponseAnalysisService;
import com.struggle.service.util.json.JsonUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchResponseAnalysisServiceImpl implements SearchResponseAnalysisService<ProductInfo>{

    private static final Logger logger = LoggerFactory.getLogger(SearchResponseAnalysisServiceImpl.class);

    @Override
    public List<ProductInfo> analysisResponse(SearchResponse response) {
        if(response != null && response.getHits().getHits() != null){
            List<ProductInfo> productList = new ArrayList<ProductInfo>();
            for(SearchHit hit: response.getHits().getHits()){
                productList.add(JsonUtils.toObject(hit.getSourceAsString(), ProductInfo.class));
            }
            return productList;
        }
        return null;
    }
}
