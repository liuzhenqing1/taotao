package com.taotao.searche.dao;

import com.taotao.result.SearchItem;
import com.taotao.result.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;
   public SearchResult search(SolrQuery query) throws Exception {
       SearchResult result=new SearchResult();
       QueryResponse response = solrServer.query(query);
       SolrDocumentList documentList = response.getResults();
       result.setRecordCount(documentList.getNumFound());
       Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
      List<SearchItem> itemList=new ArrayList<SearchItem>();
       for(SolrDocument document:documentList){
           SearchItem item=new SearchItem();
           item.setId((String) document.get("id"));
           item.setPrice((Long) document.get("item_price"));
           item.setImage((String) document.get("item_image"));
           item.setSellPoint((String) document.get("item_sell_point"));
           item.setCategoryName((String) document.get("item_category_name"));
           item.setItemDesc((String) document.get("item_desc"));
           List<String> list = highlighting.get(document.get("id")).get("item_title");
           if(list!=null&&list.size()>0){
               item.setTitle(list.get(0));
           }else {
               item.setTitle((String) document.get("item_title"));
           }
           itemList.add(item);
       }
       result.setItemList(itemList);
       return result;
   }

}
