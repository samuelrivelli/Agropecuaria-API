package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.repository.ClienteRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ClienteService {

    private ClienteRepository repository;
    public List<Cliente> getClientes(){
        return repository.findAll();
    }
    public Optional<Cliente> getClienteById(Long id){
        return repository.findById(id);
    }


}
