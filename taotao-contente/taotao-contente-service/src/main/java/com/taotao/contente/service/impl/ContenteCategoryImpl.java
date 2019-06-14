package com.taotao.contente.service.impl;

import com.taotao.contente.service.ContenteCategory;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContenteCategoryImpl implements ContenteCategory {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUITreeNode> getCategoryList(Long parentId) {
       List<EasyUITreeNode> result=new ArrayList<EasyUITreeNode>();
        List<TbContentCategory> categorys= tbContentCategoryMapper.getContenTCategoryById(parentId);
        for (TbContentCategory category:categorys) {
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent() ? "closed":"open");
            result.add(node);
        }
        return result;
    }

    @Override
    public TaotaoResult addContenteCategory(Long parentId, String name) {
      TbContentCategory contentCategory=new TbContentCategory();
      contentCategory.setName(name);
      contentCategory.setSortOrder(1);
      contentCategory.setStatus(1);
      contentCategory.setParentId(parentId);
      contentCategory.setIsParent(false);
      Date date=new Date();
      contentCategory.setCreated(date);
      contentCategory.setUpdated(date);
      tbContentCategoryMapper.addContenteCategory(contentCategory);
        TbContentCategory paContentCategory = tbContentCategoryMapper.getContenteCategory(parentId);
    if(!paContentCategory.getIsParent()){
        paContentCategory.setIsParent(true);
    tbContentCategoryMapper.updateContentCategory(paContentCategory);
    }

        return TaotaoResult.ok(paContentCategory);
    }
}
