package com.taotao.contente.service.impl;



import com.taotao.contente.jedis.JedisClient;
import com.taotao.contente.service.ContentService;
import com.taotao.contente.service.ContenteCategory;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.TaotaoResult;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;
    @Override
    public EasyUIDataGridResult findContentAllById(Long categoryId) {
        List<TbContent> contents = tbContentMapper.findContenteALL(categoryId);
        EasyUIDataGridResult result=new EasyUIDataGridResult(contents.size(),contents);
        return result;
    }

    @Override
    public List<TbContent> findContentsById(Long categoryId) {

        String json = jedisClient.hget(CONTENT_KEY, categoryId + "");
      if(StringUtils.isNotBlank(json)){
            List<TbContent>  tbContents= JsonUtils.jsonToList(json,TbContent.class);
            System.out.println("从缓存中拿到数据");
            return  tbContents;
        }

        List<TbContent> contents = tbContentMapper.findContenteALL(categoryId);
        System.out.println("从数据库中拿到数据");
       jedisClient.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(contents));

        return contents;
    }

    @Override
    public TaotaoResult addContent(TbContent content) {
        Date time=new Date();
        content.setCreated(time);
        content.setUpdated(time);
        tbContentMapper.insertContent(content);
        jedisClient.hdel(CONTENT_KEY,content.getCategoryId().toString());
        return TaotaoResult.ok();
    }
}
