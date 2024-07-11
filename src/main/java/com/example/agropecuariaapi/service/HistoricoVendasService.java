package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.HistoricoVendas;
import com.example.agropecuariaapi.model.repository.HistoricoVendasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HistoricoVendasService {

    @Autowired
    private HistoricoVendasRepository repository;
    public List<HistoricoVendas> findAll(){
        return repository.findAll();
    }
    public Optional<HistoricoVendas> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public HistoricoVendas save(HistoricoVendas historicoVendas){
        return repository.save(historicoVendas);
    }

    @Transactional
    public void excluir(HistoricoVendas historicoVendas){
        Objects.requireNonNull(historicoVendas.getId());
        repository.delete(historicoVendas);
    }


}
