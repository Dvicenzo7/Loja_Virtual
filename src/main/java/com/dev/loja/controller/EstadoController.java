package com.dev.loja.controller;

import com.dev.loja.entity.Estado;
import com.dev.loja.entity.Estado;
import com.dev.loja.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/administrativo/estados/cadastrar")
    public ModelAndView cadastrar(Estado estado){
        ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
        mv.addObject("estado", estado);
        return mv;
    }
    @GetMapping("/administrativo/estados/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
        mv.addObject("listaestados", estadoRepository.findAll());
        return mv;
    }

    @PostMapping("/administrativo/estados/salvar")
    public ModelAndView salvar(Estado estado, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(estado);
        }
        estadoRepository.saveAndFlush(estado);
        return cadastrar(new Estado());
    }

    @GetMapping("/administrativo/estados/editar/{id}")
    public ModelAndView editar(@PathVariable ("id")Long id){
        Optional<Estado> estado = estadoRepository.findById(id);
        return cadastrar(estado.get());
    }
    @GetMapping("/administrativo/estados/remover/{id}")
    public ModelAndView remover(@PathVariable ("id")Long id){
        Optional<Estado> estado = estadoRepository.findById(id);
        estadoRepository.delete(estado.get());
        return listar();
    }
}
