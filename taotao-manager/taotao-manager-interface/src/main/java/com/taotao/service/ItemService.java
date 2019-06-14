package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.TaotaoResult;

public interface ItemService {
	/**
	 * 查询得到商品信息
	 * @param itemId 商品id
	 * @return 指定商品id的商品信息
	 */
	TbItem findTbItemById(Long itemId);

	/**
	 * 商品列表展示
	 * @param page 商品页数
	 * @param rows 每页商品条数
	 * @return
	 */
	EasyUIDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 添加商品信息
	 * @param tbItem 商品对象
	 * @param desc  商品描述信息
	 * @return  返回200成功
 	 */
	TaotaoResult addTbItem(TbItem tbItem,String desc,String itemParams);

	/**
	 * 根据itemId查询商品描述信息
	 * @param itemId 商品id
	 * @return 商品描述对象
	 */
	TbItemDesc findItemDescByItemId(Long itemId);

	/**
	 * 根据商品id查询商品规格参数
	 * @param itemId 商品id
	 * @return 商品规格参数
	 */
    String findItemParamById(Long itemId);
}
