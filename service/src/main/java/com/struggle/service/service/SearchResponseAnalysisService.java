package com.struggle.service.service;

import org.elasticsearch.action.search.SearchResponse;
import java.util.List;


public interface SearchResponseAnalysisService<T extends Object> {
    public List<T> analysisResponse(SearchResponse request);
}
