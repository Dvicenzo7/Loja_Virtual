package com.dev.loja.repository;

import com.dev.loja.entity.EntradaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaProdutoRepository extends JpaRepository<EntradaProduto, Long> {
}
