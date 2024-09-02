package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {

}
