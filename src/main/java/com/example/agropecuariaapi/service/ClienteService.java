package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.repository.ClienteRepository;
import com.example.agropecuariaapi.model.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Cliente save(Cliente cliente)  {
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente){
        Objects.requireNonNull(cliente.getId());
        try{
            repository.delete(cliente);
        } catch (DataIntegrityViolationException e){
            throw new RegraNegocioException("O cliente não pode ser excluído pois possui vendas associadas a ele");
        }
    }

    public void validar(Cliente Cliente) {

        if (Cliente.getNome() == null || Cliente.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (Cliente.getCpf() == null || Cliente.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (Cliente.getEmail() == null || Cliente.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }




}
