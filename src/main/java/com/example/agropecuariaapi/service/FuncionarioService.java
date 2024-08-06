package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Funcionario;
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
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario Funcionario) {

        if (Funcionario.getNome() == null || Funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (Funcionario.getCpf() == null || Funcionario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (Funcionario.getEmail() == null || Funcionario.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }


}
