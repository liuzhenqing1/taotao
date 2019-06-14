package com.taotao.searche.service;

import com.taotao.result.SearchResult;

public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
