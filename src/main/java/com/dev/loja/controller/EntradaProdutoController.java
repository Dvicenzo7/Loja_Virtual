package com.dev.loja.controller;

import com.dev.loja.entity.EntradaItens;
import com.dev.loja.entity.EntradaProduto;
import com.dev.loja.entity.Produto;
import com.dev.loja.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaProdutoController {

    private  List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

    @Autowired
    private EntradaProdutoRepository entradaProdutoRepository;

    @Autowired
    private EntradaItensRepository entradaItensRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping("/administrativo/entrada/cadastrar")
    public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens){
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("listaEntradasItens", this.listaEntrada);
        mv.addObject("entradaItens", entradaItens);
        mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
        mv.addObject("listaPodutos", produtoRepository.findAll());
        return mv;
    }
//    @GetMapping("/administrativo/estados/listar")
//    public ModelAndView listar(){
//        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
//        mv.addObject("listaestados", estadoRepository.findAll());
//        return mv;
//    }

    @PostMapping("/administrativo/entrada/salvar")
    public ModelAndView salvar(String acao,EntradaProduto entrada, EntradaItens entradaItens){

        if (acao.equals("itens")) {
            this.listaEntrada.add(entradaItens);
        } else if (acao.equals("salvar")) {
            entradaProdutoRepository.save(entrada);
            for (EntradaItens it : listaEntrada){
                it.setEntrada(entrada);
                entradaItensRepository.saveAndFlush(it);
                Optional<Produto> produto  = produtoRepository.findById(it.getProduto().getId());
                Produto produto1 = produto.get();
                produto1.setQuantidadeEstoque(produto1.getQuantidadeEstoque() + it.getQuantidade());
                produto1.setValorVenda(it.getValorVenda());
                produtoRepository.saveAndFlush(produto1);
                this.listaEntrada = new ArrayList<>();
            }
            return cadastrar(new EntradaProduto(),new EntradaItens());
        }
        return cadastrar(entrada, new EntradaItens());
    }

//    @GetMapping("/administrativo/estados/editar/{id}")
//    public ModelAndView editar(@PathVariable ("id")Long id){
//        Optional<Estado> estado = estadoRepository.findById(id);
//        return cadastrar(estado.get());
//    }
//    @GetMapping("/administrativo/estados/remover/{id}")
//    public ModelAndView remover(@PathVariable ("id")Long id){
//        Optional<Estado> estado = estadoRepository.findById(id);
//        estadoRepository.delete(estado.get());
//        return listar();
//    }
}
