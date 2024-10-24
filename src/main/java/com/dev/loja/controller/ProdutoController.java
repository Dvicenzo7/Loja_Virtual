package com.dev.loja.controller;

import com.dev.loja.entity.Produto;
import com.dev.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProdutoController {

    private static String caminhoImagens = "C:/Users/User/Downloads/imagens/";

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
    public ModelAndView salvar(Produto produto, BindingResult result, @RequestParam("file") MultipartFile arquivo){
        if(result.hasErrors()){
            return cadastrar(produto);
        }

        produtoRepository.saveAndFlush(produto);
        try{
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoImagens+String.valueOf(produto.getId())+arquivo.getOriginalFilename());
                Files.write(caminho, bytes);
                produto.setNomeImagem(String.valueOf(produto.getId())+arquivo.getOriginalFilename());
                System.out.println(caminhoImagens);
                produtoRepository.saveAndFlush(produto);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


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

    @GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] mostrarImagem(@PathVariable ("imagem")String imagem){
        File imagemArquivo = new File(caminhoImagens+imagem);
       if (imagem != null || imagem.trim().length()>0) {
           try {
               return Files.readAllBytes(imagemArquivo.toPath());
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
       return null;
    }


}
