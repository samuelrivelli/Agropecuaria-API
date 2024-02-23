package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
