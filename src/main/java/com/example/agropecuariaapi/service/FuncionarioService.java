package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Funcionario;
import com.example.agropecuariaapi.model.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;
    public List<Funcionario> findAll(){
        return repository.findAll();
    }
    public Optional<Funcionario> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funcionario save(Funcionario funcionario){
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }


}
