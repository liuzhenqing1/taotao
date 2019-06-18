package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/page")
public class PageController {
@RequestMapping("/login")
    public String showLogin(){
    return "login";
}
    @RequestMapping("/register")
    public String showRegister() {
        return "register";
    }

}
