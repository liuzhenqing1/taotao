package com.taotao.service;

import com.taotao.result.EasyUITreeNode;
import com.taotao.result.ItemCatResult;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getCatList(Long parentId);

    ItemCatResult findItemCatAll(Long parentId);

}
