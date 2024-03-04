package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Endereco> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Endereco = service.findById(id);

        if(!Endereco.isPresent()){
            return new ResponseEntity<>("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Endereco);

    }

}
