package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;
    public List<Fornecedor> findAll(){
        return repository.findAll();
    }
    public Optional<Fornecedor> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor save(Fornecedor fornecedor){
        return repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Fornecedor fornecedor){
        Objects.requireNonNull(fornecedor.getId());
        repository.delete(fornecedor);
    }


}
