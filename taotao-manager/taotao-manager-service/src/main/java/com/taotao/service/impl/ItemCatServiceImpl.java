package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.ItemCat;
import com.taotao.result.ItemCatResult;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EasyUITreeNode> getCatList(Long parentId) {
        List<TbItemCat> tbItemCats = tbItemCatMapper.findTbItemCatByParentId(parentId);
        List<EasyUITreeNode> results=new ArrayList<EasyUITreeNode>();
        for (TbItemCat tbItem:tbItemCats) {
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(tbItem.getId());
            node.setText(tbItem.getName());
            node.setState(tbItem.getIsParent()?"closed":"open");
            results.add(node);
        }
        return results;
    }

    @Override
    public ItemCatResult findItemCatAll(Long parentId) {
        ItemCatResult result=new ItemCatResult();
        result.setData(getItemAll(parentId));
        return result;
    }



    private List<ItemCat> getItemAll(Long parentId) {
        List results=new ArrayList();

        List<TbItemCat> itemCats = tbItemCatMapper.findTbItemCatByParentId(parentId);
        int count=0;
        for(TbItemCat tbItemCat:itemCats){
            ItemCat itemCat=new ItemCat();
            itemCat.setUrl("/products/"+tbItemCat.getId()+".html");
       if (tbItemCat.getIsParent()) {
           if (parentId == 0) {

               itemCat.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");

           } else {
               itemCat.setName(tbItemCat.getName());

           }

           count++;
           //递归 这里在递归 如果在递归说明这里是第一级或第二级目录
           itemCat.setItem(getItemAll(tbItemCat.getId()));
           results.add(itemCat);
           if ( parentId==0&&count>=14){
               break;
           }
       }else {
        results.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
       }
        }
        return results;
    }
}
