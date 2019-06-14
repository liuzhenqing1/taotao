package com.taotao.controller;

import com.taotao.contente.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentAll(Long categoryId){
        EasyUIDataGridResult result= contentService.findContentAllById(categoryId);
    return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content){
        TaotaoResult result = contentService.addContent(content);
        return result;
    }

}
