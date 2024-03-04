package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Estoque;
import com.example.agropecuariaapi.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Estoque> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Estoque = service.findById(id);

        if(!Estoque.isPresent()){
            return new ResponseEntity<>("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Estoque);

    }

}
