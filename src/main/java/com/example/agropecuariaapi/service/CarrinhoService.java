package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Carrinho;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.repository.CarrinhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    public List<Carrinho> findAll(){
        return repository.findAll();
    }

    public Optional<Carrinho> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Carrinho save(Carrinho carrinho)  {
        return repository.save(carrinho);
    }
    @Transactional
    public void excluir(Carrinho carrinho){
        Objects.requireNonNull(carrinho.getId());
        try{
            repository.delete(carrinho);
        } catch (DataIntegrityViolationException e){
            throw new RegraNegocioException("Erro ao excluir carrinho");
        }
    }

}
