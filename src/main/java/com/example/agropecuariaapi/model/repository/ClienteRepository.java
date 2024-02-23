package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
