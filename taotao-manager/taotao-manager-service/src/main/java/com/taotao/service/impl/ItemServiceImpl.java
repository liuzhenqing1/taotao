package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.result.EasyUIDataGridResult;
import com.taotao.result.TaotaoResult;
import com.taotao.service.jedis.JedisClient;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

import javax.jms.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${BASE}")
	private String BASE;
	@Value("${DESC}")
	private String DESC;
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination topicDestination;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public TbItem findTbItemById(Long itemId) {
	//取出缓存
		String json = jedisClient.get(ITEM_INFO + ":" + itemId + BASE);
		if (StringUtils.isNotBlank(json)){
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			System.out.println("从缓存中获取基本信息");
			return tbItem;
		}

		TbItem tbItem = tbItemMapper.findTbItemById(itemId);
		System.out.println("从数据库中获取基本信息");
		//加入缓存
		jedisClient.set(ITEM_INFO + ":" + itemId + BASE, JsonUtils.objectToJson(tbItem));
		jedisClient.expire(ITEM_INFO + ":" + itemId + BASE,EXPIRE_TIME);

		return tbItem;
	}
	@Override
	public TbItemDesc findItemDescByItemId(Long itemId) {
		String json = jedisClient.get(ITEM_INFO + ":" + itemId + DESC);
		if (StringUtils.isNotBlank(json)){
			TbItemDesc itemDesc = JsonUtils.jsonToPojo(json,TbItemDesc.class);
			return itemDesc;
		}

		TbItemDesc itemDesc=tbItemDescMapper.findItemDescByItemId(itemId);
		//加入缓存
		jedisClient.set(ITEM_INFO + ":" + itemId + DESC, JsonUtils.objectToJson(itemDesc));
		jedisClient.expire(ITEM_INFO + ":" + itemId + DESC,EXPIRE_TIME);
		return itemDesc;
	}

	@Override
	public String findItemParamById(Long itemId) {
		TbItemParamItem tbItemParamItem=tbItemParamItemMapper.getItemParamById(itemId);
		// 把规格参数json数据转换成java对象
		String paramData = tbItemParamItem.getParamData();
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for(Map m1:jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("            <td>"+m2.get("v")+"</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");

		return sb.toString() ;
	}

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		PageHelper.startPage(page,rows);

		List<TbItem> tbItems = tbItemMapper.findTbItems();
		//根据 当前页和 分页条数 创建出一个当前页 指定条信息的商品集合对象
		PageInfo<TbItem> pageInfos=new PageInfo<TbItem>(tbItems);
		EasyUIDataGridResult result=new EasyUIDataGridResult(pageInfos.getTotal(),tbItems);

			return result;
	}

	@Override
	public TaotaoResult addTbItem(TbItem tbItem,String desc,String itemParams) {
		final Long id=IDUtils.genItemId();
		tbItem.setId(id);
		tbItem.setStatus((byte)1);
		Date time=new Date();
		tbItem.setCreated(time);
		tbItem.setUpdated(time);
		tbItemMapper.insertTbItem(tbItem);
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setCreated(time);
		tbItemDesc.setUpdated(time);
		tbItemDesc.setItemDesc(desc);
		tbItemDescMapper.insertTbItemDesc(tbItemDesc);
		TbItemParamItem tbItemParamItem=new TbItemParamItem();

		tbItemParamItem.setCreated(time);
		tbItemParamItem.setUpdated(time);
		tbItemParamItem.setItemId(id);
		tbItemParamItem.setParamData(itemParams);

		tbItemParamItemMapper.insertItemParam(tbItemParamItem);
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(id+"");
				return textMessage;
			}
		});

		return TaotaoResult.ok();
	}


}
