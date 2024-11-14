package com.dev.loja.repository;

import com.dev.loja.entity.Funcionario;
import com.dev.loja.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<Papel, Long> {
}
