package com.dev.loja.repository;

import com.dev.loja.entity.Compra;
import com.dev.loja.entity.ItensCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensCompraRepository extends JpaRepository<ItensCompra, Long> {
}
