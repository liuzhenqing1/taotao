package com.taotao.service;

import com.taotao.pojo.TbItemParam;
import com.taotao.result.TaotaoResult;

public interface ItemParamService {
    /**
     * 根据分类id查询该分类的规格参数模板
     * @param itemCatId 分类id
     * @return 返回200有此参数
     */
    TaotaoResult getItemParamByCid(Long itemCatId);

    /**
     * 添加规格参数模板
     * @param tbItemParam 规格参数模板对象
     * @return TaotaoResult返回200 成功
     */
    TaotaoResult addItemParam(TbItemParam tbItemParam);
}
