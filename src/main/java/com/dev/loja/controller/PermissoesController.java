package com.dev.loja.controller;

import com.dev.loja.entity.Permissoes;
import com.dev.loja.entity.Permissoes;
import com.dev.loja.repository.FuncionarioRepository;
import com.dev.loja.repository.PapelRepository;
import com.dev.loja.repository.EstadoRepository;
import com.dev.loja.repository.PermissaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PermissoesController {

    @Autowired
    private PermissaRepository permissaoRepository;
    
    @Autowired 
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private PapelRepository papelRepository;
 

    @GetMapping("/administrativo/permissoess/cadastrar")
    public ModelAndView cadastrar(Permissoes permissoes){
        ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
        mv.addObject("permissoes", permissoes);
        mv.addObject("listaFuncionario", funcionarioRepository.findAll());
        mv.addObject("listaPapeis", papelRepository.findAll());
        return mv;
    }
    @GetMapping("/administrativo/permissoess/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
        mv.addObject("listaPermissoess", permissaoRepository.findAll());
        return mv;
    }

    @PostMapping("/administrativo/permissoess/salvar")
    public ModelAndView salvar(Permissoes permissoes, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(permissoes);
        }
        permissaoRepository.saveAndFlush(permissoes);
        return cadastrar(new Permissoes());
    }

    @GetMapping("/administrativo/permissoess/editar/{id}")
    public ModelAndView editar(@PathVariable ("id")Long id){
        Optional<Permissoes> permissoes = permissaoRepository.findById(id);
        return cadastrar(permissoes.get());
    }
    @GetMapping("/administrativo/permissoess/remover/{id}")
    public ModelAndView remover(@PathVariable ("id")Long id){
        Optional<Permissoes> permissoes = permissaoRepository.findById(id);
        permissaoRepository.delete(permissoes.get());
        return listar();
    }
}
