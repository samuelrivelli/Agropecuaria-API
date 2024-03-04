package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Produto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Produto = service.findById(id);

        if(!Produto.isPresent()){
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Produto);

    }

}
