package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Divida;
import com.example.agropecuariaapi.model.repository.DividaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DividasService {

    @Autowired
    private DividaRepository repository;
    public List<Divida> findAll(){
        return repository.findAll();
    }
    public Optional<Divida> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Divida save(Divida Divida){
        return repository.save(Divida);
    }

    @Transactional
    public void excluir(Divida Divida){
        Objects.requireNonNull(Divida.getId());
        repository.delete(Divida);
    }

}
