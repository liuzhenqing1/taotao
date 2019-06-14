package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.result.TaotaoResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
@Autowired
private ItemService itemService;

    @RequestMapping("/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        TbItem tbItem = itemService.findTbItemById(itemId);
        Item item=new Item(tbItem);
        model.addAttribute("item",item);
        return "item";
    }
    @RequestMapping("/desc/{itemId}")
    @ResponseBody //返回如果是一个对象 则由jacson 的jar包转换成json数据，如果返回时string则把数据输出到http的body里面去
    public String showItemDesc(@PathVariable Long itemId){
     TbItemDesc itemDesc =itemService.findItemDescByItemId(itemId);

        return itemDesc.getItemDesc();
    }
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public String showItemParam(@PathVariable Long itemId){
        String tbItemParam=itemService.findItemParamById(itemId);
        return tbItemParam;
    }
}
