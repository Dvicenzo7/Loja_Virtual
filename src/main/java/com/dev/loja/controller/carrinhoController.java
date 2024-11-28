package com.dev.loja.controller;

import com.dev.loja.entity.Cliente;
import com.dev.loja.entity.Compra;
import com.dev.loja.entity.ItensCompra;
import com.dev.loja.entity.Produto;
import com.dev.loja.repository.ClienteRepository;
import com.dev.loja.repository.CompraRepository;
import com.dev.loja.repository.ItensCompraRepository;
import com.dev.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class carrinhoController {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CompraRepository compraRepository;
    @Autowired
    ItensCompraRepository itensCompraRepository;

    private List<ItensCompra> itensCompras = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente;

    private void calcularTotal(){
        compra.setValorTotal(0.);
        for (ItensCompra itensCompra : itensCompras) {
            compra.setValorTotal(compra.getValorTotal() + itensCompra.getValorTotal());
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho(){
        ModelAndView mv = new ModelAndView("carrinho");
        calcularTotal();
        mv.addObject("compra",compra);
        mv.addObject("listaItens", itensCompras);
        return mv;
    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra(){
        buscarUsuarioLogado();
        ModelAndView mv = new ModelAndView("finalizar");
        calcularTotal();
        mv.addObject("cliente", cliente);
        mv.addObject("compra",compra);
        mv.addObject("listaItens", itensCompras);
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao){

        for (ItensCompra it:itensCompras) {
            if(it.getProduto().getId().equals(id)){
                if(acao.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                } else if (acao.equals(0)) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                }
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/removeProduto/{id}")
    public String removerProdutoCarrinho(@PathVariable Long id){

        for (ItensCompra it:itensCompras) {
            if(it.getProduto().getId().equals(id)){
               itensCompras.remove(it);
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id){


        Optional<Produto> prod = produtoRepository.findById(id);
        Produto produto = prod.get();

        int controle = 0;
        for (ItensCompra it:itensCompras) {
            if(it.getProduto().getId().equals(produto.getId())){
                it.setQuantidade(it.getQuantidade()+1);
                it.setValorTotal(0.);
                it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setProduto(produto);
            item.setValorUnitario(produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
            itensCompras.add(item);
        }

        return "redirect:/carrinho";
    }

    private void buscarUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String email = authentication.getName();
            cliente = clienteRepository.buscarClienteEmail(email).get(0);
        }
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView  confirmarCompra(String formaPagamento){
        ModelAndView mv = new ModelAndView("mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        compraRepository.saveAndFlush(compra);

        for (ItensCompra it: itensCompras){
            it.setCompra(compra);
            itensCompraRepository.saveAndFlush(it);
        }
        itensCompras = new ArrayList<>();
        compra = new Compra();
        return mv;
    }


}
