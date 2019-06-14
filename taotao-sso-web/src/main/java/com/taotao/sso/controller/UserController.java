package com.taotao.sso.controller;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Action;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUser(@PathVariable String param,@PathVariable int type){
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
    public TaotaoResult login(String userName,String passWord){
        TaotaoResult result = userService.login(userName, passWord);
        return  result;
    }
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult getUserByToken(@PathVariable String token){
        TaotaoResult result = userService.getUserByToken(token);
        return result;
    }
    @RequestMapping(value = "logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logoutUser(@PathVariable String token){
        TaotaoResult result = userService.logoutUser(token);
        return result;
    }
}
