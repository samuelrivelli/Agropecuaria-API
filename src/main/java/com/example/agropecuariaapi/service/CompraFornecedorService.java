package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import com.example.agropecuariaapi.model.repository.CompraFornecedorRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompraFornecedorService
{
    @Autowired
    private CompraFornecedorRepository repository;
    public List<CompraFornecedor> findAll(){
        return repository.findAll();
    }
    public Optional<CompraFornecedor> findById(Long id){
        return repository.findById(id);
    }
    @Transactional
    public CompraFornecedor save(CompraFornecedor compraFornecedor){
        validar(compraFornecedor);
        return repository.save(compraFornecedor);
    }

    @Transactional
    public void excluir(CompraFornecedor compraFornecedor){
        Objects.requireNonNull(compraFornecedor.getId());
        repository.delete(compraFornecedor);
    }

    public void validar(CompraFornecedor CompraFornecedor) {

        if (CompraFornecedor.getFornecedor() == null ) {
            throw new RegraNegocioException("Fornecedor inválido");
        }
        if (CompraFornecedor.getValor() == null || CompraFornecedor.getValor().equals("")) {
            throw new RegraNegocioException("Valor inválido");
        }

    }
}
