package com.dev.loja.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "entrada_itens")
public class EntradaItens implements Serializable {

    public EntradaItens() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EntradaProduto entrada;
    @ManyToOne
    private Produto produto;
    private Double quantidade;
    private Double valorProduto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntradaProduto getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaProduto entrada) {
        this.entrada = entrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }
}