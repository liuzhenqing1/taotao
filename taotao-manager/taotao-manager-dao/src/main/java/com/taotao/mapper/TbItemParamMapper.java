package com.taotao.mapper;


import com.taotao.pojo.TbItemParam;

public interface TbItemParamMapper {
    /**
     * 根据你分类id查询该分类的规格参数模板
     * @param itemCatId分类id
     * @return 该分类的规格参数模板
     */
    TbItemParam getItemParamByCid(Long itemCatId);

    /**
     *  根据商品类的分类id插入该商品分类的模板
     * @param tbItemParam 规格参数模板对象
     */
    void insertItemParam(TbItemParam tbItemParam);

    /**
     * g根据商品id查询商品规格参数
     * @param itemId
     * @return
     */

}