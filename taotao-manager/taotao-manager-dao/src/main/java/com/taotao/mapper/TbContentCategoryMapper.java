package com.taotao.mapper;


import com.taotao.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryMapper {
List<TbContentCategory> getContenTCategoryById(Long parentId);
void  addContenteCategory(TbContentCategory tbContentCategory);
TbContentCategory getContenteCategory(Long id);
void updateContentCategory(TbContentCategory tbContentCategory);
}