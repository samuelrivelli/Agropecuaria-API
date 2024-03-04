package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.model.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;
    public List<Endereco> findAll(){
        return repository.findAll();
    }
    public Optional<Endereco> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Endereco salvar(Endereco endereco){
        return repository.save(endereco);
    }

    //teste
    @Transactional
    public void excluir(Endereco endereco){
        Objects.requireNonNull(endereco.getId());
        repository.delete(endereco);
    }


}
