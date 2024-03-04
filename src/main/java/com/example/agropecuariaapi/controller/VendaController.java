package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Venda> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Venda = service.findById(id);

        if(!Venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Venda);

    }

}
