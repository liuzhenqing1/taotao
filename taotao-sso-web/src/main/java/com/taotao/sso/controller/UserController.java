package com.taotao.sso.controller;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${TT_TOKEN}")
    private String TT_TOKEN;
    @Autowired
    private UserService userService;
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUser(@PathVariable String param,@PathVariable int type,String callback){
        TaotaoResult result = userService.checkUser(param, type);

        return result;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult creatUser(TbUser tbUser){
        TaotaoResult result = userService.creatUser(tbUser);
        return result;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String userName, String passWord, HttpServletResponse response, HttpServletRequest request){
        TaotaoResult result = userService.login(userName, passWord);
       if(result.getStatus()==200){
           CookieUtils.setCookie(request,response,TT_TOKEN,result.getData().toString());
       }

        return  result;
    }
    @RequestMapping(value = "/token/{token}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result = userService.getUserByToken(token);
        String res;
        if(result.getStatus()==400){
            res =JsonUtils.objectToJson(result.getData());
            return res;
        }
        if(StringUtils.isNotBlank(callback)){
            String jsonp=callback+"("+JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }
    @RequestMapping(value = "logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logoutUser(@PathVariable String token){
        TaotaoResult result = userService.logoutUser(token);
        return result;
    }
}
