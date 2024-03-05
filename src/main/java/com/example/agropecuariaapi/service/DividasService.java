package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Dividas;
import com.example.agropecuariaapi.model.repository.DividasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DividasService {

    @Autowired
    private DividasRepository repository;
    public List<Dividas> findAll(){
        return repository.findAll();
    }
    public Optional<Dividas> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Dividas salvar(Dividas Dividas){
        return repository.save(Dividas);
    }

    @Transactional
    public void excluir(Dividas Dividas){
        Objects.requireNonNull(Dividas.getId());
        repository.delete(Dividas);
    }

}
