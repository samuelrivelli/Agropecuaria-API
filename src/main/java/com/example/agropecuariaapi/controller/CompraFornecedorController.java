package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import com.example.agropecuariaapi.service.CompraFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compraFornecedor")
public class CompraFornecedorController {

    @Autowired
    private CompraFornecedorService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<CompraFornecedor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional compraFornecedor = service.findById(id);

        if(!compraFornecedor.isPresent()){
            return new ResponseEntity<>("Compra não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(compraFornecedor);

    }

}
