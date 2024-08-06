package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.FuncionarioDTO;
import com.example.agropecuariaapi.model.entity.*;
import com.example.agropecuariaapi.model.entity.Funcionario;
import com.example.agropecuariaapi.service.EnderecoService;
import com.example.agropecuariaapi.service.FuncionarioService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private EnderecoService enderecoService;

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
    public ResponseEntity post(@RequestBody FuncionarioDTO dto){
        try {
            Funcionario Funcionario = converter(dto);
            Endereco endereco = enderecoService.save(Funcionario.getEndereco());
            Funcionario.setEndereco(endereco);
            Funcionario = service.save(Funcionario);
            return new ResponseEntity(Funcionario, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FuncionarioDTO dto) {
        try {
            Optional<Funcionario> optionalFuncionario = service.findById(id);
            if (!optionalFuncionario.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario not found");
            }
            Funcionario FuncionarioExistente = optionalFuncionario.get();

            Funcionario FuncionarioAtualizado = converter(dto);

            Endereco enderecoAtualizado = enderecoService.save(FuncionarioAtualizado.getEndereco());
            FuncionarioAtualizado.setEndereco(enderecoAtualizado);

            FuncionarioAtualizado.setId(id);

            FuncionarioAtualizado = service.save(FuncionarioAtualizado);

            return ResponseEntity.ok(FuncionarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Funcionario converter(FuncionarioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Funcionario Funcionario = modelMapper.map(dto, Funcionario.class);
        Endereco endereco = modelMapper.map(dto,Endereco.class);
        Funcionario.setEndereco(endereco);

        return Funcionario;
    }



}
