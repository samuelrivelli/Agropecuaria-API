package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Pessoa;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Pessoa> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

}
