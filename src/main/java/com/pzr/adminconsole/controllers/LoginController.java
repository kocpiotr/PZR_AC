package com.pzr.adminconsole.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @RequestMapping(value = {"/", "/index.html"})
    public String authorize(){
        return "index";
    }

    @RequestMapping("/login.html")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register.html")
    public String registerPage() {
        return "register";
    }

}
