package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Pessoa;
import com.example.agropecuariaapi.model.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {

    private PessoaRepository repository;
    public List<Pessoa> findAll(){
        return repository.findAll();
    }
    public Optional<Pessoa> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Pessoa salvar(Pessoa Pessoa){
        return repository.save(Pessoa);
    }

    @Transactional
    public void excluir(Pessoa pessoa){
        Objects.requireNonNull(pessoa.getId());
        repository.delete(pessoa);
    }


}
