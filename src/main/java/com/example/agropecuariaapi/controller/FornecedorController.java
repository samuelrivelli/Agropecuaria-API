package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Fornecedors")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Fornecedor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Fornecedor = service.findById(id);

        if(!Fornecedor.isPresent()){
            return new ResponseEntity<>("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Fornecedor);

    }

}
