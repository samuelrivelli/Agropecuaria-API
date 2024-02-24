package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Estoque;
import com.example.agropecuariaapi.model.repository.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstoqueService {

    private EstoqueRepository repository;
    public List<Estoque> findAll(){
        return repository.findAll();
    }
    public Optional<Estoque> findById(Long id){
        return repository.findById(id);
    }
    @Transactional
    public Estoque salvar(Estoque estoque){
        return repository.save(estoque);
    }

    @Transactional
    public void excluir(Estoque estoque){
        Objects.requireNonNull(estoque.getId());
        repository.delete(estoque);
    }


}
