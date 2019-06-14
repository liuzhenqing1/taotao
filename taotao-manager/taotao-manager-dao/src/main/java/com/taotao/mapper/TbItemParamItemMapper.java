package com.taotao.mapper;


import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;

public interface TbItemParamItemMapper {
    /**
     * 根据商品id查询商品规格参数
     * @param itemId
     * @return
     */


    /**
     * 插入一条商品规格参数
     * @param tbItemParamItem
     */
    void insertItemParam(TbItemParamItem tbItemParamItem);

    TbItemParamItem getItemParamById(Long itemId);
}