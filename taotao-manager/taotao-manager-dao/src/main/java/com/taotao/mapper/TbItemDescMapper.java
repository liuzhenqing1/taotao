package com.taotao.mapper;

import com.taotao.pojo.TbItemDesc;

public interface TbItemDescMapper {
    void insertTbItemDesc(TbItemDesc tbItemDesc);

    /**
     * 根据商品id查询商品信息
     * @param itemId 商品id
     * @return 商品信息对象
     */
    TbItemDesc findItemDescByItemId(Long itemId);
}