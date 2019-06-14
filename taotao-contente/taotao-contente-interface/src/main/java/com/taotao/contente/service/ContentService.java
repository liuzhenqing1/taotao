package com.taotao.contente.service;

import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.TaotaoResult;

import java.util.List;

public interface ContentService {
    EasyUIDataGridResult findContentAllById(Long categoryId);
    List<TbContent> findContentsById(Long categoryId);
    TaotaoResult addContent(TbContent content);
}
