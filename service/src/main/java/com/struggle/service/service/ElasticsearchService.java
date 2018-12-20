package com.struggle.service.service;

import com.struggle.service.model.Response;
import com.struggle.service.model.bean.ProductInfo;

import java.util.List;

public interface ElasticsearchService {

    Response<List<ProductInfo>> searchResult(int type, String content);
}
