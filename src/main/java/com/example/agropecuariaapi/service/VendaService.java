package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;
    public List<Venda> findAll(){
        return repository.findAll();
    }
    public Optional<Venda> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Venda save(Venda venda){
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda){
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }


}
