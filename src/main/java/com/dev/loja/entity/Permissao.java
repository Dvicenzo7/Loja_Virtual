//package com.dev.loja.entity;
//
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@Entity
//@Table(name = "permissoes")
//public class Permissao implements Serializable {
//
//    public Permissao{
//        super();
//    }
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Temporal(TemporalType.DATE)
//    private Date dataCadastro = new Date();
//
//    @ManyToOne
//    private Funcionario funcionario;
//
//    @ManyToOne
//    private Papel papel;
//}
