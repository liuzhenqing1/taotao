package com.taotao.controller;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParam;
import com.taotao.result.TaotaoResult;
import com.taotao.service.ItemParamService;
import com.taotao.service.ItemService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
  @Autowired
  private ItemParamService itemParamService;
    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId){
        TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
        return result;
    }
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult addItemParam(@PathVariable Long cid,String paramData){
        TbItemParam tbItemParam=new TbItemParam();
        tbItemParam.setItemcatId(cid);
        tbItemParam.setParamData(paramData);
        TaotaoResult result=itemParamService.addItemParam(tbItemParam);
        return result;
    }
}
