package com.taotao.mapper;

import com.taotao.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatMapper {
    /**
     *
     * 根据分类id查询分类
     * @param parentId 父级类目id
     * @return 分类
     */
    List<TbItemCat> findTbItemCatByParentId(Long parentId);

}