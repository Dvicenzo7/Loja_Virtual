package com.dev.loja.controller;

import com.dev.loja.entity.Produto;
import com.dev.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/administrativo/produtos/cadastrar")
    public ModelAndView cadastrar(Produto produto){
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }
    @GetMapping("/administrativo/produtos/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaprodutos", produtoRepository.findAll());
        return mv;
    }

    @PostMapping("/administrativo/produtos/salvar")
    public ModelAndView salvar(Produto produto, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(produto);
        }
        produtoRepository.saveAndFlush(produto);
        return cadastrar(new Produto());
    }

    @GetMapping("/administrativo/produtos/editar/{id}")
    public ModelAndView editar(@PathVariable ("id")Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return cadastrar(produto.get());
    }
    @GetMapping("/administrativo/produtos/remover/{id}")
    public ModelAndView remover(@PathVariable ("id")Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        produtoRepository.delete(produto.get());
        return listar();
    }
}
