package com.example.agropecuariaapi.model.repository;

import com.example.agropecuariaapi.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
