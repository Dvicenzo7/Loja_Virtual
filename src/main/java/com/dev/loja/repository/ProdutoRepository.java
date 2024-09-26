package com.dev.loja.repository;

import com.dev.loja.entity.Estado;
import com.dev.loja.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
