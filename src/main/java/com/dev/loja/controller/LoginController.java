package com.dev.loja.controller;

import com.dev.loja.entity.Cidade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView cadastrar(Cidade cidade){
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }
}
