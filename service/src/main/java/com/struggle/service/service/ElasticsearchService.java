package com.struggle.service.service;

import com.struggle.service.model.Response;
import com.struggle.service.model.bean.ProductInfo;

public interface ElasticsearchService {

    Response<ProductInfo> searchResult(int type, String content);
}
