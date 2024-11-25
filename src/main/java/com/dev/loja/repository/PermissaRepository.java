package com.dev.loja.repository;

import com.dev.loja.entity.Permissoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaRepository extends JpaRepository<Permissoes, Long> {
}
