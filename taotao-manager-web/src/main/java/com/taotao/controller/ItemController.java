package com.taotao.controller;

import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem tbItem = itemService.findTbItemById(itemId);
		return tbItem;
	}
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);

		return result;
	}
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertTbItem(TbItem tbItem,String desc,String itemParams){
		TaotaoResult taotaoResult = itemService.addTbItem(tbItem, desc,itemParams);
		return taotaoResult;
}
}