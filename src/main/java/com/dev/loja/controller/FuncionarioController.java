package com.dev.loja.controller;

import com.dev.loja.entity.Funcionario;
import com.dev.loja.repository.CidadeRepository;
import com.dev.loja.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    CidadeRepository cidadeRepository;


    @GetMapping("/administrativo/funcionarios/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("administrativo/usuarios/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }
    @GetMapping("/administrativo/funcionarios/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/usuarios/lista");
        mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
        return mv;
    }

    @PostMapping("/administrativo/funcionarios/salvar")
    public ModelAndView salvar(Funcionario funcionario, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(funcionario);
        }
//        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
        funcionarioRepository.saveAndFlush(funcionario);
        return cadastrar(new Funcionario());
    }

    @GetMapping("/administrativo/funcionarios/editar/{id}")
    public ModelAndView editar(@PathVariable ("id")Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return cadastrar(funcionario.get());
    }
    @GetMapping("/administrativo/funcionarios/remover/{id}")
    public ModelAndView remover(@PathVariable ("id")Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        funcionarioRepository.delete(funcionario.get());
        return listar();
    }
}
