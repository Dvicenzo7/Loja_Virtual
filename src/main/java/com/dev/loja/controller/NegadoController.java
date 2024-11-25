package com.dev.loja.controller;

import com.dev.loja.entity.Funcionario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class NegadoController {

    @GetMapping("/negado")
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("/negado");
        return mv;
    }
}
