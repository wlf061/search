package com.struggle.service.controller;


import com.struggle.service.model.Response;
import com.struggle.service.model.bean.ProductInfo;
import com.struggle.service.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/search")
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Resource
    private ElasticsearchService elasticsearchService;

    @RequestMapping("/queryContent")
    @ResponseBody
    public Response<ProductInfo> queryContent(@RequestParam(value = "type") int type, @RequestParam(value = "content") String content) {
        return elasticsearchService.searchResult(type, content);
    }

}


