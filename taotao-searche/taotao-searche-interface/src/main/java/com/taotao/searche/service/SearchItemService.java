package com.taotao.searche.service;

import com.taotao.pojo.TbItem;
import com.taotao.result.SearchItem;
import com.taotao.result.TaotaoResult;


public interface SearchItemService {
    TaotaoResult importAllItem() throws Exception;

    SearchItem findItemById(Long id);
}
