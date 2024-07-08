package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.model.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    public List<Produto> findAll(){
        return repository.findAll();
    }
    public Optional<Produto> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Produto save(Produto produto){
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto){
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }


}
