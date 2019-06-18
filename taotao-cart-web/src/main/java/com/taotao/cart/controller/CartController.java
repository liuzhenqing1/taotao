package com.taotao.cart.controller;

import com.taotao.pojo.TbItem;

import com.taotao.result.TaotaoResult;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Value("${TT_CART}")
    private String TT_CART;
    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
        List<TbItem> cartList = getCartList(request);
       boolean flag=false;
        for (TbItem tbItem:cartList) {
            if(tbItem.getId()==itemId){
              tbItem.setNum(tbItem.getNum()+num);
              flag=true;
              break;
            }
        }
    if (!flag){
        TbItem item = itemService.findTbItemById(itemId);
        String image= item.getImage();
        if (StringUtils.isNotBlank(image)){
            String[] images = image.split(",");
            item.setImage(images[0]);
        }
        item.setNum(num);
        cartList.add(item);

    }
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
        return "cartSuccess";
    }
    public List<TbItem> getCartList(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        if(StringUtils.isNotBlank(json)){
            List<TbItem> result = JsonUtils.jsonToList(json, TbItem.class);
            return  result;
        }
        return new ArrayList<TbItem>();
    }
    @RequestMapping("/cart")
    public String showCartList(HttpServletRequest request, Model model){
        List<TbItem> cartList = getCartList(request);
        model.addAttribute("cartList",cartList);
        return "cart";
    }
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateNum(HttpServletRequest request,HttpServletResponse response,
                                  @PathVariable Long itemId,@PathVariable Integer num){
        List<TbItem> cartList = getCartList(request);
        for (TbItem item:cartList) {
            if (item.getId()==itemId.longValue()){
                item.setNum(num);
                break;
            }
        }

        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
        return TaotaoResult.ok();
    }
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletResponse response,HttpServletRequest request){
        List<TbItem> cartList = getCartList(request);
        for (int i = 0; i <cartList.size(); i++) {
            if (cartList.get(i).getId()==itemId.longValue()){
                cartList.remove(i);
                break;
            }
        }
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
       //注意要重定向到cart方法里去
        return "cart";
    }

}
