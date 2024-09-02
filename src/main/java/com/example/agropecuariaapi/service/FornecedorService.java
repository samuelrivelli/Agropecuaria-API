package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.repository.FornecedorRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;
    public List<Fornecedor> findAll(){
        return repository.findAll();
    }
    public Optional<Fornecedor> findById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor save(Fornecedor fornecedor){
        validar(fornecedor);
        return repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Fornecedor fornecedor){
        Objects.requireNonNull(fornecedor.getId());
        try{
            repository.delete(fornecedor);
        } catch(DataIntegrityViolationException e){
            throw new RegraNegocioException("O fornecedor não pode ser excluído pois possui compras atreladas a ele");
        }
    }

    public void validar(Fornecedor Fornecedor) {

        if (Fornecedor.getRazaoSocial() == null || Fornecedor.getRazaoSocial().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (Fornecedor.getCnpj() == null || Fornecedor.getCnpj().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (Fornecedor.getEmail() == null || Fornecedor.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }


}
