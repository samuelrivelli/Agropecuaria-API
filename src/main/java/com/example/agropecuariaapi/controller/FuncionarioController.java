package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Funcionario;
import com.example.agropecuariaapi.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Funcionario> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Funcionario = service.findById(id);

        if(!Funcionario.isPresent()){
            return new ResponseEntity<>("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Funcionario);

    }

}
