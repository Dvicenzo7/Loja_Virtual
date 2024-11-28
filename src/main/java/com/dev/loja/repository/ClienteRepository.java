package com.dev.loja.repository;

import com.dev.loja.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    @Query("from Cliente where email =?1 ")
    public List<Cliente> buscarClienteEmail(String email);
}
