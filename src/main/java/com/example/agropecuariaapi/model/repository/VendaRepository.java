package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
