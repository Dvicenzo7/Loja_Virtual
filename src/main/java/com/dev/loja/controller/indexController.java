package com.dev.loja.controller;

import com.dev.loja.entity.Funcionario;
import com.dev.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }


}
