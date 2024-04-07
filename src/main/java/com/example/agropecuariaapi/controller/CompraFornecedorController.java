package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.CompraFornecedorDTO;
import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import com.example.agropecuariaapi.service.CompraFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compraFornecedor")
public class CompraFornecedorController {

    @Autowired
    private CompraFornecedorService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<CompraFornecedor> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(CompraFornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<CompraFornecedor> compraFornecedor = service.findById(id);

        if(!compraFornecedor.isPresent()){
            return new ResponseEntity<>("Compra não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(compraFornecedor.map(CompraFornecedorDTO::create));

    }

    @PostMapping
    public ResponseEntity post(@RequestBody CompraFornecedor CompraFornecedor){
        CompraFornecedor = service.salvar(CompraFornecedor);
        return ResponseEntity.ok().body(CompraFornecedor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<CompraFornecedor> CompraFornecedor = service.findById(id);

        if(!CompraFornecedor.isPresent()){
            return new ResponseEntity<>("Compra não encontrada", HttpStatus.NOT_FOUND);
        }

        service.excluir(CompraFornecedor.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
