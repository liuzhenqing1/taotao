package com.taotao.searche.service.impl;

import com.taotao.result.SearchResult;
import com.taotao.searche.dao.SearchDao;
import com.taotao.searche.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
       SolrQuery query=new SolrQuery();
       query.setQuery(queryString);
       //设置分页条件
       query.set("df","item_keywords");
        query.setStart((page-1)*rows);
        query.setRows(rows);
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style='color:red'>");
        query.setHighlightSimplePost("</em>");

        SearchResult result = searchDao.search(query);
        Long recordCount = result.getRecordCount();
        Long pageCount=recordCount%rows==0?recordCount/rows:recordCount/rows+1;
        result.setPageCount(pageCount);
        return result;
    }
}
