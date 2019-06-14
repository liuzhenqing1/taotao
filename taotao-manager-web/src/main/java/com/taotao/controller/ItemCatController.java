package com.taotao.controller;

import com.taotao.result.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
  @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    //页面传过来一个id 默认为0
    public List<EasyUITreeNode> getCategoryList(@RequestParam(value ="id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> catList = itemCatService.getCatList(parentId);
        return  catList;
    }
}
