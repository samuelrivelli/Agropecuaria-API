package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
