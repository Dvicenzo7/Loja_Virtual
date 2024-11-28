package com.dev.loja.repository;

import com.dev.loja.entity.Cliente;
import com.dev.loja.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
