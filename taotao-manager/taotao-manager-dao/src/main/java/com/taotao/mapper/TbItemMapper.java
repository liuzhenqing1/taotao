package com.taotao.mapper;

import com.taotao.pojo.TbItem;

import java.util.List;

public interface TbItemMapper {
	/**
	 * 根据商品id查询指定商品信息
	 * @param itemId 商品id
	 * @return	返回指定商品id的商品信息
	 */
 	TbItem findTbItemById(Long itemId);
	List<TbItem> findTbItems();
	void insertTbItem(TbItem tbItem);
}