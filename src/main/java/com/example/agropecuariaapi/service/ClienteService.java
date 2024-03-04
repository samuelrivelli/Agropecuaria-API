package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    public List<Cliente> findAll(){
        return repository.findAll();
    }
    public Optional<Cliente> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente){
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }


}
