package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
