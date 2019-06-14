package com.taotao.searche.dao;

import com.taotao.result.SearchItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SearchItemMapper {
    @Select("SELECT a.id,a.title,a.sellPoint,a.price,a.image,b.name categoryName,c.itemDesc FROM tbitem a INNER JOIN tbitemcat b ON a.cid=b.id INNER JOIN tbitemdesc c ON a.id=c.itemId WHERE a.status=1")
    List<SearchItem> findAllItems();
    @Select("SELECT a.id,a.title,a.sellPoint,a.price,a.image,b.name categoryName,c.itemDesc FROM tbitem a INNER JOIN tbitemcat b ON a.cid=b.id INNER JOIN tbitemdesc c ON a.id=c.itemId WHERE a.id=#{a.id}")
    SearchItem getItemById(Long id);
}
