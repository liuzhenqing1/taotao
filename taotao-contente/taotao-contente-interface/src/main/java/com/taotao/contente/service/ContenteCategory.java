package com.taotao.contente.service;

import com.taotao.pojo.TbContentCategory;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.TaotaoResult;

import java.util.List;

public interface ContenteCategory {
    List<EasyUITreeNode> getCategoryList(Long parentId);

    /**
     * 添加内容分类
     * @param parentId 父类id
     * @param name 名称
     * @return TaotaoResult
     */
    TaotaoResult addContenteCategory(Long parentId, String name);

}
