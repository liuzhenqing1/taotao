package com.taotao.controller;

import com.taotao.contente.service.ContenteCategory;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.TaotaoResult;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContenteCategoryController {
    @Autowired
    private ContenteCategory contenteCategory;
   @RequestMapping("/list")
   @ResponseBody
    public List<EasyUITreeNode> getCategoryList(@RequestParam(value= "id" ,defaultValue = "0") Long parentId){
       List<EasyUITreeNode> result = contenteCategory.getCategoryList(parentId);
       return result;
   }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult addContentCategory(Long parentId,String name){
        TaotaoResult result = contenteCategory.addContenteCategory(parentId, name);
        return result;
    }

}
