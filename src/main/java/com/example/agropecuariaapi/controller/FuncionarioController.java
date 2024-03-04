package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.dto.FuncionarioDTO;
import com.example.agropecuariaapi.model.entity.Funcionario;
import com.example.agropecuariaapi.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Funcionario> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Funcionario> Funcionario = service.findById(id);

        if(!Funcionario.isPresent()){
            return new ResponseEntity<>("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Funcionario.map(FuncionarioDTO::create));

    }

    @PostMapping
    public ResponseEntity post(@RequestBody Funcionario Funcionario){
        Funcionario = service.salvar(Funcionario);
        return ResponseEntity.ok().body(Funcionario);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Funcionario> Funcionario = service.findById(id);

        if(!Funcionario.isPresent()){
            return new ResponseEntity<>("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Funcionario.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Funcionario FuncionarioAtualizado){

        Optional<Funcionario> FuncionarioExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Funcionario não encontrado",HttpStatus.NOT_FOUND);
        }

        Funcionario Funcionario = FuncionarioExistente.get();
        Funcionario.setNome(FuncionarioAtualizado.getNome());
        Funcionario.setCpf(FuncionarioAtualizado.getCpf());
        Funcionario.setEmail(FuncionarioAtualizado.getEmail());
        Funcionario.setCpf(FuncionarioAtualizado.getCpf());
        Funcionario.setEndereco(FuncionarioAtualizado.getEndereco());

        service.salvar(Funcionario);
        return ResponseEntity.ok(Funcionario);
    }


}
